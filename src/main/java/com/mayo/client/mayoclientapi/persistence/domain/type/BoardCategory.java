package com.mayo.client.mayoclientapi.persistence.domain.type;

import lombok.Getter;

@Getter
public enum BoardCategory {
    TERMS(0),
    NOTICE(1),
    EVENT(2),
    TERMSDETAIL(3);

    private final int state;

    BoardCategory(int state) {
        this.state = state;
    }
}
