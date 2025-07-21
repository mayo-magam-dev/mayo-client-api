package com.mayo.client.mayoclientapi.persistence.domain.type;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Weekday {
    MONDAY(1, "월"),
    TUESDAY(2, "화"),
    WEDNESDAY(3, "수"),
    THURSDAY(4, "목"),
    FRIDAY(5, "금"),
    SATURDAY(6, "토"),
    SUNDAY(7, "일");

    private final int code;
    private final String label;

    Weekday(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public static String labelOf(int code) {
        return Arrays.stream(values())
                .filter(w -> w.code == code)
                .findFirst()
                .map(w -> w.label)
                .orElse("?");
    }
}
