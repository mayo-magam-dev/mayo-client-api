package com.mayo.client.mayoclientapi.presentation.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.cloud.Timestamp;
import com.mayo.client.mayoclientapi.common.serializer.DateFormatDeserializer;
import com.mayo.client.mayoclientapi.persistence.domain.User;

public record CreateUserRequest(
        String email,
        String gender,
        String name,
        String displayName,
        String phoneNumber,
        Boolean agreeMarketing,
        Boolean agreeTerms1,
        Boolean agreeTerms2,
        @JsonDeserialize(using = DateFormatDeserializer.class)
        Timestamp birthday
) {
    public User toEntity(String uid) {

        String genderType;

        if((gender).equalsIgnoreCase("MALE")) {
            genderType = "남자";
        } else if(gender.equalsIgnoreCase("FEMALE")) {
            genderType = "여자";
        } else {
            genderType = "미선택";
        }

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
                .displayName(displayName)
                .name(name)
                .gender(genderType)
                .build();
    }
}
