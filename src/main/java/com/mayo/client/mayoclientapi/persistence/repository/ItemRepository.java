package com.mayo.client.mayoclientapi.persistence.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.mayo.client.mayoclientapi.common.annotation.FirestoreTransactional;
import com.mayo.client.mayoclientapi.common.exception.ApplicationException;
import com.mayo.client.mayoclientapi.common.exception.payload.ErrorStatus;
import com.mayo.client.mayoclientapi.persistence.domain.Item;
import com.mayo.client.mayoclientapi.presentation.dto.response.ReadFirstItemResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Repository
@Slf4j
@RequiredArgsConstructor
@FirestoreTransactional
public class ItemRepository {

    private static final String COLLECTION_NAME = "items";
    private final Firestore firestore;

    public Optional<DocumentReference> findDocRefById(String itemId) {

        ApiFuture<DocumentSnapshot> future = firestore.collection(COLLECTION_NAME).document(itemId).get();

        try {
            return Optional.of(future.get().getReference());
        } catch (InterruptedException | ExecutionException e) {
            log.error("firebase 통신 중 오류가 발생했습니다.");
        }

        return Optional.empty();
    }

    public Optional<Item> findItemByDocRef(DocumentReference itemRef) {

        ApiFuture<DocumentSnapshot> future = itemRef.get();
        try {
            DocumentSnapshot documentSnapshot = future.get();
            if(documentSnapshot.exists()) {
                return Optional.ofNullable(fromDocument(documentSnapshot));
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();

    }

    public List<Item> findItemsByStoreId(String storeId) {

        List<Item> items = new ArrayList<>();

        DocumentReference storeDocumentId = firestore.collection("stores").document(storeId);
        CollectionReference itemsRef = firestore.collection("items");
        Query query = itemsRef.whereEqualTo("store_ref", storeDocumentId)
                .whereEqualTo("is_active", true);
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        QuerySnapshot querySnapshot = null;

        try {
            querySnapshot = querySnapshotApiFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("스토어로 아이템을 가져오는 중 에러가 발생하였습니다.", 400, LocalDateTime.now()));
        }

        for (QueryDocumentSnapshot itemDocument : querySnapshot.getDocuments()) {
            Item item = fromDocument(itemDocument);
            items.add(item);
        }

        return items;
    }

    public Double findMaxPercentItemByStoreId(String storeId) {

        DocumentReference storeDocumentId = firestore.collection("stores").document(storeId);
        CollectionReference itemsRef = firestore.collection("items");
        Query query = itemsRef.whereEqualTo("store_ref", storeDocumentId)
                .whereEqualTo("is_active", true);
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        QuerySnapshot querySnapshot = null;

        try {
            List<QueryDocumentSnapshot> documents = querySnapshotApiFuture.get().getDocuments();

            return documents.stream()
                    .map(doc -> {
                        Object value = doc.get("sale_percent");
                        if (value instanceof Double) {
                            return (Double) value;
                        } else {
                            return 0.0;
                        }
                    })
                    .max(Double::compareTo)
                    .orElse(0.0);

        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("스토어로 아이템을 가져오는 중 에러가 발생하였습니다.", 400, LocalDateTime.now()));
        }

    }

    public void updateItemsStateOutOfStock(String storeId) {

        CollectionReference itemRef = firestore.collection("items");
        DocumentReference storesDocumentId = firestore.collection("stores").document(storeId);
        QuerySnapshot querySnapshot = null;

        try {
            querySnapshot = itemRef.whereEqualTo("store_ref", storesDocumentId).get().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("해당 가게에 아이템이 없습니다.", 400, LocalDateTime.now()));
        }

