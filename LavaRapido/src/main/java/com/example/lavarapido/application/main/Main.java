package br.edu.ifps.luana.application.main;

import com.example.lavarapido.application.repository.daoimplements.ClientDaoJdbc;
import com.example.lavarapido.application.repository.daoimplements.ServiceDaoJdbc;
import com.example.lavarapido.domain.entities.client.CPF;
import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.client.Telephone;
import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.service.Service;
import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;
import com.example.lavarapido.usecases.Client.CreateClientUseCase;
import com.example.lavarapido.usecases.Client.DeleteClientUseCase;
import com.example.lavarapido.usecases.Client.ReactiveClientUseCase;
import com.example.lavarapido.usecases.Client.UpdateClientUseCase;
import com.example.lavarapido.usecases.Service.CreateServiceUseCase;
import com.example.lavarapido.usecases.Service.InactivateServiceUseCase;
import com.example.lavarapido.usecases.Service.ReactiveServiceUseCase;
import com.example.lavarapido.usecases.Service.UpdateServiceUseCase;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //testando use case createClientUseCase
        //testeInsereCliente();

        //testando use case UpdateClientUseCase
        //testeAtualizaCliente();

        //testando use case DeleteClientUseCase
        //testeRemoveCliente();

        //testando use case ReactiveClientUseCase
        //testeReativarCliente();

        //testando use case CreateServiceUseCase
        //testeInsereServico();

        //testando use case UpdateServiceUseCase
        //testeAtualizaServico();

        //testando use case UpdateServiceUseCase
        //testandoInativarServico();

        //testando use case UpdateServiceUseCase
        //testandoReativarServico();
    }

    public static void testeInsereCliente() {
        Client c = new Client("Luana Monteiro",new Telephone("16 99293-5849"), new CPF("428.888.999-16"), Status.ACTIVE);

        ClientDaoJdbc cjdbc = new ClientDaoJdbc();

        CreateClientUseCase ucc = new CreateClientUseCase(cjdbc);

        ucc.insert(c);
    }

    public static void testeAtualizaCliente() {
        ClientDaoJdbc cjdbc = new ClientDaoJdbc();

        Client cn = cjdbc.findOneByCPF(new CPF("428.888.999-16")).get();

        cn.setName("pinduca");

        UpdateClientUseCase ucu = new UpdateClientUseCase(cjdbc);

        ucu.update(cn);
    }

    public static void testeRemoveCliente() {
        ClientDaoJdbc cjdbc = new ClientDaoJdbc();

        Client cd = cjdbc.findOneByCPF(new CPF("428.888.999-16")).get();

        cd.setSchedulings( new ArrayList<>());

        DeleteClientUseCase ucd = new DeleteClientUseCase(cjdbc);

        ucd.delete(cd);
    }

    public static void testeReativarCliente(){
        ClientDaoJdbc cjdbc = new ClientDaoJdbc();

        Client c = new Client("Luan Marqueti",new Telephone("16 99999-5555"), new CPF("444.888.999-16"), Status.INACTIVE);

        CreateClientUseCase ucc = new CreateClientUseCase(cjdbc);

        ucc.insert(c);  //colocou o cliente no banco

        Client c1 = cjdbc.findOneByCPF(new CPF("444.888.999-16")).get();

        ReactiveClientUseCase ruc = new ReactiveClientUseCase(cjdbc);

        ruc.reactive(c1);

    }

    public static void testeInsereServico(){
        ServiceDaoJdbc sjdbc = new ServiceDaoJdbc();

        Service s = new Service(Status.ACTIVE, "Lavar carro");

        s.setPrice(new VehicleCategory("jepe"), 200.0);

        CreateServiceUseCase ucs = new CreateServiceUseCase(sjdbc);

        ucs.insert(s);

    }

    public static void testeAtualizaServico() {
        ServiceDaoJdbc sjdbc = new ServiceDaoJdbc();

        Service s = sjdbc.findOneByName("Lavar carro").get();

        s.setName("Lavar carro com bucha assolan");

        UpdateServiceUseCase usuc = new UpdateServiceUseCase(sjdbc);

        usuc.update(s);
    }

    public static void testandoInativarServico(){
        ServiceDaoJdbc sjdbc = new ServiceDaoJdbc();

        Service s = sjdbc.findOneByName("Lavar carro com bucha assolan").get();

        InactivateServiceUseCase isuc = new InactivateServiceUseCase(sjdbc);

        isuc.inactivate(s);
    }

    public static void testandoReativarServico(){
        ServiceDaoJdbc sjdbc = new ServiceDaoJdbc();

        Service s = sjdbc.findOneByName("Lavar carro com bucha assolan").get();

        ReactiveServiceUseCase rsuc = new ReactiveServiceUseCase(sjdbc);

        rsuc.reactive(s);
    }

}