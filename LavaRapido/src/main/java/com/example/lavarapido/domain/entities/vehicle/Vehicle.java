package com.example.lavarapido.domain.entities.vehicle;

import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.scheduling.Scheduling;

import java.util.UUID;

public class Vehicle {

    UUID uuid = UUID.randomUUID();
    private String id = uuid.toString();

    private Status status;

    private String model;

    private String color;

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

    public Vehicle(Status status, String model, String color, String id) {
        this.status = status;
        this.model = model;
        this.color = color;
        this.id = id;
    }

    public Vehicle(Plate plate, String color, String model, String id) {
        this.plate = plate;
        this.color = color;
        this.model = model;
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

    public String getId() {
        return id;
    }

    public Status getStatus() {
        return status;
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
