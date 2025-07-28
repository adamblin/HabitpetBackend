package com.habitpet.app.habitpetbackend.domain.enums;

import java.util.Arrays;

public enum ActionType {
    FEED,
    CLEAN,
    PET;

    public static ActionType fromString(String value) {
        return Arrays.stream(values())
                .filter(v -> v.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Action inválida: " + value));
    }
}

