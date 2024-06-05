package com.example.lavarapido.usecases.Vehicle;

import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import com.example.lavarapido.usecases.utils.EntityNotFoundException;

public class ReactiveVehicleClientUseCase {
    private final VehicleDAO vehicleDAO;

    public ReactiveVehicleClientUseCase(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public boolean reactive(Vehicle vehicle) {
        if (vehicle == null || vehicleDAO.findByLicensePlate(vehicle.getPlate()).isEmpty())
            throw new EntityNotFoundException("Vehicle not found.");

        if (vehicle.getStatus() == Status.INACTIVE){
            vehicle.changeVehicleStatus(Status.ACTIVE);
            return vehicleDAO.update(vehicle);
        }

        throw new RuntimeException("Client is already activated.");

    }
}
