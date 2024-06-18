package com.example.lavarapido.domain.entities.general;

import java.util.HashMap;
import java.util.Map;

public enum Status {
    ACTIVE("Ativo"),
    INACTIVE("Inativo");

    private String label;
    private static final Map<String, Status> BY_LABEL = new HashMap<>();

    static {
        for (Status status : values()) {
            BY_LABEL.put(status.label, status);
        }
    }

    Status(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public static Status fromLabel(String label) {
        return BY_LABEL.get(label);
    }
}
