package com.mayo.client.mayoclientapi.presentation.dto.response;

import com.google.cloud.Timestamp;
import com.mayo.client.mayoclientapi.persistence.domain.Reservation;
import com.mayo.client.mayoclientapi.persistence.domain.Store;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ReadReservationDetailResponse(
        String storeName,
        String storeNumber,
        LocalDateTime createdAt,
        LocalDateTime pickupTime,
        String reservationId,
        List<ReadCartResponse> cartList,
        Double totalPrice
) {
    public static ReadReservationDetailResponse from(Reservation reservation, Store store, List<ReadCartResponse> cartList) {
        return ReadReservationDetailResponse.builder()
                .reservationId(reservation.getReservationId())
                .storeName(store.getStoreName())
                .storeNumber(store.getStoreNumber())
                .createdAt(reservation.getCreatedAt().toSqlTimestamp().toLocalDateTime())
                .pickupTime(reservation.getPickupTime().toSqlTimestamp().toLocalDateTime())
                .cartList(cartList)
                .totalPrice(reservation.getTotalPrice())
                .build();
    }
}
