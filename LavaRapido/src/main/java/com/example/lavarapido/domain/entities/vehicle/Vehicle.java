package com.example.lavarapido.domain.entities.vehicle;

import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.scheduling.Scheduling;

public class Vehicle {

    private Status status;

    private String model;

    private String color;

    private Long id;

    private Plate plate;

    //edition: add vehicleCategory
    private  VehicleCategory vehicleCategory;

    private Scheduling scheduling;

    public VehicleCategory getVehicleCategory() {
        return vehicleCategory;
    }

    public void setVehicleCategory(VehicleCategory vehicleCategory) {
        this.vehicleCategory = vehicleCategory;
    }

    public Vehicle(Status status, String model, String color, Long id) {
        this.status = status;
        this.model = model;
        this.color = color;
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void changeVehicleStatus(Status status) {
        this.status = status;
    }

    public Plate getPlate() {
        return plate;
    }

    public void setPlate(Plate plate) {
        this.plate = plate;
    }

    public Scheduling getScheduling() {
        return scheduling;
    }

    public void setScheduling(Scheduling scheduling) {
        this.scheduling = scheduling;
    }
}
