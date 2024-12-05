package com.mayo.client.mayoclientapi.persistance.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.mayo.client.mayoclientapi.common.exception.ApplicationException;
import com.mayo.client.mayoclientapi.common.exception.payload.ErrorStatus;
import com.mayo.client.mayoclientapi.persistance.domain.Cart;
import com.mayo.client.mayoclientapi.persistance.domain.Reservation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
@Slf4j
public class CartRepository {

    private final String COLLECTION_NAME_CARTS = "carts";

    public void save(Cart cart) {
        Firestore db = FirestoreClient.getFirestore();
        db.collection(COLLECTION_NAME_CARTS).add(cart.toMap());
    }

    public List<Cart> findCartsByUserRef(DocumentReference userRef) {

        Firestore db = FirestoreClient.getFirestore();
        List<Cart> cartList = new ArrayList<>();
        CollectionReference collectionReference = db.collection(COLLECTION_NAME_CARTS);

        Query query = collectionReference.whereEqualTo("userRef", userRef)
                .whereEqualTo("cartActive", true);

        ApiFuture<QuerySnapshot> future = query.get();

        try {
            for (QueryDocumentSnapshot cartDocument : future.get().getDocuments()) {
                cartList.add(fromDocument(cartDocument));
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        return cartList;
    }

    public Optional<Cart> findByDocRef(DocumentReference docRef) {

        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = null;

        try {
            document = future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(
                    ErrorStatus.toErrorStatus("해당하는 카트가 없습니다.", 404, LocalDateTime.now())
            );
        }

        if(document.exists()) {
            return Optional.ofNullable(fromDocument(document));
        }

        return Optional.empty();
    }

    public Optional<Cart> findCartById(String cartId) {

        Firestore db = FirestoreClient.getFirestore();

        try {
            DocumentReference docRef = db.collection("carts").document(cartId);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot cartSnapshot = future.get();

            if (cartSnapshot.exists()) {
                return Optional.ofNullable(fromDocument(cartSnapshot));
            }
        }
        catch (ExecutionException | InterruptedException e) {
            throw new ApplicationException(
                    ErrorStatus.toErrorStatus("장바구니를 찾는데 에러가 발생하였습니다.", 400, LocalDateTime.now())
            );
        }

        return Optional.empty();
    }

    public void updateCartIsActiveFalse(String cartId) {
        Firestore db = FirestoreClient.getFirestore();

        try {
            DocumentReference docRef = db.collection("carts").document(cartId).get().get().getReference();
            docRef.update("cartActive", false);
        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(
                    ErrorStatus.toErrorStatus("firebase 통신 중 오류가 발생하였습니다.", 500, LocalDateTime.now() )
            );
        }
    }

    public Optional<DocumentReference> findFirstCartsByReservation(Reservation reservation) {

        List<DocumentReference> cartRefs = reservation.getCartRef();

        if (!cartRefs.isEmpty()) {
            return Optional.ofNullable(cartRefs.get(0));
        }

        return Optional.empty();
    }

    private Cart fromDocument(DocumentSnapshot document) {

        return Cart.builder()
                .cartId(document.getId())
                .itemCount(document.get("itemCount", Integer.class))
                .cartActive(document.getBoolean("cartActive"))
                .createdAt(document.getTimestamp("created_at"))
                .pickupTime(document.getTimestamp("pickup_time"))
                .subtotal(document.getDouble("subtotal"))
                .item((DocumentReference) document.get("item"))
                .userRef((DocumentReference) document.get("userRef"))
                .storeRef((DocumentReference) document.get("store_ref"))
                .build();
    }

}