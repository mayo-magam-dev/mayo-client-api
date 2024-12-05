package com.mayo.client.mayoclientapi.presentation.dto.response;

import com.mayo.client.mayoclientapi.persistance.domain.Store;
import lombok.Builder;

@Builder
public record ReadSimpleStoreResponse(
        String id,
        String storeName,
        Boolean openState,
        Long storeSellingType,
        String address,
        Double maxSalePercent
) {
    public static ReadSimpleStoreResponse from(Store store, Double maxSalePercent) {
        return ReadSimpleStoreResponse.builder()
                .id(store.getId())
                .storeSellingType(store.getStoreSellingType())
                .storeName(store.getStoreName())
                .openState(store.getOpenState())
                .address(store.getAddress())
                .maxSalePercent(maxSalePercent)
                .build();
    }
}
