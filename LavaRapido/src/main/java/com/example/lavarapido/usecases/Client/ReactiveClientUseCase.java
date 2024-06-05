package com.example.lavarapido.usecases.Client;

import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.usecases.utils.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public class ReactiveClientUseCase {
    private final ClientDAO clientDAO;

    public ReactiveClientUseCase(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public Optional<List<Client>> findClientsByName(String name) {
        return clientDAO.findByName(name);
    }

    public boolean reactive(Client client) {
        if (client == null || clientDAO.findOneByCPF(client.getCpf()).isEmpty())
            throw new EntityNotFoundException("Client not found.");

        if (client.getStatus() == Status.INACTIVE){
            client.setStatus(Status.ACTIVE);
            return clientDAO.update(client);
        }

        throw new RuntimeException("Client Status is already ACTIVE.");
    }
}
