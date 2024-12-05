package com.mayo.client.mayoclientapi.presentation.dto.request;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.mayo.client.mayoclientapi.persistance.domain.Cart;

public record CreateCartRequest(
        String itemId,
        Integer itemCount,
        String storeId
) {
    public Cart toEntity(DocumentReference item, DocumentReference store, DocumentReference user, Double subTotal) {
        return Cart.builder()
                .createdAt(Timestamp.now())
                .cartActive(true)
                .item(item)
                .itemCount(itemCount)
                .storeRef(store)
                .subtotal(subTotal)
                .userRef(user)
                .build();
    }
}
