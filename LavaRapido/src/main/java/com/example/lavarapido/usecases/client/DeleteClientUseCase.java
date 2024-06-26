package com.example.lavarapido.usecases.client;

import com.example.lavarapido.application.repository.daoimplements.SchedulingDaoJdbc;
import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.scheduling.Scheduling;
import com.example.lavarapido.usecases.utils.ShowAlert;
import com.example.lavarapido.usecases.utils.EntityNotFoundException;

import java.util.List;

public class DeleteClientUseCase {

    private final ClientDAO clientDAO;
    private final SchedulingDaoJdbc schedulingDaoJdbc;

    public DeleteClientUseCase(ClientDAO clientDAO, SchedulingDaoJdbc schedulingDaoJdbc) {
        this.clientDAO = clientDAO;
        this.schedulingDaoJdbc = schedulingDaoJdbc;
    }

    public boolean delete(Client client) {
        if (client == null || clientDAO.findOneByCPF(client.getCpf()).isEmpty()) {
            ShowAlert.showErrorAlert("Cliente não encontrado.");
            throw new EntityNotFoundException("Client not found.");
        }

        List<Scheduling> allSchedulings = schedulingDaoJdbc.findAll();
        boolean hasSchedulings = allSchedulings.stream()
                .anyMatch(scheduling -> scheduling.getClient().getId().equals(client.getId()));

        if (!hasSchedulings) {
            return clientDAO.delete(client);
        }

        ShowAlert.showInfoAlert("Não é possível deletar cliente com agendamentos. O cliente será inativado.");
        client.setStatus(Status.INACTIVE);
        return clientDAO.update(client);
    }
}
