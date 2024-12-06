package com.mayo.client.mayoclientapi.persistance.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.mayo.client.mayoclientapi.common.exception.ApplicationException;
import com.mayo.client.mayoclientapi.common.exception.payload.ErrorStatus;
import com.mayo.client.mayoclientapi.persistance.domain.FCMToken;
import com.mayo.client.mayoclientapi.persistance.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserRepository {

    private static final String COLLECTION_NAME_FCM_TOKENS = "fcm_tokens";
    private static final String COLLECTION_NAME_USERS = "users";
    private static final String FIELD_FCM_TOKEN = "fcm_token";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void save(User user) {
        Firestore db = FirestoreClient.getFirestore();
        db.collection(COLLECTION_NAME_USERS).document(user.getUid()).set(user.toMap());
    }

    public void deleteUser(String uid) {
        Firestore db = FirestoreClient.getFirestore();
        db.collection(COLLECTION_NAME_USERS).document(uid).delete();
    }

    public void removeNoticeStore(User user, DocumentReference storeRef) {
        Firestore db = FirestoreClient.getFirestore();

        db.collection(COLLECTION_NAME_USERS)
                .document(user.getUid())
                .update("noticeStores", FieldValue.arrayRemove(storeRef));
    }

    public void addNoticeStore(User user, DocumentReference storeRef) {
        Firestore db = FirestoreClient.getFirestore();

        db.collection(COLLECTION_NAME_USERS)
                .document(user.getUid())
                .update("noticeStores", FieldValue.arrayUnion(storeRef));
    }

    public void removeFavoriteStore(User user, DocumentReference storeRef) {
        Firestore db = FirestoreClient.getFirestore();

        db.collection(COLLECTION_NAME_USERS)
                .document(user.getUid())
                .update("favorite_stores", FieldValue.arrayRemove(storeRef));
    }

    public void addFavoriteStore(User user, DocumentReference storeRef) {
        Firestore db = FirestoreClient.getFirestore();

        db.collection(COLLECTION_NAME_USERS)
                .document(user.getUid())
                .update("favorite_stores", FieldValue.arrayUnion(storeRef));
    }

    public Optional<DocumentReference> findDocByUserId(String uid) {

        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<DocumentSnapshot> future = db.collection(COLLECTION_NAME_USERS).document(uid).get();

        try {
            return Optional.of(future.get().getReference());
        } catch (InterruptedException | ExecutionException e) {
            log.error("firebase 통신 중 오류 발생");
        }

        return Optional.empty();
    }

    public Optional<User> findByUserId(String userId) {

        Firestore db = FirestoreClient.getFirestore();

        DocumentReference documentReference = db.collection("users").document(userId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = null;

        try {
            document = future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("해당 유저를 찾는데 오류가 발생하였습니다", 400, LocalDateTime.now()));
        }

        return Optional.ofNullable(fromDocument(document));
    }

    public List<String> getFCMTokenByUserId(String userId) {

        Firestore db = FirestoreClient.getFirestore();

        List<String> fcmTokens = new ArrayList<>();
        DocumentReference userRef = db.collection("users").document(userId);

        try {
            DocumentSnapshot userDocument = userRef.get().get();

        if (userDocument.exists()) {
            CollectionReference fcmTokensCollectionRef = userRef.collection(COLLECTION_NAME_FCM_TOKENS);
            ApiFuture<QuerySnapshot> future = fcmTokensCollectionRef.get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for (QueryDocumentSnapshot document : documents) {
                String fcmToken = document.getString(FIELD_FCM_TOKEN);
                if (fcmToken != null) {
                    fcmTokens.add(fcmToken);
                }
            }
        }
        } catch (Exception e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("fcm 토큰을 가져오는데 에러가 발생하였습니다.", 404, LocalDateTime.now()));
        }

        return fcmTokens;
    }

    public List<String> getFCMTokenByStoresId(String storesId) {

        Firestore db = FirestoreClient.getFirestore();

        List<String> fcmTokens = new ArrayList<>();

        CollectionReference usersCollection = db.collection("users");
        DocumentReference storesDocumentId = db.collection("stores").document(storesId);
        Query query = usersCollection.whereEqualTo("store_ref", storesDocumentId);

        try {
            ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
            for (QueryDocumentSnapshot userDocument : querySnapshotApiFuture.get().getDocuments()) {
                CollectionReference fcmTokensCollection = userDocument.getReference()
                        .collection(COLLECTION_NAME_FCM_TOKENS);
                ApiFuture<QuerySnapshot> fcmTokenSnapshot = fcmTokensCollection.get();

                if (!fcmTokenSnapshot.get().isEmpty()) {
                    for(QueryDocumentSnapshot docSnap : fcmTokenSnapshot.get().getDocuments()) {
                        fcmTokens.add(docSnap.getString(FIELD_FCM_TOKEN));
                    }
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            log.error("getFCMTokenByStoresRef error: " + LocalDateTime.now() + ", " + e.getMessage());
        }
        return fcmTokens;
    }

    public void createFCMTokenById(String userId, String token){

        Firestore db = FirestoreClient.getFirestore();

        DocumentReference userRef = db.collection("users").document(userId);

        DocumentSnapshot userDocument = null;
        try {
            userDocument = userRef.get().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("firebase 통신 중 오류가 발생하였습니다.", 500, LocalDateTime.now()));
        }

        if (userDocument.exists()) {
            CollectionReference fcmTokensCollectionRef = userRef.collection(COLLECTION_NAME_FCM_TOKENS);
            ApiFuture<QuerySnapshot> future = fcmTokensCollectionRef.get();
            List<QueryDocumentSnapshot> documents = null;
            try {
                documents = future.get().getDocuments();
            } catch (InterruptedException | ExecutionException e) {
                throw new ApplicationException(ErrorStatus.toErrorStatus("firebase 통신 중 오류가 발생하였습니다.", 500, LocalDateTime.now()));
            }

            for (QueryDocumentSnapshot document : documents) {
                String fcmToken = document.getString(FIELD_FCM_TOKEN);
                if(fcmToken != null && fcmToken.equals(token)) {
                    return;
                }
            }

            FCMToken fcmToken = FCMToken.builder()
                    .token(token)
                    .createdAt(Timestamp.now().toString())
                    .deviceType("web")
                    .build();

            Map<String, Object> jsonFCM = objectMapper.convertValue(fcmToken, Map.class);

            fcmTokensCollectionRef.add(jsonFCM);
        }
    }

    private User fromDocument(DocumentSnapshot document) {
        return User.builder()
                .uid(document.getId())
                .userid(document.getId())
                .email(document.getString("email"))
                .displayName(document.getString("display_name"))
                .photoUrl(document.getString("photo_url"))
                .isManager(document.getBoolean("is_manager"))
                .name(document.getString("name"))
                .storeRef((DocumentReference) document.get("store_ref"))
                .agreeTerms1(document.getBoolean("agree_terms1"))
                .agreeTerms2(document.getBoolean("agree_terms2"))
                .agreeMarketing(document.getBoolean("agree_marketing"))
                .birthday(document.getTimestamp("birthday"))
                .phoneNumber(document.getString("phone_number"))
                .createdTime(document.getTimestamp("created_time"))
                .favoriteStores((List<DocumentReference>) document.get("favorite_stores"))
                .noticeStores((List<DocumentReference>) document.get("noticeStores"))
                .build();
    }

}

