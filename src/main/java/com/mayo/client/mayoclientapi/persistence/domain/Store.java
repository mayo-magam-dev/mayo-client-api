package com.mayo.client.mayoclientapi.persistence.domain;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.firebase.database.PropertyName;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


@NoArgsConstructor
@Getter
@ToString
public class Store {

    @DocumentId
    private String id;

    @PropertyName("store_name")
    private String storeName;

    @PropertyName("open_state")
    private Boolean openState;

    @PropertyName("address")
    private String address;

    @PropertyName("store_image")
    private String storeImage;

    @PropertyName("store_mainImage")
    private String storeMainImage;

    @PropertyName("open_time")
    private String openTime;

    @PropertyName("close_time")
    private String closeTime;

    @PropertyName("sale_start")
    private String saleStart;

    @PropertyName("sale_end")
    private String saleEnd;

    @PropertyName("store_description")
    private String storeDescription;

    @PropertyName("store_number")
    private String storeNumber;

    @PropertyName("store_mapUrl")
    private String storeMapUrl;

    @PropertyName("origin_info")
    private String originInfo;

    @PropertyName("additional_comment")
    private String additionalComment;

    @PropertyName("open_day_of_week")
    private List<Integer> openDayOfWeek;

    @PropertyName("store_category")
    private Long storeCategory;

    @PropertyName("store_sellingType")
    private Long storeSellingType;

    @PropertyName("account_number")
    private String accountNumber;

    @Builder
    public Store(String id, String storeName, Boolean openState, String address, String storeImage, String storeMainImage, String openTime, String closeTime, String saleStart, String saleEnd, String storeDescription, String storeNumber, String storeMapUrl, String originInfo, String additionalComment, List<Integer> openDayOfWeek, Long storeCategory, Long storeSellingType, String accountNumber) {
        this.id = id;
        this.storeName = storeName;
        this.openState = openState;
        this.address = address;
        this.storeImage = storeImage;
        this.storeMainImage = storeMainImage;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.saleStart = saleStart;
        this.saleEnd = saleEnd;
        this.storeDescription = storeDescription;
        this.storeNumber = storeNumber;
        this.storeMapUrl = storeMapUrl;
        this.originInfo = originInfo;
        this.additionalComment = additionalComment;
        this.openDayOfWeek = openDayOfWeek;
        this.storeCategory = storeCategory;
        this.storeSellingType = storeSellingType;
        this.accountNumber = accountNumber;
    }
}
