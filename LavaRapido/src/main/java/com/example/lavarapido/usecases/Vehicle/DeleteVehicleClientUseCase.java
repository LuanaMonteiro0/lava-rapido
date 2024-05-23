package com.example.lavarapido.usecases.Vehicle;

import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import com.example.lavarapido.usecases.utils.EntityNotFoundException;

public class DeleteVehicleClientUseCase {
    private final VehicleDAO vehicleDAO;

    public DeleteVehicleClientUseCase(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public boolean delete(Vehicle vehicle) {
        if (vehicle == null || vehicleDAO.findByPlate(vehicle.getPlate()).isEmpty())
            throw new EntityNotFoundException("Category not found.");

        if (vehicle.getScheduling() == null)
            return vehicleDAO.delete(vehicle);

        vehicle.changeVehicleStatus(Status.INACTIVE);
        return vehicleDAO.update(vehicle);
    }
}
