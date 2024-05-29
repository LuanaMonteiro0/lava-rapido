package com.example.lavarapido.usecases.Service;

import com.example.lavarapido.domain.entities.service.Service;
import com.example.lavarapido.usecases.utils.EntityAlreadyExistsException;
import com.example.lavarapido.usecases.utils.Notification;
import com.example.lavarapido.usecases.utils.Validator;

public class CreateServiceUseCase {

    private final ServiceDAO serviceDAO;

    public CreateServiceUseCase(ServiceDAO serviceDAO) {
        this.serviceDAO = serviceDAO;
    }

    public String insert(Service service) {
        Validator<Service> validator = new ServiceInputRequestValidator();
        Notification notification = validator.validate(service);

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        String name = service.getName();
        if (serviceDAO.findOneByName(name).isPresent())
            throw new EntityAlreadyExistsException("This name is already in use.");

        return serviceDAO.create(service);
    }
}
