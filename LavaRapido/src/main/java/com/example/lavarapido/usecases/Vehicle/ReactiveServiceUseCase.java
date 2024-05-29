package com.example.lavarapido.usecases.Vehicle;

import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.service.Service;
import com.example.lavarapido.usecases.Service.ServiceDAO;
import com.example.lavarapido.usecases.utils.EntityNotFoundException;

public class ReactiveServiceUseCase {
    private final ServiceDAO serviceDAO;

    public ReactiveServiceUseCase(ServiceDAO serviceDAO) {
        this.serviceDAO = serviceDAO;
    }

    public boolean reactive(Service service) {

        String name  = service.getName();
        if (serviceDAO.findOneByName(name).isEmpty())
            throw new EntityNotFoundException("Service not found.");

        if (service.getStatus() == Status.INACTIVE){
            service.changeStatus(Status.ACTIVE);
            return serviceDAO.update(service);
        }

        throw new RuntimeException("Service is already active.");

    }
}
