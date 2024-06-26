package com.example.lavarapido.usecases.Vehicle;

import com.example.lavarapido.application.repository.daoimplements.SchedulingDaoJdbc;
import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.scheduling.Scheduling;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import com.example.lavarapido.usecases.utils.EntityNotFoundException;
import com.example.lavarapido.usecases.utils.ShowAlert;

import java.util.List;

public class DeleteVehicleClientUseCase {
    private final VehicleDAO vehicleDAO;
    private final SchedulingDaoJdbc schedulingDaoJdbc;

    public DeleteVehicleClientUseCase(VehicleDAO vehicleDAO, SchedulingDaoJdbc schedulingDaoJdbc) {
        this.vehicleDAO = vehicleDAO;
        this.schedulingDaoJdbc = schedulingDaoJdbc;
    }

    public boolean delete(Vehicle vehicle) {
        if (vehicle == null || vehicleDAO.findByLicensePlate(vehicle.getPlate()).isEmpty()) {
            ShowAlert.showErrorAlert("Veículo não encontrado.");
            throw new EntityNotFoundException("Veículo não encontrado.");
        }

        List<Scheduling> allSchedulings = schedulingDaoJdbc.findAll();
        boolean hasSchedulings = allSchedulings.stream()
                .anyMatch(scheduling -> scheduling.getVehicle().getId().equals(vehicle.getId()));

        if (!hasSchedulings) {
            return vehicleDAO.delete(vehicle);
        }

        ShowAlert.showInfoAlert("Não é possível deletar um veículo com agendamentos. O veículo será inativado.");
        vehicle.setStatus(Status.INACTIVE);
        return vehicleDAO.update(vehicle);
    }
}
