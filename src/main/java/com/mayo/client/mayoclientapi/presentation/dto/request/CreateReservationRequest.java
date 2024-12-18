package com.mayo.client.mayoclientapi.presentation.dto.request;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.mayo.client.mayoclientapi.persistence.domain.Reservation;
import com.mayo.client.mayoclientapi.persistence.domain.type.ReservationState;

import java.util.List;

public record CreateReservationRequest(
        Timestamp pickupTime,
        Boolean reservationIsPlastic,
        String reservationRequest
) {
    public Reservation toEntity(List<DocumentReference> cartRefList, DocumentReference storeRef, Double totalPrice, DocumentReference userRef, Double totalSalePrice) {
        return Reservation.builder()
                .cartRef(cartRefList)
                .pickupTime(pickupTime)
                .reservationIsPlastics(reservationIsPlastic)
                .reservationRequest(reservationRequest)
                .reservationState(ReservationState.NEW.ordinal())
                .storeRef(storeRef)
                .totalPrice(totalPrice)
                .userRef(userRef)
                .createdAt(Timestamp.now())
                .totalSalePrice(totalSalePrice)
                .build();
    }
}
