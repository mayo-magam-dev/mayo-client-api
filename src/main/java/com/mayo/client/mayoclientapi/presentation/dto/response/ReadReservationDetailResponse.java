package com.mayo.client.mayoclientapi.presentation.dto.response;

import com.google.cloud.Timestamp;
import com.mayo.client.mayoclientapi.persistance.domain.Reservation;
import com.mayo.client.mayoclientapi.persistance.domain.Store;
import lombok.Builder;

import java.util.List;

@Builder
public record ReadReservationDetailResponse(
        String storeName,
        String storeNumber,
        Timestamp createdAt,
        Timestamp pickupTime,
        String reservationId,
        List<ReadCartResponse> cartList,
        Double totalPrice
) {
    public static ReadReservationDetailResponse from(Reservation reservation, Store store, List<ReadCartResponse> cartList) {
        return ReadReservationDetailResponse.builder()
                .reservationId(reservation.getReservationId())
                .storeName(store.getStoreName())
                .storeNumber(store.getStoreNumber())
                .createdAt(reservation.getCreatedAt())
                .pickupTime(reservation.getPickupTime())
                .cartList(cartList)
                .totalPrice(reservation.getTotalPrice())
                .build();
    }
}
