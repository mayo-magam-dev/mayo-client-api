package com.mayo.client.mayoclientapi.persistence.repository;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.mayo.client.mayoclientapi.common.exception.ApplicationException;
import com.mayo.client.mayoclientapi.common.exception.payload.ErrorStatus;
import com.mayo.client.mayoclientapi.persistence.domain.Banner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
@RequiredArgsConstructor
public class BannerRepository {

    public static final String COLLECTION_NAME = "banners";
    private final Firestore firestore;

    public List<Banner> getBanners() {

        Query query = firestore.collection(COLLECTION_NAME)
                .whereEqualTo("is_active", true);
        List<QueryDocumentSnapshot> documents = new ArrayList<>();
        List<Banner> bannerList = new ArrayList<>();

        try {
            QuerySnapshot querySnapshot = query.get().get();
            documents = querySnapshot.getDocuments();
        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(
                    ErrorStatus.toErrorStatus("통신 중 오류가 발생하였습니다.", 500, LocalDateTime.now())
            );
        }

        for (QueryDocumentSnapshot bannerDocument : documents) {
            Banner banner = fromDocument(bannerDocument);
            bannerList.add(banner);
        }

        return bannerList;
    }

    private Banner fromDocument(QueryDocumentSnapshot bannerDocument) {
        return Banner.builder()
                    .id(bannerDocument.getId())
                    .imageUrl(bannerDocument.getString("image_url"))
                    .isActive(Boolean.TRUE.equals(bannerDocument.getBoolean("is_active")))
                    .build();
    }
}
