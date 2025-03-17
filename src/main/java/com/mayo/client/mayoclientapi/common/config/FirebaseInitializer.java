package com.mayo.client.mayoclientapi.common.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.mayo.client.mayoclientapi.common.exception.ApplicationException;
import com.mayo.client.mayoclientapi.common.exception.payload.ErrorStatus;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Configuration
@Slf4j
public class FirebaseInitializer {

    private static final String DATABASE_URL = "https://mayo-app.firebaseio.com";
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final long RETRY_INTERVAL_MS = 500;

    private final AtomicBoolean reconnecting = new AtomicBoolean(false);

    @Bean
    public Firestore firestore() {
        return FirestoreClient.getFirestore();
    }

    @Bean
    public FirestoreConnectionInterceptor firestoreConnectionInterceptor() {
        return new FirestoreConnectionInterceptor();
    }

    @PostConstruct
    public void init() {
        firestoreInit();
    }

    private synchronized void firestoreInit() {
        try {
            InputStream inputStream = new ClassPathResource("/key/mayo-app-280d4.json").getInputStream();
            File file = File.createTempFile("key", ".json");
            FileCopyUtils.copy(inputStream.readAllBytes(), file);
            FileInputStream firebaseAccount = new FileInputStream(file);
            FirebaseOptions.Builder optionBuilder = FirebaseOptions.builder();

            if(FirebaseApp.getApps().isEmpty()) {
                FirebaseOptions options = optionBuilder
                        .setCredentials(GoogleCredentials.fromStream(firebaseAccount))
                        .setDatabaseUrl(DATABASE_URL)
                        .build();

                FirebaseApp.initializeApp(options);
                log.info("Firebase has been initialized successfully");
            }

        } catch (Exception e) {
            log.error("Firebase 초기화 중 오류 발생: {}", e.getMessage(), e);
            throw new ApplicationException(ErrorStatus.toErrorStatus("firebase 연결 중 오류 발생", 400, LocalDateTime.now()));
        }
    }

    public class FirestoreConnectionInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            ensureFirestoreConnection();
            return true;
        }

        private void ensureFirestoreConnection() {
            if (!isFirestoreConnected() && reconnecting.compareAndSet(false, true)) {
                try {
                    log.warn("Firestore 연결이 끊어짐을 감지. 재연결 시도 중...");
                    reconnectFirestore();
                } finally {
                    reconnecting.set(false);
                }
            }
        }

        private boolean isFirestoreConnected() {
            try {
                if (FirebaseApp.getApps().isEmpty()) {
                    return false;
                }

                Firestore firestore = FirestoreClient.getFirestore();
                firestore.collection("connection_test").limit(1).get().get(2, TimeUnit.SECONDS);

                return true;
            } catch (Exception e) {
                log.debug("Firestore 연결 확인 실패: {}", e.getMessage());
                return false;
            }
        }

        private void reconnectFirestore() {
            for (int attempt = 0; attempt < MAX_RETRY_ATTEMPTS; attempt++) {
                try {
                    log.info("Firestore 재연결 시도 중 ({}/{})", attempt + 1, MAX_RETRY_ATTEMPTS);

                    try {
                        for (FirebaseApp app : FirebaseApp.getApps()) {
                            app.delete();
                        }
                        log.info("기존 Firebase 앱 삭제 완료");
                    } catch (Exception e) {
                        log.warn("Firebase 앱 삭제 중 오류: {}", e.getMessage());
                    }

                    firestoreInit();

                    Firestore firestore = FirestoreClient.getFirestore();
                    firestore.collection("connection_test").limit(1).get().get(2, TimeUnit.SECONDS);

                    log.info("Firestore 재연결 성공");
                    return;
                } catch (Exception e) {
                    log.error("재연결 시도 {} 실패: {}", attempt + 1, e.getMessage());
                    if (attempt < MAX_RETRY_ATTEMPTS - 1) {
                        try {
                            Thread.sleep(RETRY_INTERVAL_MS * (attempt + 1)); // 백오프 전략 적용
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                            log.error("재연결 대기 중 인터럽트 발생", ie);
                        }
                    }
                }
            }
            log.error("Firestore {} 시도 후 재연결 실패", MAX_RETRY_ATTEMPTS);
        }
    }
}