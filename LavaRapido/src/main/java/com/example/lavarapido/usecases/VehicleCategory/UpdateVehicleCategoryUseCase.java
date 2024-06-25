package com.example.lavarapido.usecases.VehicleCategory;

import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;
import com.example.lavarapido.usecases.utils.EntityAlreadyExistsException;
import com.example.lavarapido.usecases.utils.EntityNotFoundException;
import com.example.lavarapido.usecases.utils.Notification;
import com.example.lavarapido.usecases.utils.Validator;

import java.util.Optional;

public class UpdateVehicleCategoryUseCase {
    private final VehicleCategoryDAO vehicleCategoryDAO;

    public UpdateVehicleCategoryUseCase(VehicleCategoryDAO vehicleCategoryDAO) {
        this.vehicleCategoryDAO = vehicleCategoryDAO;
    }

    public boolean update(VehicleCategory vehicleCategory) {
        Validator<VehicleCategory> validator = new VehicleCategoryRequestValidator();
        Notification notification = validator.validate(vehicleCategory);

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        String id = vehicleCategory.getId();
        if (vehicleCategoryDAO.findOne(id).isEmpty())
            throw new EntityNotFoundException("Category not found by ID.");

        String name = vehicleCategory.getName();
        if (vehicleCategoryDAO.findOneByName(name).isPresent())
            throw new EntityAlreadyExistsException("This category name is already in use. Please enter with a new name.");

        return vehicleCategoryDAO.update(vehicleCategory);
    }
}
