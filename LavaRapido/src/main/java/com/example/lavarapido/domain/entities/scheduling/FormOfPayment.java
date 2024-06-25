package com.example.lavarapido.domain.entities.scheduling;

import com.example.lavarapido.domain.entities.general.Status;

import java.util.Arrays;

public enum FormOfPayment {
    PIX("PIX"),
    MONEY("Dinheiro"),
    CREDIT("Crédito"),
    DEBIT("Débito");

    private String label;

    FormOfPayment(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public static FormOfPayment toEnum(String value) {
        return Arrays.stream(FormOfPayment.values())
                .filter(c -> value.equals(c.toString()))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
