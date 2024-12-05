package com.mayo.client.mayoclientapi.presentation.dto.response;

import com.mayo.client.mayoclientapi.persistance.domain.Cart;
import com.mayo.client.mayoclientapi.persistance.domain.Item;
import com.mayo.client.mayoclientapi.persistance.domain.Store;
import lombok.Builder;

@Builder
public record ReadCartResponse(
        String cartId,
        Integer itemCount,
        Double subtotal,
        String itemName,
        String itemImage,
        String storeId
) {
    public static ReadCartResponse from(Cart cart, Item item, Store store) {
        return ReadCartResponse.builder()
                .cartId(cart.getCartId())
                .itemCount(cart.getItemCount())
                .subtotal(cart.getSubtotal())
                .itemName(item.getItemName())
                .itemImage(item.getItemImage())
                .storeId(store.getId())
                .build();
    }
}
