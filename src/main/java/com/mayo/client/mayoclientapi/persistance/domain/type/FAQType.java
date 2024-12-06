package com.mayo.client.mayoclientapi.persistance.domain.type;

public enum FAQType {

    UPDATE_INFO("정보 수정"),
    DELETE_ACCOUNT("회원 탈퇴"),
    STORE_PICKUP("매장/픽업"),
    ORDER_HISTORY("주문 내역"),
    ORDER_NOTIFICATION("주문 알림"),
    MAIN_FEATURES("앱 주요 기능"),
    HOW_TO_USE("앱 이용 방법"),
    PAYMENT("결제"),
    COUPON("쿠폰"),
    EVENT("이벤트"),
    ETC("기타");

    private final String text;

    FAQType(String text) {
        this.text = text;
    }
}
