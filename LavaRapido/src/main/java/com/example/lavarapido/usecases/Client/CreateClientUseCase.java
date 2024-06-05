package com.example.lavarapido.usecases.Client;

import com.example.lavarapido.domain.entities.client.CPF;
import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.usecases.utils.EntityAlreadyExistsException;
import com.example.lavarapido.usecases.utils.Notification;
import com.example.lavarapido.usecases.utils.Validator;

import java.util.List;
import java.util.Optional;

public class CreateClientUseCase {

    private final ClientDAO clientDAO;

    public CreateClientUseCase(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public String insert(Client client) {
        Validator<Client> validator = new ClientInputRequestValidator();
        Notification notification = validator.validate(client);

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        CPF cpf = client.getCpf();
        if (clientDAO.findOneByCPF(cpf).isPresent())
            throw new EntityAlreadyExistsException("This CPF is already in use.");

        return clientDAO.create(client);
    }
}
