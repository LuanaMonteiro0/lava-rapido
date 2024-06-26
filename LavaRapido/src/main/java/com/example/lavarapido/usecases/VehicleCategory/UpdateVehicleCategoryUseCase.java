package com.example.lavarapido.usecases.VehicleCategory;

import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;
import com.example.lavarapido.usecases.utils.EntityAlreadyExistsException;
import com.example.lavarapido.usecases.utils.EntityNotFoundException;
import com.example.lavarapido.usecases.utils.Notification;
import com.example.lavarapido.usecases.utils.ShowAlert;
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

        if (notification.hasErrors()) {
            ShowAlert.showErrorAlert(notification.errorMessage());
            throw new IllegalArgumentException(notification.errorMessage());
        }

        String id = vehicleCategory.getId();
        Optional<VehicleCategory> existingCategory = vehicleCategoryDAO.findOne(id);
        if (existingCategory.isEmpty()) {
            ShowAlert.showErrorAlert("Categoria não encontrada pelo ID.");
            throw new EntityNotFoundException("Categoria não encontrada pelo ID.");
        }

        String name = vehicleCategory.getName();
        Optional<VehicleCategory> categoryWithName = vehicleCategoryDAO.findOneByName(name);
        if (categoryWithName.isPresent() && !categoryWithName.get().getId().equals(id)) {
            ShowAlert.showErrorAlert("Este nome de categoria já está em uso. Por favor, insira um novo nome.");
            throw new EntityAlreadyExistsException("Este nome de categoria já está em uso. Por favor, insira um novo nome.");
        }

        return vehicleCategoryDAO.update(vehicleCategory);
    }
}
