package com.mayo.client.mayoclientapi.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.Timestamp;
import com.mayo.client.mayoclientapi.common.annotation.FirestoreTransactional;
import com.mayo.client.mayoclientapi.common.exception.ApplicationException;
import com.mayo.client.mayoclientapi.common.exception.payload.ErrorStatus;
import com.mayo.client.mayoclientapi.persistence.repository.PushNotificationRepository;
import com.mayo.client.mayoclientapi.presentation.dto.request.FCMMessageRequest;
import com.mayo.client.mayoclientapi.presentation.dto.request.CreateClientPushNotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.http.HttpHeaders;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FCMService {

    private static final String SERVER_NAME = "mayo-client-server";
    private static final String API_URL = "https://fcm.googleapis.com/v1/projects/mayo-app-280d4/messages:send";
    private final ObjectMapper objectMapper;
    private final PushNotificationRepository pushNotificationRepository;

    public Boolean sendMessageTo(String targetToken, String title, String body, String image){

        String message = makeMessage(targetToken, title, body, image);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=UTF-8"));

        Request request = null;
        try {
            request = new Request.Builder()
                    .url(API_URL)
                    .post(requestBody)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                    .header(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                    .build();
        } catch (IOException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("fcm 요청 생성 중 오류가 발생하였습니다.", 500, LocalDateTime.now()));
        }

        boolean result = false;

        try (Response response = client.newCall(request).execute()) {
            result = response.isSuccessful();
        } catch (Exception e) {
            log.error("FCM sendMessageTo error:" + LocalDateTime.now() + ", msg: " + e.getMessage());
        }

        return result;
    }

    private String makeMessage(String targetToken, String title, String body, String image)  {
        FCMMessageRequest fcmMessageRequest = FCMMessageRequest.builder()
                .message(
                        FCMMessageRequest.Message.builder()
                                .token(targetToken)
                                .notification(
                                        FCMMessageRequest.Notification.builder()
                                                .title(title)
                                                .body(body)
                                                .image(image)
                                                .build()
                                )
                                .build()
                )
                .validateOnly(false)
                .build();

        try {
            return objectMapper.writeValueAsString(fcmMessageRequest);
        } catch (JsonProcessingException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("json 변경 중 오류가 발생하였습니다.", 500, LocalDateTime.now()));
        }
    }

    public boolean addWebPushNotifications(CreateClientPushNotificationRequest request) {
        return pushNotificationRepository.addPushNotification(request);
    }

    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "/key/mayo-app-280d4.json";
        String url = "https://www.googleapis.com/auth/cloud-platform";
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of(url));
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

    public boolean sendNewReservationMessage(List<String> tokens){

        List<Boolean> results = new ArrayList<>();

        for (String token : tokens) {
            results.add(sendMessageTo(token, "신규 주문이 있습니다!", "주문을 확인해주세요!", null));
        }

        boolean status = !results.contains(false);
        CreateClientPushNotificationRequest request = CreateClientPushNotificationRequest.builder()
                .notificationText("주문을 확인해주세요!")
                .notificationTitle("신규 주문이 들어왔어요!")
                .sender(SERVER_NAME)
                .numSent(results.size())
                .timestamp(Timestamp.now())
                .status(status)
                .build();

        if (!addWebPushNotifications(request)) {
            log.info("push notification false = " + Timestamp.now());
        }
        return status;
    }
}