package com.mayo.client.mayoclientapi.persistence.domain.type;

import com.mayo.client.mayoclientapi.common.exception.ApplicationException;
import com.mayo.client.mayoclientapi.common.exception.payload.ErrorStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public enum MealCategory {
    CHINESE(11),
    JAPANESE(12),
    KOREAN(13),
    KOREAN_SNACK(14),
    WESTERN(15),
    FAST_FOOD(16);

    private final long code;

    MealCategory(long code) {
        this.code = code;
    }

    public static MealCategory fromCode(long code) {
        for (MealCategory category : MealCategory.values()) {
            if (category.code == code) {
                return category;
            }
        }
        throw new ApplicationException(
                ErrorStatus.toErrorStatus("해당하는 카테고리가 없습니다.", 404, LocalDateTime.now())
        );
    }
}
