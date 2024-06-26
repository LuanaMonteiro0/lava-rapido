package com.example.lavarapido.usecases.scheduling;

import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.scheduling.Scheduling;
import com.example.lavarapido.domain.entities.service.Service;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import com.example.lavarapido.usecases.client.ClientDAO;
import com.example.lavarapido.usecases.service.ServiceDAO;
import com.example.lavarapido.usecases.vehicle.VehicleDAO;
import com.example.lavarapido.usecases.utils.*;

import java.util.List;
import java.util.Optional;

public class InsertSchedulingUseCase {
    private final SchedulingDAO schedulingDAO;
    private final ClientDAO clientDAO;
    private final VehicleDAO vehicleDAO;
    private final ServiceDAO serviceDAO;

    public InsertSchedulingUseCase(SchedulingDAO schedulingDAO, ClientDAO clientDAO, VehicleDAO vehicleDAO, ServiceDAO serviceDAO) {
        this.schedulingDAO = schedulingDAO;
        this.clientDAO = clientDAO;
        this.vehicleDAO = vehicleDAO;
        this.serviceDAO = serviceDAO;
    }

    public String insert(Scheduling scheduling) {
        Validator<Scheduling> validator = new SchedulingInputRequestValidator();
        Notification notification = validator.validate(scheduling);

        if (notification.hasErrors()) {
            ShowAlert.showErrorAlert(notification.errorMessage());
            throw new IllegalArgumentException(notification.errorMessage());
        }

        String id = scheduling.getId();
        if (schedulingDAO.findOne(id).isPresent())
            throw new EntityAlreadyExistsException("Este ID já está em uso.");

        Client client = scheduling.getClient();
        Optional<Client> clientScheduling = clientDAO.findOne(client.getId());
        if (clientScheduling.isEmpty()) {
            ShowAlert.showErrorAlert("Cliente não existe.");
            throw new EntityNotFoundException("Cliente não existe.");
        }

        Vehicle vehicle = scheduling.getVehicle();
        if (vehicleDAO.findByLicensePlate(vehicle.getPlate()).isEmpty()) {
            ShowAlert.showErrorAlert("Veículo não está registrado.");
            throw new EntityNotFoundException("Veículo não está registrado.");
        }

        List<Service> services = scheduling.getServices();
        for (Service service : services)
            if (serviceDAO.findOne(service.getId()).isEmpty()) {
                ShowAlert.showErrorAlert("Serviço não encontrado.");
                throw new EntityNotFoundException("Serviço não encontrado.");
            }

        return schedulingDAO.create(scheduling);
    }
}
