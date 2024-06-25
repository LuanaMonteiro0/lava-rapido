package com.example.lavarapido.usecases.VehicleCategory;

import com.example.lavarapido.application.repository.daoimplements.ServicesPricesDaoJdbc;
import com.example.lavarapido.application.repository.daoimplements.VehicleDaoJdbc;
import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;
import com.example.lavarapido.usecases.utils.EntityNotFoundException;

import java.util.Map;

public class DeleteVehicleCategoryUseCase {
    private final VehicleCategoryDAO vehicleCategoryDAO;
    private final ServicesPricesDaoJdbc servicesPricesDaoJdbc;
    private final VehicleDaoJdbc vehicleDaoJdbc;

    public DeleteVehicleCategoryUseCase(VehicleCategoryDAO vehicleCategoryDAO, ServicesPricesDaoJdbc servicePricesDaoJdbc, VehicleDaoJdbc vehicleDaoJdbc) {
        this.vehicleCategoryDAO = vehicleCategoryDAO;
        this.servicesPricesDaoJdbc = servicePricesDaoJdbc;
        this.vehicleDaoJdbc = vehicleDaoJdbc;
    }

    public boolean delete(VehicleCategory vehicleCategory) {
        if (vehicleCategory == null || vehicleCategoryDAO.findOne(vehicleCategory.getId()).isEmpty())
            throw new EntityNotFoundException("Category not found.");

        if (isCategoryRelatedToAnyVehicle(vehicleCategory) || isCategoryRelatedToAnyService(vehicleCategory)) {
            throw new IllegalStateException("Category cannot be deleted because it is related to a vehicle or service.");
        }

        return vehicleCategoryDAO.delete(vehicleCategory);
    }

    private boolean isCategoryRelatedToAnyVehicle(VehicleCategory vehicleCategory) {
        return vehicleDaoJdbc.findAll().stream()
                .anyMatch(vehicle -> vehicle.getVehicleCategory().getId().equals(vehicleCategory.getId()));
    }

    private boolean isCategoryRelatedToAnyService(VehicleCategory vehicleCategory) {
        return servicesPricesDaoJdbc.findAllVehicleCategoryIds().stream()
                .anyMatch(category -> category.equals(vehicleCategory.getId()));
    }
}
