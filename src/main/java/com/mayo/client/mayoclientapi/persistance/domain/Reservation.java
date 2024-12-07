package com.mayo.client.mayoclientapi.persistance.domain;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
@ToString
public class Reservation {

    @DocumentId
    private String id;

    @PropertyName("reservation_id")
    private String reservationId;

    @PropertyName("reservation_state")
    private Integer reservationState;

    @PropertyName("reservation_request")
    private String reservationRequest;

    @PropertyName("reservation_is_plastics")
    private Boolean reservationIsPlastics;

    @PropertyName("created_at")
    private Timestamp createdAt;

    @PropertyName("quantityList")
    private List<Integer> quantityList;

    @PropertyName("pickup_time")
    private Timestamp pickupTime;

    @PropertyName("total_price")
    private Double totalPrice;

    @PropertyName("store_ref")
    private DocumentReference storeRef;

    @PropertyName("user_ref")
    private DocumentReference userRef;

    @PropertyName("itemList_ref")
    private List<DocumentReference> itemListRef;

    @PropertyName("cart_ref")
    private List<DocumentReference> cartRef;

    @PropertyName("total_sale_price")
    private Double totalSalePrice;

    @Builder
    public Reservation(String id, String reservationId, Integer reservationState, String reservationRequest, Boolean reservationIsPlastics, Timestamp createdAt, List<Integer> quantityList, Timestamp pickupTime, Double totalPrice, DocumentReference storeRef, DocumentReference userRef, List<DocumentReference> itemListRef, List<DocumentReference> cartRef, Double totalSalePrice) {
        this.id = id;
        this.reservationId = reservationId;
        this.reservationState = reservationState;
        this.reservationRequest = reservationRequest;
        this.reservationIsPlastics = reservationIsPlastics;
        this.createdAt = createdAt;
        this.quantityList = quantityList;
        this.pickupTime = pickupTime;
        this.totalPrice = totalPrice;
        this.storeRef = storeRef;
        this.userRef = userRef;
        this.itemListRef = itemListRef;
        this.cartRef = cartRef;
        this.totalSalePrice = totalSalePrice;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("cart_ref", cartRef);
        result.put("created_at", createdAt);
        result.put("pickup_time", pickupTime);
        result.put("reservation_is_plastic", reservationIsPlastics);
        result.put("reservation_request", reservationRequest);
        result.put("reservation_state", reservationState);
        result.put("store_ref", storeRef);
        result.put("total_price", totalPrice);
        result.put("user_ref", userRef);
        result.put("total_sale_price", totalSalePrice);

        return result;
    }
}
