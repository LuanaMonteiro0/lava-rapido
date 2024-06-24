package com.example.lavarapido.domain.entities.scheduling;

import com.example.lavarapido.domain.entities.general.Status;

import java.util.Arrays;

public enum SchedulingStatus {
    PENDING("Pendente"),
    PAID("Pago"),
    ABSENT("Ausente");

    private String label;

    SchedulingStatus(String label) {
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
