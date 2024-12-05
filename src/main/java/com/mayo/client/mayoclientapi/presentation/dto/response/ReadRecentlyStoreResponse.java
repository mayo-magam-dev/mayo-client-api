package com.mayo.client.mayoclientapi.presentation.dto.response;

import com.mayo.client.mayoclientapi.persistance.domain.Store;
import lombok.Builder;

@Builder
public record ReadRecentlyStoreResponse(
        String id,
        String storeName,
        Boolean openState,
        Long storeSellingType,
        String address
) {
    public static ReadRecentlyStoreResponse from(Store store) {
        return ReadRecentlyStoreResponse.builder()
                .id(store.getId())
                .storeSellingType(store.getStoreSellingType())
                .storeName(store.getStoreName())
                .openState(store.getOpenState())
                .address(store.getAddress())
                .build();
    }
}
