package com.example.lavarapido.domain.entities.general;

public enum Status {
    ACTIVE("Ativo"),
    INACTIVE("Inativo");

    private String label;

    Status(String label) {this.label = label;}

    @Override
    public String toString() {return label;}
}
