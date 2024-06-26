package com.example.lavarapido.usecases.service;

import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.service.Service;
import com.example.lavarapido.usecases.utils.EntityNotFoundException;
import com.example.lavarapido.usecases.utils.ShowAlert;

public class InactivateServiceUseCase {
    private final ServiceDAO serviceDAO;

    public InactivateServiceUseCase(ServiceDAO serviceDAO) {
        this.serviceDAO = serviceDAO;
    }

    public boolean inactivate(Service service) {

        String name = service.getName();
        if (serviceDAO.findOneByName(name).isEmpty()) {
            ShowAlert.showErrorAlert("Serviço não encontrado.");
            throw new EntityNotFoundException("Serviço não encontrado.");
        }

        if (service.getStatus() == Status.ACTIVE) {
            service.changeStatus(Status.INACTIVE);
            return serviceDAO.update(service);
        }

        ShowAlert.showErrorAlert("O serviço já está inativo.");
        throw new RuntimeException("O serviço já está inativo.");
    }
}