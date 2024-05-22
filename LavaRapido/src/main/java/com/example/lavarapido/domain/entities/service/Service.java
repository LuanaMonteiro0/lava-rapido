package com.example.lavarapido.domain.entities.service;

import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service {

    private Integer id;
    private String name;
    private final Map<VehicleCategory, Double> price = new HashMap<>();

    private List<VehicleCategory> vehicleCategories;
    private Status status;

    public Service(Status status) {
        status = Status.ACTIVE;
    }

    public Service(Status status, String name) {
        this(null, status, name);
    }

    public Service(Integer id, Status status, String name) {
        this.id = id;
        this.status = status;
        this.name = name;
    }

    public void changeStatus(Status status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public Map<VehicleCategory, Double> getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
