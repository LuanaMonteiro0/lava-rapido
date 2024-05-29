package com.example.lavarapido.usecases.Service;

import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.service.Service;
import com.example.lavarapido.usecases.utils.EntityNotFoundException;

public class InactivateServiceUseCase {
    private final ServiceDAO serviceDAO;

    public InactivateServiceUseCase(ServiceDAO serviceDAO) {
        this.serviceDAO = serviceDAO;
    }

    public boolean inactivate(Service service) {

        String name  = service.getName();
        if (serviceDAO.findOneByName(name).isEmpty())
            throw new EntityNotFoundException("Service not found.");

        if (service.getStatus() == Status.ACTIVE){
            service.changeStatus(Status.INACTIVE);
            return serviceDAO.update(service);
        }

        throw new RuntimeException("Service is already inactivated.");

    }
}
