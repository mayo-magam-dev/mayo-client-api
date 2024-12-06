package com.mayo.client.mayoclientapi.presentation.dto.request;

public record CreateFCMTokenRequest(
        String userId,
        String fcmToken
) {
}
