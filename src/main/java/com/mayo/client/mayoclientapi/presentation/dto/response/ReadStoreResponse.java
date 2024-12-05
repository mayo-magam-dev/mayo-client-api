package com.mayo.client.mayoclientapi.presentation.dto.response;

import com.mayo.client.mayoclientapi.persistance.domain.Store;
import lombok.Builder;

@Builder
public record ReadStoreResponse(
        String id,
        String storeName,
        Boolean openState,
        String address,
        String storeImage,
        String openTime,
        String closeTime,
        String saleStart,
        String saleEnd,
        String storeDescription,
        String storeNumber,
        String storeMapUrl,
        String originInfo,
        String additionalComment,
        Long storeCategory,
        Long storeSellingType
) {
    public static ReadStoreResponse from(Store store) {
        return ReadStoreResponse.builder()
                .id(store.getId())
                .storeName(store.getStoreName())
                .openState(store.getOpenState())
                .address(store.getAddress())
                .storeImage(store.getStoreImage())
                .openTime(store.getOpenTime())
                .closeTime(store.getCloseTime())
                .saleStart(store.getSaleStart())
                .saleEnd(store.getSaleEnd())
                .storeDescription(store.getStoreDescription())
                .storeNumber(store.getStoreNumber())
                .storeMapUrl(store.getStoreMapUrl())
                .originInfo(store.getOriginInfo())
                .additionalComment(store.getAdditionalComment())
                .storeCategory(store.getStoreCategory())
                .storeSellingType(store.getStoreSellingType())
                .build();
    }
}
