package com.mayo.client.mayoclientapi.presentation.dto.response;

import com.google.cloud.Timestamp;
import com.mayo.client.mayoclientapi.persistance.domain.Reservation;
import com.mayo.client.mayoclientapi.persistance.domain.Store;
import lombok.Builder;

@Builder
public record ReadReservationResponse(
        String reservationId,
        String storeName,
        String storeImage,
        ReadFirstItemResponse firstItem,
        Double totalPrice,
        Timestamp createdAt
) {
    public static ReadReservationResponse from(Reservation reservation, Store store, ReadFirstItemResponse firstItem) {
        return ReadReservationResponse.builder()
                .reservationId(reservation.getId())
                .storeName(store.getStoreName())
                .storeImage(store.getStoreImage())
                .firstItem(firstItem)
                .totalPrice(reservation.getTotalPrice())
                .createdAt(reservation.getCreatedAt())
                .build();
    }
}
