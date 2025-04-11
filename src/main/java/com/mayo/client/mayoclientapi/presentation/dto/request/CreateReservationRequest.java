package com.mayo.client.mayoclientapi.presentation.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.mayo.client.mayoclientapi.common.serializer.TimestampDeserializer;
import com.mayo.client.mayoclientapi.persistence.domain.Reservation;
import com.mayo.client.mayoclientapi.persistence.domain.type.ReservationState;

import java.util.List;

public record CreateReservationRequest(
        @JsonDeserialize(using = TimestampDeserializer.class)
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
                .reservationState(ReservationState.NEW.getState())
                .storeRef(storeRef)
                .totalPrice(totalPrice)
                .userRef(userRef)
                .createdAt(Timestamp.now())
                .totalSalePrice(totalSalePrice)
                .build();
    }
}
