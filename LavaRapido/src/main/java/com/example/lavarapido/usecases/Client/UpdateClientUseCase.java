package com.example.lavarapido.usecases.Client;

import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.usecases.utils.ShowAlert;
import com.example.lavarapido.usecases.utils.EntityNotFoundException;
import com.example.lavarapido.usecases.utils.Notification;
import com.example.lavarapido.usecases.utils.Validator;

import java.util.List;
import java.util.Optional;

public class UpdateClientUseCase {

    private final ClientDAO clientDAO;

    public UpdateClientUseCase(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public Optional<List<Client>> findClientsByName(String name) {
        return clientDAO.findByName(name);
    }

    public boolean update(Client client) {

        Validator<Client> validator = new ClientInputRequestValidator();
        Notification notification = validator.validate(client);

        if (notification.hasErrors()) {
            ShowAlert.showErrorAlert(notification.errorMessage());
            throw new IllegalArgumentException(notification.errorMessage());
        }

        String id = client.getId();
        if (clientDAO.findOne(id).isEmpty()) {
            ShowAlert.showErrorAlert("Cliente não encontrado");
            throw new EntityNotFoundException("Client not found.");
        }

        return clientDAO.update(client);
    }
}