        for (QueryDocumentSnapshot itemDocument : querySnapshot.getDocuments()) {
            DocumentReference item = itemRef.document(itemDocument.getId());
            item.update("item_on_sale", false, "item_quantity", 0);
        }
    }

    public void updateItemQuantityMinus(String itemId, Integer count) {

        try {
            DocumentReference docRef = firestore.collection("items").document(itemId);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot itemSnapshot = future.get();

            if (itemSnapshot.exists()) {
                Integer itemQuantity = itemSnapshot.get("item_quantity", Integer.class);

                if(itemQuantity != null && itemQuantity - count >= 0) {
                    Integer updatedItemQuantity = itemQuantity - count;
                    docRef.update("item_quantity", updatedItemQuantity);
                    if(updatedItemQuantity == 0) {
                        docRef.update("item_on_sale", false);
                    }
                } else {
                    throw new ApplicationException(
                            ErrorStatus.toErrorStatus("아이템이 너무 많습니다.", 400, LocalDateTime.now())
                    );
                }
            }
        }
        catch (ExecutionException | InterruptedException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("아이템을 찾는데 에러가 발생하였습니다.", 400, LocalDateTime.now()));
        }
    }

    public Optional<Item> findItemById(String itemId) {

        try {
            DocumentReference docRef = firestore.collection("items").document(itemId);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot itemSnapshot = future.get();

            if (itemSnapshot.exists()) {
                return Optional.ofNullable(fromDocument(itemSnapshot));
            }
        }
        catch (ExecutionException | InterruptedException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("아이템을 찾는데 에러가 발생하였습니다.", 400, LocalDateTime.now()));
        }

        return Optional.empty();
    }

    public ReadFirstItemResponse findFirstItemNamesFromCart(DocumentReference cart){

        DocumentSnapshot cartSnapshot = null;

        try {
            cartSnapshot = cart.get().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("cart객체에서 아이템을 가져오는데 실패했습니다.", 400, LocalDateTime.now()));
        }

        if(cartSnapshot.exists()) {
            DocumentReference itemRef = (DocumentReference) cartSnapshot.get("item");

            if(itemRef != null) {
                DocumentSnapshot itemSnapshot = null;
                try {
                    itemSnapshot = itemRef.get().get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new ApplicationException(ErrorStatus.toErrorStatus("cart로 아이템을 가져오는데 실패했습니다.", 400, LocalDateTime.now()));
                }

                if (itemSnapshot.exists()) {
                    return ReadFirstItemResponse.builder()
                            .itemName(itemSnapshot.getString("item_name"))
                            .itemQuantity(cartSnapshot.get("itemCount", Integer.class))
                            .build();
                }
            }
        }
        return ReadFirstItemResponse.builder()
                .itemName(" ")
                .itemQuantity(0)
                .build();
    }

    private Item fromDocument(DocumentSnapshot document) {
        return Item.builder()
                .itemId(document.getId())
                .itemName(document.getString("item_name"))
                .itemDescription(document.getString("item_description"))
                .originalPrice(document.get("original_price", Integer.class))
                .salePercent(document.getDouble("sale_percent"))
                .itemCreated(document.getTimestamp("item_created"))
                .itemModified(document.getTimestamp("time_modified"))
                .itemQuantity(document.get("item_quantity", Integer.class))
                .itemOnSale(document.getBoolean("item_on_sale"))
                .itemImage(document.getString("item_image"))
                .storeName(document.getString("store_name"))
                .storeAddress(document.getString("store-address"))
                .userItemQuantity(document.get("user_item_quantity", Integer.class))
                .salePrice(document.getDouble("sale_price"))
                .cookingTime(document.get("cooking_time",Integer.class))
                .additionalInformation(document.getString("additional_information"))
                .storeRef((DocumentReference) document.get("store_ref"))
                .build();
    }

    private List<Item> getRandomItems(List<Item> items, int n) {
        Random random = new SecureRandom();
        List<Item> randomItems = new ArrayList<>();
        Set<Integer> indices = new HashSet<>();

        int maxItems = Math.min(n, items.size());

        while (indices.size() < maxItems) {
            indices.add(random.nextInt(items.size()));
        }

        for (int index : indices) {
            randomItems.add(items.get(index));
        }

        return randomItems;
    }
}