package com.example.lavarapido.usecases.Client;

import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.usecases.utils.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public class DeleteClientUseCase {

    private final ClientDAO clientDAO;

    public DeleteClientUseCase(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public Optional<List<Client>> findClientsByName(String name) {
        return clientDAO.findByName(name);
    }

    public boolean delete(Client client) {
        if (client == null || clientDAO.findOneByCPF(client.getCpf()).isEmpty())
            throw new EntityNotFoundException("Client not found.");


        if (client.getSchedulings().isEmpty())
           return clientDAO.delete(client);

        client.setStatus(Status.INACTIVE);
        return clientDAO.update(client);
    }

}
