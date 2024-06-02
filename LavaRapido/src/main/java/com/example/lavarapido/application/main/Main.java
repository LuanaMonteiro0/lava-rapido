package br.edu.ifps.luana.application.main;

import com.example.lavarapido.application.repository.daoimplements.ClientDaoJdbc;
import com.example.lavarapido.domain.entities.client.CPF;
import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.client.Telephone;
import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.usecases.Client.CreateClientUseCase;
import com.example.lavarapido.usecases.Client.DeleteClientUseCase;
import com.example.lavarapido.usecases.Client.UpdateClientUseCase;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //testan
        testeInsereCliente();
        //testeAtualizaCliente();
        //testeRemoveCliente();


    }

    public static void testeInsereCliente(){
        Client c = new Client("Luana Monteiro",new Telephone("16 99293-5849"), new CPF("428.888.999-16"), Status.ACTIVE);

        ClientDaoJdbc cjdbc = new ClientDaoJdbc();

        CreateClientUseCase ucc = new CreateClientUseCase(cjdbc);

        ucc.insert(c);
    }

    public static void testeAtualizaCliente(){
        ClientDaoJdbc cjdbc = new ClientDaoJdbc();

        Client cn = cjdbc.findOneByCPF(new CPF("428.888.999-16")).get();

        cn.setName("pinduca");

        UpdateClientUseCase ucu = new UpdateClientUseCase(cjdbc);

        ucu.update(cn);
    }

    public static void testeRemoveCliente(){
        ClientDaoJdbc cjdbc = new ClientDaoJdbc();

        Client cd = cjdbc.findOneByCPF(new CPF("428.888.999-16")).get();

        cd.setSchedulings( new ArrayList<>());

        DeleteClientUseCase ucd = new DeleteClientUseCase(cjdbc);

        ucd.delete(cd);
    }



}