package com.mayo.client.mayoclientapi.presentation.dto.response;

import com.mayo.client.mayoclientapi.persistence.domain.Reservation;
import com.mayo.client.mayoclientapi.persistence.domain.Store;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReadReservationResponse(
        String reservationId,
        String storeName,
        String storeImage,
        ReadFirstItemResponse firstItem,
        Double totalPrice,
        LocalDateTime createdAt,
        Integer reservationState
) {
    public static ReadReservationResponse from(Reservation reservation, Store store, ReadFirstItemResponse firstItem) {
        return ReadReservationResponse.builder()
                .reservationId(reservation.getId())
                .storeName(store.getStoreName())
                .storeImage(store.getStoreImage())
                .firstItem(firstItem)
                .totalPrice(reservation.getTotalPrice())
                .createdAt(reservation.getCreatedAt().toSqlTimestamp().toLocalDateTime())
                .reservationState(reservation.getReservationState())
                .build();
    }
}
