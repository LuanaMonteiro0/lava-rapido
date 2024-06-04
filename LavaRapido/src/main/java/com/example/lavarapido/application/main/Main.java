package br.edu.ifps.luana.application.main;

import com.example.lavarapido.application.repository.daoimplements.ClientDaoJdbc;
import com.example.lavarapido.application.repository.daoimplements.ServiceDaoJdbc;
import com.example.lavarapido.application.repository.daoimplements.VehicleDaoJdbc;
import com.example.lavarapido.domain.entities.client.CPF;
import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.client.Telephone;
import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.service.Service;
import com.example.lavarapido.domain.entities.vehicle.LicensePlate;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import com.example.lavarapido.usecases.Client.CreateClientUseCase;
import com.example.lavarapido.usecases.Client.DeleteClientUseCase;
import com.example.lavarapido.usecases.Client.ReactiveClientUseCase;
import com.example.lavarapido.usecases.Client.UpdateClientUseCase;
import com.example.lavarapido.usecases.Vehicle.AddVehicleClientUseCase;
import com.example.lavarapido.usecases.Vehicle.DeleteVehicleClientUseCase;
import com.example.lavarapido.usecases.Vehicle.ReactiveVehicleClientUseCase;
import com.example.lavarapido.usecases.Vehicle.UpdateVehicleClientUseCase;

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

        //testando use case AddVehicleClientUseCase
        //testeInsereVeiculo();

        //testando use case UpdateVehicleClientUseCase
        //testeAtualizaVeiculo();

        //testando use case DeleteVehicleClientUseCase
        //testeRemoveVeiculo();

        //testando use case DeleteVehicleClientUseCase
        //testeReativaVeiculo();
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

    public static void testeReativarCliente() {
        ClientDaoJdbc cjdbc = new ClientDaoJdbc();

        Client c = new Client("Luan Marqueti",new Telephone("16 99999-5555"), new CPF("444.888.999-16"), Status.INACTIVE);

        CreateClientUseCase ucc = new CreateClientUseCase(cjdbc);

        ucc.insert(c);  //colocou o cliente no banco

        Client c1 = cjdbc.findOneByCPF(new CPF("444.888.999-16")).get();

        ReactiveClientUseCase ruc = new ReactiveClientUseCase(cjdbc);

        ruc.reactive(c1);
    }

    public static void testeInsereServico() {
        ServiceDaoJdbc sjdbc = new ServiceDaoJdbc();

        Service s = new Service(Status.ACTIVE, "Lavar carro");




    }


    public static void testeInsereVeiculo() {

        VehicleDaoJdbc vDaoJdbc = new VehicleDaoJdbc();

        Vehicle v = new Vehicle(new LicensePlate("CJA0562"), "Vw Gol", "Vermelho", Status.INACTIVE);

        AddVehicleClientUseCase avcUc = new AddVehicleClientUseCase(vDaoJdbc);

        avcUc.insert(v);

    }

    public static void testeReativaVeiculo() {

        VehicleDaoJdbc vDaoJdbc = new VehicleDaoJdbc();

        Vehicle v = vDaoJdbc.findByLicensePlate(new LicensePlate("CJA0562")).get();

        ReactiveVehicleClientUseCase rvcUc = new ReactiveVehicleClientUseCase(vDaoJdbc);

        rvcUc.reactive(v);

    }

    public static void testeAtualizaVeiculo() {

        VehicleDaoJdbc vDaoJdbc = new VehicleDaoJdbc();

        Vehicle v = vDaoJdbc.findByLicensePlate(new LicensePlate("CJA0562")).get();
        v.setModel("Volkswagen Gol");
        v.setColor("Laranja");

        UpdateVehicleClientUseCase uvcUc = new UpdateVehicleClientUseCase(vDaoJdbc);

        uvcUc.update(v);

    }

    public static void testeRemoveVeiculo() {

        VehicleDaoJdbc vDaoJdbc = new VehicleDaoJdbc();

        Vehicle v = vDaoJdbc.findByLicensePlate(new LicensePlate("CJA0562")).get();

        DeleteVehicleClientUseCase dvcUc = new DeleteVehicleClientUseCase(vDaoJdbc);

        dvcUc.delete(v);

    }









}