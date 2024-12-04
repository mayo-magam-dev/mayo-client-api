package com.mayo.client.mayoclientapi.persistance.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.mayo.client.mayoclientapi.common.exception.ApplicationException;
import com.mayo.client.mayoclientapi.common.exception.payload.ErrorStatus;
import com.mayo.client.mayoclientapi.persistance.domain.Store;
import org.springframework.stereotype.Repository;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Repository
public class StoreRepository {

    private static final String COLLECTION_NAME = "stores";

    public List<Store> findOpenStores() {

        List<Store> list = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME)
                .whereEqualTo("open_state", true)
                .get();

        try {
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for(QueryDocumentSnapshot document : documents) {
                list.add(fromDocument(document));
            }

        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("알 수 없는 문제가 발생하였습니다.", 500 ,LocalDateTime.now()));
        }

        return list;
    }

    public List<Store> findRandomOpenStores() {

        List<Store> list = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME)
                .whereEqualTo("open_state", true)
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

        Firestore db = FirestoreClient.getFirestore();
        DocumentReference documentReference = db.collection(COLLECTION_NAME).document(storeId);
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
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();

        try {
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for(QueryDocumentSnapshot document : documents) {
                list.add(fromDocument(document));
            }

        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("알 수 없는 문제가 발생하였습니다.", 500 ,LocalDateTime.now()));
        }

        return list;
    }

    public List<Store> findByCategory(Long category) {

        List<Store> list = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();

        try {
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for(QueryDocumentSnapshot document : documents) {
                Store store = fromDocument(document);
                if(Objects.equals(store.getStoreCategory(), category)) {
                    list.add(store);
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("알 수 없는 문제가 발생하였습니다.", 500 ,LocalDateTime.now()));
        }

        return list;
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
                .build();
    }
}
