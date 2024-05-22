package com.example.lavarapido.usecases.VehicleCaterory;

import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;
import com.example.lavarapido.usecases.utils.EntityNotFoundException;
import com.example.lavarapido.usecases.utils.Notification;
import com.example.lavarapido.usecases.utils.Validator;

import java.util.Optional;

public class UpdateVehicleCategoryUseCase {
    private final VehicleCategoryDAO vehicleCategoryDAO;

    public UpdateVehicleCategoryUseCase(VehicleCategoryDAO vehicleCategoryDAO) {
        this.vehicleCategoryDAO = vehicleCategoryDAO;
    }

    public Optional<VehicleCategory> findCategoriesByName(String name) {
        return vehicleCategoryDAO.findOneByName(name);
    }

    public boolean update(VehicleCategory vehicleCategory) {
        Validator<VehicleCategory> validator = new VehicleCategoryRequestValidator();
        Notification notification = validator.validate(vehicleCategory);

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        Long id = vehicleCategory.getId();
        if (vehicleCategoryDAO.findOne(id).isEmpty())
            throw new EntityNotFoundException("Client not found.");

        return vehicleCategoryDAO.update(vehicleCategory);
    }
}
