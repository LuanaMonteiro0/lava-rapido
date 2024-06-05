package com.example.lavarapido.usecases.VehicleCategory;

import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;
import com.example.lavarapido.usecases.utils.EntityAlreadyExistsException;
import com.example.lavarapido.usecases.utils.Notification;
import com.example.lavarapido.usecases.utils.Validator;

public class InsertVehicleCategoryUseCase {

    private final VehicleCategoryDAO vehicleCategoryDAO;

    public InsertVehicleCategoryUseCase(VehicleCategoryDAO vehicleCategoryDAO) {
        this.vehicleCategoryDAO = vehicleCategoryDAO;
    }

    public String insert(VehicleCategory vehicleCategory) {
        Validator<VehicleCategory> validator = new VehicleCategoryRequestValidator();
        Notification notification = validator.validate(vehicleCategory);

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        String id = vehicleCategory.getId();
        if (vehicleCategoryDAO.findOne(id).isPresent())
            throw new EntityAlreadyExistsException("This category name is already in use.");

        return vehicleCategoryDAO.create(vehicleCategory);
    }
}
