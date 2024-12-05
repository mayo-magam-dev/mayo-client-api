package com.mayo.client.mayoclientapi.presentation.dto.response;

import com.mayo.client.mayoclientapi.persistance.domain.Item;
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
        String itemImage,
        Double salePrice,
        Integer cookingTime,
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
