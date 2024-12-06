package com.mayo.client.mayoclientapi.presentation.dto.request;

import com.google.cloud.Timestamp;
import lombok.Builder;

@Builder
public record CreateClientPushNotificationRequest(
        String notificationImageUrl,
        String notificationText,
        String notificationTitle,
        int numSent,
        String sender,
        Boolean status,
        Timestamp timestamp
) {
}
