package com.example.lavarapido.usecases.Service;

import com.example.lavarapido.domain.entities.service.Service;
import com.example.lavarapido.usecases.utils.EntityNotFoundException;
import com.example.lavarapido.usecases.utils.Notification;
import com.example.lavarapido.usecases.utils.Validator;

public class UpdateServiceUseCase {
    private final ServiceDAO serviceDAO;

    public UpdateServiceUseCase(ServiceDAO serviceDAO) {
        this.serviceDAO = serviceDAO;
    }

    public boolean update(Service service) {
        Validator<Service> validator = new ServiceInputRequestValidator();
        Notification notification = validator.validate(service);

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        String id = service.getId();
        if (serviceDAO.findOne(id).isEmpty())
            throw new EntityNotFoundException("Service not found by ID");

//        String name = service.getName();
//        if (serviceDAO.findOneByName(name).isPresent())
//            throw new EntityNotFoundException("Service name is already in use");

        return serviceDAO.update(service);
    }

}
