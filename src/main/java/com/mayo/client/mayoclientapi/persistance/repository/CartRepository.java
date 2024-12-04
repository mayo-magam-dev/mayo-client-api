package com.mayo.client.mayoclientapi.persistance.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.mayo.client.mayoclientapi.common.exception.ApplicationException;
import com.mayo.client.mayoclientapi.common.exception.payload.ErrorStatus;
import com.mayo.client.mayoclientapi.persistance.domain.Cart;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
public class CartRepository {

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