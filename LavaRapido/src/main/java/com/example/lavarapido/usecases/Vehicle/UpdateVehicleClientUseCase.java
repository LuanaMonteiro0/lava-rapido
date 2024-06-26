package com.example.lavarapido.usecases.Vehicle;

import com.example.lavarapido.domain.entities.vehicle.LicensePlate;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import com.example.lavarapido.usecases.utils.EntityNotFoundException;
import com.example.lavarapido.usecases.utils.Notification;
import com.example.lavarapido.usecases.utils.ShowAlert;
import com.example.lavarapido.usecases.utils.Validator;

public class UpdateVehicleClientUseCase {
    private final VehicleDAO vehicleDAO;

    public UpdateVehicleClientUseCase(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public boolean update(Vehicle vehicle) {
        Validator<Vehicle> validator = new VehicleRequestValidator();
        Notification notification = validator.validate(vehicle);

        if (notification.hasErrors()) {
            ShowAlert.showErrorAlert(notification.errorMessage());
            throw new IllegalArgumentException(notification.errorMessage());
        }

        LicensePlate licensePlate = vehicle.getPlate();
        if (vehicleDAO.findByLicensePlate(licensePlate).isEmpty()) {
            ShowAlert.showErrorAlert("Veículo não encontrado.");
            throw new EntityNotFoundException("Veículo não encontrado.");
        }

        return vehicleDAO.update(vehicle);
    }

}
