package com.mayo.client.mayoclientapi.presentation.dto.response;

import com.mayo.client.mayoclientapi.persistence.domain.Cart;
import com.mayo.client.mayoclientapi.persistence.domain.Item;
import com.mayo.client.mayoclientapi.persistence.domain.Store;
import lombok.Builder;

@Builder
public record ReadCartResponse(
        String cartId,
        Integer cartItemCount,
        Integer itemQuantity,
        Double subtotal,
        String itemName,
        String itemImage,
        String storeId
) {
    public static ReadCartResponse from(Cart cart, Item item, Store store) {
        return ReadCartResponse.builder()
                .cartId(cart.getCartId())
                .cartItemCount(cart.getItemCount())
                .itemQuantity(item.getItemQuantity())
                .subtotal(cart.getSubtotal())
                .itemName(item.getItemName())
                .itemImage(item.getItemImage())
                .storeId(store.getId())
                .build();
    }
}
