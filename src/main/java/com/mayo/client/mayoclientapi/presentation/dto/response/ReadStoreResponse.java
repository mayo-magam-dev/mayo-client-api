package com.mayo.client.mayoclientapi.presentation.dto.response;

import com.mayo.client.mayoclientapi.persistence.domain.Store;
import com.mayo.client.mayoclientapi.persistence.domain.type.Weekday;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

import java.io.Serializable;

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
        @Schema(nullable = true)
        String originInfo,
        @Schema(nullable = true)
        String additionalComment,
        Long storeCategory,
        Long storeSellingType,
        String mainImage,
        @Schema(nullable = true)
        String accountNumber,
        @Schema(name = "가게의 오픈 요일", example = "[\"월\", \"화\", \"수\"]")
        List<String> openDayOfWeek
) implements Serializable {
    public static ReadStoreResponse from(Store store) {
        List<String> openDayLabels = store.getOpenDayOfWeek() != null
            ? store.getOpenDayOfWeek().stream()
            .sorted()
            .map(Weekday::labelOf)
            .toList()
            : List.of();

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
                .mainImage(store.getStoreMainImage())
                .accountNumber(store.getAccountNumber())
                .openDayOfWeek(openDayLabels)
                .build();
    }
}
