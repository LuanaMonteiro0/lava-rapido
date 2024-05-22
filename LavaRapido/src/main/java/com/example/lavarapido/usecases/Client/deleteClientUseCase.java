package com.example.lavarapido.usecases.Client;

import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.usecases.utils.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public class deleteClientUseCase {

    private ClientDAO clientDAO;

    public deleteClientUseCase(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    //IMPLEMENTAÇÃO INCOMPLETA POR CAUSA DOS AGENDAMENTOS

    /*
    * Para realizar a remoçao / inativação do cliente, precisamos verificar seus Agendamentos (Appointments)
    * Esses 'Appointments' é apontado como uma classe nos diagramas de sequencia, mas nao é indicado essa
    * classe.
    * É necessário a criação dessa classe? Como podemos implementar essa parte?
    */

    public Optional<List<Client>> findClientsByName(String name) {
        return clientDAO.findByName(name);
    }

    public boolean delete(Client client) {
        if (client == null || clientDAO.findOne(client.getCpfString()).isEmpty())
            throw new EntityNotFoundException("Client not found.");

        /*
        * if (clientDAO.getAppointments(client).isEmpty())
        *   return clientDAO.delete(client)
        *
        * return clientDAO.inactivate(client) --> update?
        * */
        return clientDAO.delete(client);
    }

}
