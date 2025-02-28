package com.mayo.client.mayoclientapi.presentation.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.cloud.Timestamp;
import com.mayo.client.mayoclientapi.common.serializer.FirestoreTimestampDeserializer;
import com.mayo.client.mayoclientapi.persistence.domain.User;
import com.mayo.client.mayoclientapi.persistence.domain.type.GenderType;

public record CreateUserRequest(
        String email,
        GenderType gender,
        String name,
        String displayName,
        String phoneNumber,
        Boolean agreeMarketing,
        Boolean agreeTerms1,
        Boolean agreeTerms2,
        @JsonDeserialize(using = FirestoreTimestampDeserializer.class)
        Timestamp birthday
) {
    public User toEntity(String uid) {
        return User.builder()
                .uid(uid)
                .userid(uid)
                .email(email)
                .createdTime(Timestamp.now())
                .phoneNumber(phoneNumber)
                .agreeMarketing(agreeMarketing)
                .agreeTerms1(agreeTerms1)
                .agreeTerms2(agreeTerms2)
                .birthday(birthday)
                .build();
    }
}
