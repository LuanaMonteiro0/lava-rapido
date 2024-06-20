package com.example.lavarapido.domain.entities.vehicle;

import java.util.UUID;

public class VehicleCategory {

    private final String id;
    private String name;

    public VehicleCategory(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public VehicleCategory(String name) {
        this.name = name;
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}