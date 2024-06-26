package com.example.lavarapido.usecases.Vehicle;

import com.example.lavarapido.domain.entities.vehicle.LicensePlate;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import com.example.lavarapido.usecases.utils.EntityAlreadyExistsException;
import com.example.lavarapido.usecases.utils.Notification;
import com.example.lavarapido.usecases.utils.ShowAlert;
import com.example.lavarapido.usecases.utils.Validator;

public class AddVehicleClientUseCase {
    private final VehicleDAO vehicleDAO;

    public AddVehicleClientUseCase(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public String insert(Vehicle vehicle) {
        Validator<Vehicle> validator = new VehicleRequestValidator();
        Notification notification = validator.validate(vehicle);

        if (notification.hasErrors()) {
            ShowAlert.showErrorAlert(notification.errorMessage());
            throw new IllegalArgumentException(notification.errorMessage());
        }

        LicensePlate licensePlate = vehicle.getPlate();
        if (vehicleDAO.findByLicensePlate(licensePlate).isPresent()) {
            ShowAlert.showErrorAlert("Esta placa já está em uso.");
            throw new EntityAlreadyExistsException("Esta placa já está em uso.");
        }

        return vehicleDAO.create(vehicle);
    }
}
