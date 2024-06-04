package com.example.lavarapido.usecases.VehicleCategory;

import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;
import com.example.lavarapido.usecases.utils.Notification;
import com.example.lavarapido.usecases.utils.Validator;

public class VehicleCategoryRequestValidator extends Validator<VehicleCategory> {
    @Override
    public Notification validate(VehicleCategory vehicleCategory) {

        Notification notification = new Notification();

        if (vehicleCategory == null) {
            notification.addError("Category is null");
            return notification;
        }

        if (nullOrEmpty(vehicleCategory.getName()))
            notification.addError("Name is null or empty");

        return notification;
    }
}
