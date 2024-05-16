package com.example.lavarapido.domain.entities.vehicle;

import com.example.lavarapido.domain.entities.general.Status;

public class Vehicle {

    private Plate plate;

    private VehicleCategory vehicleCategory;

    private Status status;

    private String model;

    private String color;

    private Long id;



    public Vehicle(
            Plate plate,
            VehicleCategory vehicleCategory,
            Status status,
            String model,
            String color,
            Long id) {

        this.plate = plate;
        this.vehicleCategory = vehicleCategory;
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

    public Plate getPlate() {
        return plate;
    }

    public VehicleCategory getVehicleCategory() {
        return vehicleCategory;
    }

    public void changeVehicleStatus(Status status) {
        this.status = status;
    }
}
