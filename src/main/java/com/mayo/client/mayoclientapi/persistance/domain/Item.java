package com.mayo.client.mayoclientapi.persistance.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class Item {

    @DocumentId
    @JsonProperty("item_id")
    private String itemId;

    @PropertyName("item_name")
    @JsonProperty("item_name")
    private String itemName;

    @PropertyName("item_description")
    @JsonProperty("item_description")
    private String itemDescription;

    @PropertyName("original_price")
    @JsonProperty("original_price")
    private Integer originalPrice;

    @PropertyName("sale_percent")
    @JsonProperty("sale_percent")
    private Double salePercent;

    @PropertyName("item_created")
    @JsonProperty("item_created")
    private Timestamp itemCreated;

    @PropertyName("item_modified")
    @JsonProperty("item_modified")
    private Timestamp itemModified;

    @PropertyName("item_quantity")
    @JsonProperty("item_quantity")
    private Integer itemQuantity;

    @PropertyName("item_on_sale")
    @JsonProperty("item_on_sale")
    private Boolean itemOnSale;

    @PropertyName("item_image")
    @JsonProperty("item_image")
    private String itemImage;

    @PropertyName("store_name")
    @JsonProperty("store_name")
    private String storeName;

    @PropertyName("store_address")
    @JsonProperty("store_address")
    private String storeAddress;

    @PropertyName("user_item_quantity")
    @JsonProperty("user_item_quantity")
    private Integer userItemQuantity;

    @PropertyName("sale_price")
    @JsonProperty("sale_price")
    private Double salePrice;

    @PropertyName("cooking_time")
    @JsonProperty("cooking_time")
    private Integer cookingTime;

    @PropertyName("additional_information")
    @JsonProperty("additional_information")
    private String additionalInformation;

    @PropertyName("store_ref")
    @JsonProperty("store_ref")
    private DocumentReference storeRef;

    @Builder
    public Item(String itemId, String itemName, String itemDescription, Integer originalPrice, Double salePercent, Timestamp itemCreated, Timestamp itemModified, Integer itemQuantity, Boolean itemOnSale, String itemImage, String storeName, String storeAddress, Integer userItemQuantity, Double salePrice, Integer cookingTime, String additionalInformation, DocumentReference storeRef) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.originalPrice = originalPrice;
        this.salePercent = salePercent;
        this.itemCreated = itemCreated;
        this.itemModified = itemModified;
        this.itemQuantity = itemQuantity;
        this.itemOnSale = itemOnSale;
        this.itemImage = itemImage;
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.userItemQuantity = userItemQuantity;
        this.salePrice = salePrice;
        this.cookingTime = cookingTime;
        this.additionalInformation = additionalInformation;
        this.storeRef = storeRef;
    }
}
