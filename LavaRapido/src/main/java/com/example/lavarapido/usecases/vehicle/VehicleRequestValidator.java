package com.example.lavarapido.usecases.vehicle;

import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import com.example.lavarapido.usecases.utils.Notification;
import com.example.lavarapido.usecases.utils.Validator;

public class VehicleRequestValidator extends Validator<Vehicle> {
    @Override
    public Notification validate(Vehicle vehicle) {

        Notification notification = new Notification();

        if (vehicle == null) {
            notification.addError("Vehicle is null");
            return notification;
        }

        if (nullOrEmpty(vehicle.getModel()))
            notification.addError("Model is null or empty");

        if (vehicle.getVehicleCategory() == null)
            notification.addError("Category is null");

        if (nullOrEmpty(vehicle.getColor()))
            notification.addError("Color is null or empty");

        if (vehicle.getPlate() == null)
            notification.addError("Plate is null");

        return notification;
    }
}
