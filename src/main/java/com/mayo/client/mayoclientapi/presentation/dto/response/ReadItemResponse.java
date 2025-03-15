package com.mayo.client.mayoclientapi.presentation.dto.response;

import com.mayo.client.mayoclientapi.persistence.domain.Item;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ReadItemResponse(
        String itemId,
        String itemName,
        String itemDescription,
        Integer originalPrice,
        Double salePercent,
        Integer itemQuantity,
        Boolean itemOnSale,
        @Schema(nullable = true)
        String itemImage,
        Double salePrice,
        Integer cookingTime,
        @Schema(nullable = true)
        String additionalInformation
) {
    public static ReadItemResponse from(Item item) {
        return ReadItemResponse.builder()
                .itemId(item.getItemId())
                .itemName(item.getItemName())
                .itemDescription(item.getItemDescription())
                .originalPrice(item.getOriginalPrice())
                .salePercent(item.getSalePercent())
                .itemQuantity(item.getItemQuantity())
                .itemOnSale(item.getItemOnSale())
                .itemImage(item.getItemImage())
                .salePrice(item.getSalePrice())
                .cookingTime(item.getCookingTime())
                .additionalInformation(item.getAdditionalInformation())
                .build();
    }
}
