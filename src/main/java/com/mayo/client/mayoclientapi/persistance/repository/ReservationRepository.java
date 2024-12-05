package com.mayo.client.mayoclientapi.persistance.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.mayo.client.mayoclientapi.common.exception.ApplicationException;
import com.mayo.client.mayoclientapi.common.exception.payload.ErrorStatus;
import com.mayo.client.mayoclientapi.persistance.domain.Reservation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Repository
@Slf4j
public class ReservationRepository {

    private static final String USER_COLLECTION_NAME = "users";
    private static final String COLLECTION_NAME = "reservation";

    public List<Reservation> getReservationsByUserId(String userId) {

        List<Reservation> reservations = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();

        DocumentReference userDocumentId = db.collection(USER_COLLECTION_NAME).document(userId);

        CollectionReference reservationRef = db.collection(COLLECTION_NAME);
        Query query = reservationRef.whereEqualTo("user_ref", userDocumentId);

        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        QuerySnapshot querySnapshot = null;

        try {
            querySnapshot = querySnapshotApiFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        Comparator<Reservation> createdAtComparator = Comparator
                .comparing(entity -> entity.getCreatedAt().toSqlTimestamp(), Comparator.reverseOrder());

        for (QueryDocumentSnapshot reservationDocument : querySnapshot.getDocuments()) {
            Reservation Reservation = fromDocument(reservationDocument);
            reservations.add(Reservation);
        }

        return reservations;
    }

    //예약 도큐먼트 Id를 입력받아 reservation 객체를 가져옵니다.
    public Optional<Reservation> findByReservationId(String reservationId) {

        Firestore db = FirestoreClient.getFirestore();

        try {
            DocumentReference docRef = db.collection("reservation").document(reservationId);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                return Optional.ofNullable(fromDocument(document));
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public Optional<Reservation> findRecentlyByUserId(String userId) {

        Firestore db = FirestoreClient.getFirestore();

        DocumentReference userDocumentId = db.collection(USER_COLLECTION_NAME).document(userId);

        CollectionReference reservationRef = db.collection(COLLECTION_NAME);
        Query query = reservationRef
                .whereEqualTo("user_ref", userDocumentId)
                .orderBy("created_at", Query.Direction.DESCENDING)
                .limit(1);

        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();

        try {
            QuerySnapshot querySnapshot = querySnapshotApiFuture.get();

            if (!querySnapshot.isEmpty()) {
                QueryDocumentSnapshot latestDocument = querySnapshot.getDocuments().get(0);
                return Optional.of(fromDocument(latestDocument));
            }

        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("알 수 없는 오류가 발생하였습니다.", 500, LocalDateTime.now()));
        }

        return Optional.empty();
    }

    private Reservation fromDocument(DocumentSnapshot document) {
        return Reservation.builder()
                .id(document.getId())
                .reservationId(document.getString("reservation_id"))
                .reservationState(document.get("reservation_state", Integer.class))
                .reservationRequest(document.getString("reservation_request"))
                .reservationIsPlastics(document.getBoolean("reservation_is_plastics"))
                .createdAt(document.getTimestamp("created_at"))
                .pickupTime(document.getTimestamp("pickup_time"))
                .totalPrice(document.getDouble("total_price"))
                .storeRef((DocumentReference) document.get("store_ref"))
                .userRef((DocumentReference) document.get("user_ref"))
                .cartRef((List<DocumentReference>) document.get("cart_ref"))
                .build();
    }
}