package com.mayo.client.mayoclientapi.presentation.dto.request;

import com.mayo.client.mayoclientapi.persistence.domain.type.DeviceType;

public record CreateFCMTokenRequest(
        DeviceType deviceType,
        String fcmToken
) {
}
