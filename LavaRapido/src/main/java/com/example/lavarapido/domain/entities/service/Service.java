package com.example.lavarapido.domain.entities.service;

import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Service {

    UUID uuid = UUID.randomUUID();
    private String id = uuid.toString();
    private String name;
    private final Map<VehicleCategory, Double> price = new HashMap<>();
    private Status status;

    public Service() {
        this.status = Status.ACTIVE;
    }

    public Service(String id, Status status, String name) {
        this(id, status, name, new HashMap<>());
    }

    public Service(String id, String name){
        this.id = id;
        this.name = name;
    }
    public Service(String id, Status status, String name, Map<VehicleCategory, Double> price) {
        this.id = id;
        this.status = status;
        this.name = name;
        if (price != null) {
            this.price.putAll(price);
        }
    }

    public Service(String name, Status status) {
        this.name = name;
        this.status = status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void changeStatus(Status status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public Map<VehicleCategory, Double> getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void setPrice(VehicleCategory category, Double price) {
        this.price.put(category, price);
    }

    public Status getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPriceForCategory(VehicleCategory category) { return this.price.get(category); }

}