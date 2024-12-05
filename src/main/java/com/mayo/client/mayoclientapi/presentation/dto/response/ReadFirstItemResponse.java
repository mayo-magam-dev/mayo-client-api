package com.mayo.client.mayoclientapi.presentation.dto.response;

import lombok.Builder;


@Builder
public record ReadFirstItemResponse(
        String itemName,
        Integer itemQuantity
) {
}
