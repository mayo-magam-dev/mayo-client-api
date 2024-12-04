package com.mayo.client.mayoclientapi.persistance.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.mayo.client.mayoclientapi.common.exception.ApplicationException;
import com.mayo.client.mayoclientapi.common.exception.payload.ErrorStatus;
import com.mayo.client.mayoclientapi.persistance.domain.Item;
import org.springframework.stereotype.Repository;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Repository
public class ItemRepository {

    public List<Item> findItemsByStoreId(String storeId) {

        List<Item> items = new ArrayList<>();

        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference storeDocumentId = firestore.collection("stores").document(storeId);
        CollectionReference itemsRef = firestore.collection("items");
        Query query = itemsRef.whereEqualTo("store_ref", storeDocumentId);
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

    public void updateItemsStateOutOfStock(String storeId) {

        Firestore firestore = FirestoreClient.getFirestore();
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

        Firestore db = FirestoreClient.getFirestore();

        try {
            DocumentReference docRef = db.collection("items").document(itemId);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot itemSnapshot = future.get();

            if (itemSnapshot.exists()) {
                Integer itemQuantity = itemSnapshot.get("item_quantity", Integer.class);

                if(itemQuantity != null && itemQuantity - count > 0) {
                    Integer updatedItemQuantity = itemQuantity - count;
                    docRef.update("item_quantity", updatedItemQuantity);
                }
            }
        }
        catch (ExecutionException | InterruptedException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("아이템을 찾는데 에러가 발생하였습니다.", 400, LocalDateTime.now()));
        }
    }

    public Optional<Item> findItemById(String itemId) {
        Firestore db = FirestoreClient.getFirestore();

        try {
            DocumentReference docRef = db.collection("items").document(itemId);
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