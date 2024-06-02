package com.example.lavarapido.usecases.Vehicle;

import com.example.lavarapido.domain.entities.vehicle.LicensePlate;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import com.example.lavarapido.usecases.utils.EntityAlreadyExistsException;
import com.example.lavarapido.usecases.utils.Notification;
import com.example.lavarapido.usecases.utils.Validator;

public class AddVehicleClientUseCase {
    private final VehicleDAO vehicleDAO;

    public AddVehicleClientUseCase(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public Long insert(Vehicle vehicle) {
        Validator<Vehicle> validator = new VehicleRequestValidator();
        Notification notification = validator.validate(vehicle);

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        LicensePlate licensePlate = vehicle.getPlate();
        if (vehicleDAO.findByLicensePlate(licensePlate).isPresent())
            throw new EntityAlreadyExistsException("This Plate is already in use.");

        return vehicleDAO.create(vehicle);
    }

}
