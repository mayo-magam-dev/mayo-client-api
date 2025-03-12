package com.mayo.client.mayoclientapi.presentation.dto.response;

import com.mayo.client.mayoclientapi.persistence.domain.Store;
import lombok.Builder;

@Builder
public record ReadRecentlyStoreResponse(
        String id,
        String storeName,
        Boolean openState,
        Long storeSellingType,
        String address,
        String mainImage
) {
    public static ReadRecentlyStoreResponse from(Store store) {
        return ReadRecentlyStoreResponse.builder()
                .id(store.getId())
                .storeSellingType(store.getStoreSellingType())
                .storeName(store.getStoreName())
                .openState(store.getOpenState())
                .address(store.getAddress())
                .mainImage(store.getStoreMainImage())
                .build();
    }
}
