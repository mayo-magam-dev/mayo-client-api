package com.mayo.client.mayoclientapi.presentation.dto.response;

import com.google.cloud.Timestamp;
import com.mayo.client.mayoclientapi.persistence.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ReadUserResponse(
        String userid,
        String uid,
        String email,
        String displayName,
        @Schema(nullable = true)
        String photoUrl,
        @Schema(nullable = true)
        String phoneNumber,
        @Schema(nullable = true)
        Boolean agreeMarketing,
        @Schema(nullable = true)
        String gender,
        @Schema(nullable = true)
        String name
) {
    public static ReadUserResponse from(User user) {
        return ReadUserResponse.builder()
                .userid(user.getUserid())
                .uid(user.getUid())
                .email(user.getEmail())
                .displayName(user.getDisplayName())
                .photoUrl(user.getPhotoUrl())
                .phoneNumber(user.getPhoneNumber())
                .agreeMarketing(user.getAgreeMarketing())
                .gender(user.getGender())
                .name(user.getName())
                .build();
    }
}
