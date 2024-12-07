package com.mayo.client.mayoclientapi.persistance.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FAQType {

    UPDATE_INFO(11, "정보 수정"),
    DELETE_ACCOUNT(12, "회원 탈퇴"),
    STORE_PICKUP(13, "매장/픽업"),
    ORDER_HISTORY(14, "주문 내역"),
    ORDER_NOTIFICATION(15, "주문 알림"),
    MAIN_FEATURES(16, "앱 주요 기능"),
    HOW_TO_USE(17, "앱 이용 방법"),
    PAYMENT(18, "결제"),
    COUPON(19, "쿠폰"),
    EVENT(20, "이벤트"),
    ETC(21, "기타");

    private final int code;
    private final String text;

}
