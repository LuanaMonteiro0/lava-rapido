package br.edu.ifps.luana.application.main;

import com.example.lavarapido.application.repository.daoimplements.ClientDaoJdbc;
import com.example.lavarapido.domain.entities.client.CPF;
import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.client.Telephone;
import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.usecases.Client.CreateClientUseCase;

public class Main {
    public static void main(String[] args) {

        Client c = new Client("Luana Monteiro",new Telephone("16 99293-5849"), new CPF("428.888.999-16"), Status.ACTIVE);

        ClientDaoJdbc cjdbc = new ClientDaoJdbc();

        CreateClientUseCase uc = new CreateClientUseCase(cjdbc);

        uc.insert(c);

    }
}