package com.example.lavarapido.domain.entities.vehicle;

import java.util.UUID;

public class VehicleCategory {

    UUID uuid = UUID.randomUUID();
    private String id = uuid.toString();

    private String name;

    public VehicleCategory(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public VehicleCategory(String name) {
        this.name = name;
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
}
