package com.mayo.client.mayoclientapi.persistence.domain;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Getter
@ToString
public class Cart {

    @DocumentId
    private String cartId;

    @PropertyName("ItemCount")
    private Integer itemCount;

    @PropertyName("cartActive")
    private Boolean cartActive;

    @PropertyName("created_at")
    private Timestamp createdAt;

    @PropertyName("pickup_time")
    private Timestamp pickupTime;

    @PropertyName("subtotal")
    private Double subtotal;

    @PropertyName("userRef")
    private DocumentReference userRef;

    @PropertyName("item")
    private DocumentReference item;

    @PropertyName("store_ref")
    private DocumentReference storeRef;

    @Builder
    public Cart(String cartId, Integer itemCount, Boolean cartActive, Timestamp createdAt, Timestamp pickupTime, Double subtotal, DocumentReference userRef, DocumentReference item, DocumentReference storeRef) {
        this.cartId = cartId;
        this.itemCount = itemCount;
        this.cartActive = cartActive;
        this.createdAt = createdAt;
        this.pickupTime = pickupTime;
        this.subtotal = subtotal;
        this.userRef = userRef;
        this.item = item;
        this.storeRef = storeRef;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("cartActive", getCartActive());
        result.put("itemCount", getItemCount());
        result.put("created_at", getCreatedAt());
        result.put("store_ref", getStoreRef());
        result.put("subtotal", getSubtotal());
        result.put("userRef", getUserRef());
        result.put("item", getItem());

        return result;
    }
}
