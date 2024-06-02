package com.example.lavarapido.usecases.Scheduling;

import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.scheduling.Scheduling;
import com.example.lavarapido.domain.entities.service.Service;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import com.example.lavarapido.usecases.Client.ClientDAO;
import com.example.lavarapido.usecases.Service.ServiceDAO;
import com.example.lavarapido.usecases.Vehicle.VehicleDAO;
import com.example.lavarapido.usecases.utils.EntityAlreadyExistsException;
import com.example.lavarapido.usecases.utils.EntityNotFoundException;
import com.example.lavarapido.usecases.utils.Notification;
import com.example.lavarapido.usecases.utils.Validator;

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

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        String id = scheduling.getId();
        if (schedulingDAO.findOne(id).isPresent())
            throw new EntityAlreadyExistsException("This ID is already in use.");

        Client client  = scheduling.getClient();
        Optional<Client> clientScheduling = clientDAO.findOne(client.getId());
        if (clientScheduling.isEmpty())
            throw new EntityNotFoundException("Client does not exist.");
        if (clientScheduling.get().getStatus() == Status.INACTIVE)
            throw new IllegalArgumentException("Client is inactive.");

        Vehicle vehicle = scheduling.getVehicle();
        if (vehicleDAO.findByPlate(vehicle.getPlate()).isEmpty())
            throw new EntityNotFoundException("Vehicle is not registered.");

        List<Service> services = scheduling.getServices();
        for (Service service : services)
            if (serviceDAO.findOne(service.getId()).isEmpty())
                throw new EntityNotFoundException("Service not found.");

        return schedulingDAO.create(scheduling);
    }
}
