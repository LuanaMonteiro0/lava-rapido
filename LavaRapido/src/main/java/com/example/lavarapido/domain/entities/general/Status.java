package com.example.lavarapido.domain.entities.general;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Status {
    ACTIVE("Ativo"),
    INACTIVE("Inativo");

    private String label;

    Status(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public static Status toEnum(String value) {
        return Arrays.stream(Status.values())
                .filter(c -> value.equals(c.toString()))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
