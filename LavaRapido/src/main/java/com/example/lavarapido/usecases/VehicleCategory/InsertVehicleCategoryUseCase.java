package com.example.lavarapido.usecases.VehicleCategory;

import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;
import com.example.lavarapido.usecases.utils.EntityAlreadyExistsException;
import com.example.lavarapido.usecases.utils.Notification;
import com.example.lavarapido.usecases.utils.ShowAlert;
import com.example.lavarapido.usecases.utils.Validator;

public class InsertVehicleCategoryUseCase {

    private final VehicleCategoryDAO vehicleCategoryDAO;

    public InsertVehicleCategoryUseCase(VehicleCategoryDAO vehicleCategoryDAO) {
        this.vehicleCategoryDAO = vehicleCategoryDAO;
    }

    public String insert(VehicleCategory vehicleCategory) {
        Validator<VehicleCategory> validator = new VehicleCategoryRequestValidator();
        Notification notification = validator.validate(vehicleCategory);

        if (notification.hasErrors()) {
            ShowAlert.showErrorAlert(notification.errorMessage());
            throw new IllegalArgumentException(notification.errorMessage());
        }

        String name = vehicleCategory.getName();
        if (vehicleCategoryDAO.findOneByName(name).isPresent()) {
            ShowAlert.showErrorAlert("Este nome de categoria já está em uso.");
            throw new EntityAlreadyExistsException("Este nome de categoria já está em uso.");
        }

        return vehicleCategoryDAO.create(vehicleCategory);
    }
}
