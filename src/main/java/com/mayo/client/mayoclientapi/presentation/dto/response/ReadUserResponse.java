package com.mayo.client.mayoclientapi.presentation.dto.response;

import com.google.cloud.Timestamp;
import com.mayo.client.mayoclientapi.persistence.domain.User;
import lombok.Builder;

@Builder
public record ReadUserResponse(
        String userid,
        String uid,
        String email,
        String displayName,
        String photoUrl,
        Timestamp createdTime,
        String phoneNumber,
        Boolean isManager,
        Boolean agreeTerms1,
        Boolean agreeTerms2,
        Boolean agreeMarketing,
        String currentLocation,
        String gender,
        String name,
        Timestamp birthday
) {
    public static ReadUserResponse from(User user) {
        return ReadUserResponse.builder()
                .userid(user.getUserid())
                .uid(user.getUid())
                .email(user.getEmail())
                .displayName(user.getDisplayName())
                .photoUrl(user.getPhotoUrl())
                .createdTime(user.getCreatedTime())
                .phoneNumber(user.getPhoneNumber())
                .isManager(user.getIsManager())
                .agreeTerms1(user.getAgreeTerms1())
                .agreeTerms2(user.getAgreeTerms2())
                .agreeMarketing(user.getAgreeMarketing())
                .currentLocation(user.getCurrentLocation())
                .gender(user.getGender())
                .name(user.getName())
                .birthday(user.getBirthday())
                .build();
    }
}
