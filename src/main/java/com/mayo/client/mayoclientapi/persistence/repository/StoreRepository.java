package com.mayo.client.mayoclientapi.persistence.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.mayo.client.mayoclientapi.common.annotation.FirestoreTransactional;
import com.mayo.client.mayoclientapi.common.exception.ApplicationException;
import com.mayo.client.mayoclientapi.common.exception.payload.ErrorStatus;
import com.mayo.client.mayoclientapi.persistence.domain.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Repository
@RequiredArgsConstructor
public class StoreRepository {

    private static final String COLLECTION_NAME = "stores";
    private final Firestore firestore;

    public List<Store> findOpenStores() {

        List<Store> list = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION_NAME)
                .whereEqualTo("open_state", true)
                .whereEqualTo("is_active", true)
                .get();

        try {
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for(QueryDocumentSnapshot document : documents) {
                list.add(fromDocument(document));
            }

            list.sort(Comparator.comparing(Store::getStoreName));

        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("알 수 없는 문제가 발생하였습니다.", 500, LocalDateTime.now()));
        }

        return list;
    }

    public Optional<DocumentReference> findDocRefById(String storeId) {

        ApiFuture<DocumentSnapshot> future = firestore.collection(COLLECTION_NAME).document(storeId).get();

        try {
           return Optional.of(future.get().getReference());
        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(
                    ErrorStatus.toErrorStatus("알 수 없는 문제가 발생하였습니다.", 500, LocalDateTime.now())
            );
        }
    }

    public List<Store> findRandomOpenStores() {

        List<Store> list = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION_NAME)
                .whereEqualTo("open_state", true)
                .whereEqualTo("is_active", true)
                .get();

        try {
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for(QueryDocumentSnapshot document : documents) {
                list.add(fromDocument(document));
            }

        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("알 수 없는 문제가 발생하였습니다.", 500 ,LocalDateTime.now()));
        }

        return getRandomStores(list, 3);
    }

    public Optional<Store> findByStoreId(String storeId) {

        DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(storeId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = null;

        try {
            document = future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("해당 가게를 찾는데 오류가 발생하였습니다", 400, LocalDateTime.now()));
        }

        return Optional.ofNullable(fromDocument(document));
    }

    public List<Store> findAll() {

        List<Store> list = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION_NAME)
                .whereEqualTo("is_active", true).get();

        try {
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for(QueryDocumentSnapshot document : documents) {
                list.add(fromDocument(document));
            }

            list.sort(Comparator.comparing(Store::getStoreName));

        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("알 수 없는 문제가 발생하였습니다.", 500 ,LocalDateTime.now()));
        }

        return list;
    }

    public List<Store> findByCategory(Long category) {

        List<Store> list = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION_NAME)
                .whereEqualTo("is_active", true)
                .whereEqualTo("store_category", category)
                .get();

        try {
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for(QueryDocumentSnapshot document : documents) {
                list.add(fromDocument(document));
            }

            list.sort(Comparator.comparing(Store::getStoreName));

        } catch (ExecutionException | InterruptedException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("알 수 없는 문제가 발생하였습니다.", 500 ,LocalDateTime.now()));
        }

        return list;
    }

    public List<Store> findPartnerStore() {

        List<Store> list = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION_NAME)
                .whereEqualTo("is_active", true)
                .whereEqualTo("is_partner", true)
                .get();

        try {
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for(QueryDocumentSnapshot document : documents) {
                list.add(fromDocument(document));
            }

            list.sort(Comparator.comparing(Store::getStoreName));

        } catch (ExecutionException | InterruptedException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("알 수 없는 문제가 발생하였습니다.", 500 ,LocalDateTime.now()));
        }

        return list;
    }

    public Optional<Store> findByDocRef(DocumentReference docRef) {

        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = null;

        try {
            document = future.get();
            return Optional.of(fromDocument(document));
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Store> getRandomStores(List<Store> stores, int n) {
        Random random = new SecureRandom();
        List<Store> randomStores = new ArrayList<>();
        Set<Integer> indices = new HashSet<>();

        int maxItems = Math.min(n, stores.size());

        while (indices.size() < maxItems) {
            indices.add(random.nextInt(stores.size()));
        }

        for (int index : indices) {
            randomStores.add(stores.get(index));
        }

        return randomStores;
    }

    private Store fromDocument(DocumentSnapshot document) {
        List<Integer> openDayOfWeek = Optional.ofNullable((List<?>) document.get("open_day_of_week"))
            .orElse(List.of()).stream()
            .map(v -> ((Number) v).intValue())
            .toList();

        return Store.builder()
                .id(document.getId())
                .storeName(document.getString("store_name"))
                .openState(document.getBoolean("open_state"))
                .address(document.getString("address"))
                .storeImage(document.getString("store_image"))
                .openTime(document.getString("open_time"))
                .closeTime(document.getString("close_time"))
                .saleStart(document.getString("sale_start"))
                .saleEnd(document.getString("sale_end"))
                .storeDescription(document.getString("store_description"))
                .storeNumber(document.getString("store_number"))
                .storeMapUrl(document.getString("store_map_url"))
                .originInfo(document.getString("origin_info"))
                .additionalComment(document.getString("additional_comment"))
                .storeSellingType(document.getLong("store_sellingType"))
                .storeCategory((document.getLong("store_category")))
                .storeMainImage(document.getString("store_mainImage"))
                .accountNumber(document.getString("account_number"))
                .openDayOfWeek(openDayOfWeek)
                .build();
    }
}
