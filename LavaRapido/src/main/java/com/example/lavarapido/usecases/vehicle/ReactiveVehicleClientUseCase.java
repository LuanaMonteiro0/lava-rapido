package com.example.lavarapido.usecases.vehicle;

import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import com.example.lavarapido.usecases.utils.EntityNotFoundException;
import com.example.lavarapido.usecases.utils.ShowAlert;

public class ReactiveVehicleClientUseCase {
    private final VehicleDAO vehicleDAO;

    public ReactiveVehicleClientUseCase(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public boolean reactive(Vehicle vehicle) {
        if (vehicle == null || vehicleDAO.findByLicensePlate(vehicle.getPlate()).isEmpty()) {
            ShowAlert.showErrorAlert("Veículo não encontrado.");
            throw new EntityNotFoundException("Veículo não encontrado.");
        }

        if (vehicle.getStatus() == Status.INACTIVE) {
            vehicle.changeVehicleStatus(Status.ACTIVE);
            return vehicleDAO.update(vehicle);
        }

        ShowAlert.showErrorAlert("O veículo já está ativado.");
        throw new RuntimeException("O veículo já está ativado.");
    }
}
