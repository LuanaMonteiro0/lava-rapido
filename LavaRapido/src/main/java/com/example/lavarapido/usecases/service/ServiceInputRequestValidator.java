package com.example.lavarapido.usecases.service;

import com.example.lavarapido.domain.entities.service.Service;
import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;
import com.example.lavarapido.usecases.utils.Notification;
import com.example.lavarapido.usecases.utils.Validator;

import java.util.Map;

public class ServiceInputRequestValidator extends Validator<Service> {
    @Override
    public Notification validate(Service service) {
        Notification notification = new Notification();

        if (service == null) {
            notification.addError("Service is null.");
            return notification;
        }

        if (nullOrEmpty(service.getName()))
            notification.addError("Name is null or empty.");

        Map<VehicleCategory, Double> prices = service.getPrice();
        if (prices == null || prices.isEmpty()) {
            notification.addError("Prices for vehicle categories are required.");
        } else {
            for (Map.Entry<VehicleCategory, Double> entry : prices.entrySet()) {
                if (entry.getValue() == null || entry.getValue() < 0 || entry.getValue().isNaN()) {
                    notification.addError("Price for category " + entry.getKey() + "is invalid.");
                }
            }
        }

        return notification;
    }
}
