package com.mayo.client.mayoclientapi.persistence.domain;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.firebase.database.PropertyName;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Banner {

    @DocumentId
    private String id;

    @PropertyName("image_url")
    private String imageUrl;

    @PropertyName("is_active")
    private boolean isActive;

    @Builder
    public Banner(String id, String imageUrl, boolean isActive) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.isActive = isActive;
    }
}
