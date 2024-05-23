package com.example.lavarapido.usecases.Vehicle;

import com.example.lavarapido.domain.entities.vehicle.Plate;
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

        Plate plate = vehicle.getPlate();
        if (vehicleDAO.findByPlate(plate).isPresent())
            throw new EntityAlreadyExistsException("This Plate is already in use.");

        return vehicleDAO.create(vehicle);
    }

}
