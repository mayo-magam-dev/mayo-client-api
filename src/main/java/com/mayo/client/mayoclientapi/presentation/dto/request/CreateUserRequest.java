package com.mayo.client.mayoclientapi.presentation.dto.request;

import com.google.cloud.Timestamp;
import com.mayo.client.mayoclientapi.persistence.domain.User;
import com.mayo.client.mayoclientapi.persistence.domain.type.GenderType;

public record CreateUserRequest(
        String uid,
        String email,
        Timestamp createdTime,
        GenderType gender,
        String name,
        String displayName,
        String phoneNumber,
        Boolean agreeMarketing,
        Boolean agreeTerms1,
        Boolean agreeTerms2
) {
    public User toEntity(String uid) {
        return User.builder()
                .uid(uid)
                .userid(uid)
                .email(email)
                .createdTime(createdTime)
                .phoneNumber(phoneNumber)
                .agreeMarketing(agreeMarketing)
                .agreeTerms1(agreeTerms1)
                .agreeTerms2(agreeTerms2)
                .build();
    }
}
