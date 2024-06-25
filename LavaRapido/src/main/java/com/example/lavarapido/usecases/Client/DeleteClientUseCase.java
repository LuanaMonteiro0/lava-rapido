package com.example.lavarapido.usecases.Client;

import com.example.lavarapido.application.repository.daoimplements.SchedulingDaoJdbc;
import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.scheduling.Scheduling;
import com.example.lavarapido.usecases.utils.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public class DeleteClientUseCase {

    private final ClientDAO clientDAO;
    private final SchedulingDaoJdbc schedulingDaoJdbc;

    public DeleteClientUseCase(ClientDAO clientDAO, SchedulingDaoJdbc schedulingDaoJdbc) {
        this.clientDAO = clientDAO;
        this.schedulingDaoJdbc = schedulingDaoJdbc;
    }

    public Optional<List<Client>> findClientsByName(String name) {
        return clientDAO.findByName(name);
    }

    public boolean delete(Client client) {
        if (client == null || clientDAO.findOneByCPF(client.getCpf()).isEmpty()) {
            throw new EntityNotFoundException("Client not found.");
        }

        List<Scheduling> allSchedulings = schedulingDaoJdbc.findAll();
        boolean hasSchedulings = allSchedulings.stream()
                .anyMatch(scheduling -> scheduling.getClient().getId().equals(client.getId()));

        if (!hasSchedulings) {
            return clientDAO.delete(client);
        }

        client.setStatus(Status.INACTIVE);
        return clientDAO.update(client);
    }
}
