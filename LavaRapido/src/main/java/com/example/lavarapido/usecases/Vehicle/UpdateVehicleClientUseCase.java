package com.example.lavarapido.usecases.Vehicle;

import com.example.lavarapido.domain.entities.vehicle.Plate;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import com.example.lavarapido.usecases.utils.EntityNotFoundException;
import com.example.lavarapido.usecases.utils.Notification;
import com.example.lavarapido.usecases.utils.Validator;

public class UpdateVehicleClientUseCase {
    private final VehicleDAO vehicleDAO;

    public UpdateVehicleClientUseCase(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public boolean update(Vehicle vehicle) {
        Validator<Vehicle> validator = new VehicleRequestValidator();
        Notification notification = validator.validate(vehicle);

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        Plate plate = vehicle.getPlate();
        if (vehicleDAO.findByPlate(plate).isEmpty())
            throw new EntityNotFoundException("Vehicle not found.");

        return vehicleDAO.update(vehicle);
    }
}
