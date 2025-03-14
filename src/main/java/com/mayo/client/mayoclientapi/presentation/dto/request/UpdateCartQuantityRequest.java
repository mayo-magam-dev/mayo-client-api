package com.mayo.client.mayoclientapi.presentation.dto.request;

import jakarta.validation.constraints.Min;

public record UpdateCartQuantityRequest(
        String cartId,

        @Min(1)
        Integer itemQuantity
) {
}
