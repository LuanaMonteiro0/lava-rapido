package com.example.lavarapido.domain.entities.service;

import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service {

    private final long id;
    private final String name;
    private final Map<VehicleCategory, Double> price = new HashMap<>();

    private List<VehicleCategory> vehicleCategories;
    private Status status; //colocar um status padrão -> verificar se começa ATIVO ou INATIVO (exemplo no construtor)

    public Service(long id, String name) {
        this.id = id;
        this.name = name;
        // this.status = Status.ACTIVE; ?
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
