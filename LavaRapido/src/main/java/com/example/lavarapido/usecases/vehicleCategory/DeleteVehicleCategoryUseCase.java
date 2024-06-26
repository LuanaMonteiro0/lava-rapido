package com.example.lavarapido.usecases.vehicleCategory;

import com.example.lavarapido.application.repository.daoimplements.ServicesPricesDaoJdbc;
import com.example.lavarapido.application.repository.daoimplements.VehicleDaoJdbc;
import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;
import com.example.lavarapido.usecases.utils.EntityNotFoundException;
import com.example.lavarapido.usecases.utils.ShowAlert;

public class DeleteVehicleCategoryUseCase {
    private final VehicleCategoryDAO vehicleCategoryDAO;
    private final ServicesPricesDaoJdbc servicesPricesDaoJdbc;
    private final VehicleDaoJdbc vehicleDaoJdbc;

    public DeleteVehicleCategoryUseCase(VehicleCategoryDAO vehicleCategoryDAO, ServicesPricesDaoJdbc servicesPricesDaoJdbc, VehicleDaoJdbc vehicleDaoJdbc) {
        this.vehicleCategoryDAO = vehicleCategoryDAO;
        this.servicesPricesDaoJdbc = servicesPricesDaoJdbc;
        this.vehicleDaoJdbc = vehicleDaoJdbc;
    }

    public boolean delete(VehicleCategory vehicleCategory) {
        if (vehicleCategory == null || vehicleCategoryDAO.findOne(vehicleCategory.getId()).isEmpty()) {
            ShowAlert.showErrorAlert("Categoria não encontrada.");
            throw new EntityNotFoundException("Categoria não encontrada.");
        }

        if (isCategoryRelatedToAnyVehicle(vehicleCategory) || isCategoryRelatedToAnyService(vehicleCategory)) {
            ShowAlert.showErrorAlert("A categoria não pode ser excluída porque está relacionada a um veículo ou serviço.");
            throw new IllegalStateException("A categoria não pode ser excluída porque está relacionada a um veículo ou serviço.");
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
