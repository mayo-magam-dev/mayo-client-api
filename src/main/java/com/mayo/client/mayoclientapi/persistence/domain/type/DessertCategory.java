package com.mayo.client.mayoclientapi.persistence.domain.type;

import com.mayo.client.mayoclientapi.common.exception.ApplicationException;
import com.mayo.client.mayoclientapi.common.exception.payload.ErrorStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public enum DessertCategory {

    CAFE(31),
    BAKERY(32),
    FRUIT(33);

    private final long code;

    DessertCategory(long code) {
        this.code = code;
    }

    public static DessertCategory fromCode(long code) {
        for (DessertCategory category : DessertCategory.values()) {
            if (category.code == code) {
                return category;
            }
        }
        throw new ApplicationException(
                ErrorStatus.toErrorStatus("해당하는 카테고리가 없습니다.", 404, LocalDateTime.now())
        );
    }
}
