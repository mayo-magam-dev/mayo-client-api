package com.mayo.client.mayoclientapi.presentation.dto.response;

import com.mayo.client.mayoclientapi.persistence.domain.Banner;
import lombok.Builder;

@Builder
public record ReadBannerResponse(
        String id,
        String imageUrl
) {
    public static ReadBannerResponse from(Banner banner) {
        return ReadBannerResponse.builder()
                .id(banner.getId())
                .imageUrl(banner.getImageUrl())
                .build();
    }
}
