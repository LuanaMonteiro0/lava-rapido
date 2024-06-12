package com.example.lavarapido.usecases.Client;

import com.example.lavarapido.domain.entities.client.CPF;
import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.usecases.utils.EntityAlreadyExistsException;
import com.example.lavarapido.usecases.utils.Notification;
import com.example.lavarapido.usecases.utils.Validator;

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
        clientDAO.findOneByCPF(cpf)
                .orElseThrow( ()-> new EntityAlreadyExistsException("This CPF is already in use."));


        return clientDAO.create(client);
    }

}
