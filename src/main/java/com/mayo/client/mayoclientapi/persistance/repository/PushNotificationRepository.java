package com.mayo.client.mayoclientapi.persistance.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.mayo.client.mayoclientapi.presentation.dto.request.CreateClientPushNotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class PushNotificationRepository {

    private static final String COLLECTION_NAME = "push_notifications";
    private final Firestore firestore;

    public boolean addPushNotification(CreateClientPushNotificationRequest request) {

        DocumentReference addDocRef = firestore.collection(COLLECTION_NAME).document();

        try {
            ApiFuture<WriteResult> writeResult = addDocRef.set(request);

            writeResult.get();

            return true;
        } catch (Exception e) {
            log.error("add push notification error" + e.getMessage());
            return false;
        }
    }
}
