package com.example.lavarapido.application.view;
import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.service.Service;
import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;

public class ServiceView {
    private final Service service;
    private final VehicleCategory category;
    private final double price;

    public ServiceView(Service service, VehicleCategory category, double price) {
        this.service = service;
        this.category = category;
        this.price = price;
    }

    public String getName() {
        return service.getName();
    }

    public String getCategory() {
        return category.getName();
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return service.getStatus().toString();
    }

    public Service getService() {
        return service;
    }
}