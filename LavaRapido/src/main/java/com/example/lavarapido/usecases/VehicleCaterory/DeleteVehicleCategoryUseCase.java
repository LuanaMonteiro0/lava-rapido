package com.example.lavarapido.usecases.VehicleCaterory;

import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;
import com.example.lavarapido.usecases.utils.EntityNotFoundException;

public class DeleteVehicleCategoryUseCase {
    private final VehicleCategoryDAO vehicleCategoryDAO;

    public DeleteVehicleCategoryUseCase(VehicleCategoryDAO vehicleCategoryDAO) {
        this.vehicleCategoryDAO = vehicleCategoryDAO;
    }

    public boolean delete(VehicleCategory vehicleCategory) {
        if (vehicleCategory == null || vehicleCategoryDAO.findOne(vehicleCategory.getId()).isEmpty())
            throw new EntityNotFoundException("Category not found.");
        return vehicleCategoryDAO.delete(vehicleCategory);
    }
}
