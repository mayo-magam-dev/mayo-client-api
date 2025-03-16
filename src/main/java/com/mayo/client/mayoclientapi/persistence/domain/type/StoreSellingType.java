package com.mayo.client.mayoclientapi.persistence.domain.type;

import com.mayo.client.mayoclientapi.common.exception.ApplicationException;
import com.mayo.client.mayoclientapi.common.exception.payload.ErrorStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public enum StoreSellingType {

    TAKEOUT(1, "포장"),
    DINE_IN(2, "매장"),
    BOTH(3, "포장, 매장");

    private final long code;
    private final String description;

    StoreSellingType(long code, String description) {
        this.code = code;
        this.description = description;
    }

    public static String getDescriptionByCode(long code) {
        for (StoreSellingType type : StoreSellingType.values()) {
            if (type.code == code) {
                return type.description;
            }
        }
        throw new ApplicationException(
                ErrorStatus.toErrorStatus("해당하는 타입이 없습니다.", 404, LocalDateTime.now())
        );
    }
}
