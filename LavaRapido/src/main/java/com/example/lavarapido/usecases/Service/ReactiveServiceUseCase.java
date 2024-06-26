package com.example.lavarapido.usecases.Service;

import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.service.Service;
import com.example.lavarapido.usecases.Service.ServiceDAO;
import com.example.lavarapido.usecases.utils.EntityNotFoundException;
import com.example.lavarapido.usecases.utils.ShowAlert;

public class ReactiveServiceUseCase {
    private final ServiceDAO serviceDAO;

    public ReactiveServiceUseCase(ServiceDAO serviceDAO) {
        this.serviceDAO = serviceDAO;
    }

    public boolean reactive(Service service) {

        String name = service.getName();
        if (serviceDAO.findOneByName(name).isEmpty()) {
            ShowAlert.showErrorAlert("Serviço não encontrado.");
            throw new EntityNotFoundException("Serviço não encontrado.");
        }

        if (service.getStatus() == Status.INACTIVE) {
            service.changeStatus(Status.ACTIVE);
            return serviceDAO.update(service);
        }

        ShowAlert.showErrorAlert("O serviço já está ativo.");
        throw new RuntimeException("O serviço já está ativo.");
    }
}
