package br.edu.ifps.luana.application.main;

import com.example.lavarapido.application.repository.daoimplements.*;
import com.example.lavarapido.application.repository.database.DatabaseBuilder;
import com.example.lavarapido.application.view.WindowLoader;
import com.example.lavarapido.domain.entities.client.CPF;
import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.client.Telephone;
import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.scheduling.FormOfPayment;
import com.example.lavarapido.domain.entities.scheduling.Scheduling;
import com.example.lavarapido.domain.entities.scheduling.SchedulingStatus;
import com.example.lavarapido.domain.entities.service.Service;
import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;
import com.example.lavarapido.domain.entities.vehicle.LicensePlate;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import com.example.lavarapido.usecases.Client.CreateClientUseCase;
import com.example.lavarapido.usecases.Client.DeleteClientUseCase;
import com.example.lavarapido.usecases.Client.ReactiveClientUseCase;
import com.example.lavarapido.usecases.Client.UpdateClientUseCase;
import com.example.lavarapido.usecases.Scheduling.InsertSchedulingUseCase;
import com.example.lavarapido.usecases.Service.CreateServiceUseCase;
import com.example.lavarapido.usecases.Service.InactivateServiceUseCase;
import com.example.lavarapido.usecases.Service.ReactiveServiceUseCase;
import com.example.lavarapido.usecases.Service.UpdateServiceUseCase;
import com.example.lavarapido.usecases.Vehicle.AddVehicleClientUseCase;
import com.example.lavarapido.usecases.Vehicle.DeleteVehicleClientUseCase;
import com.example.lavarapido.usecases.Vehicle.ReactiveVehicleClientUseCase;
import com.example.lavarapido.usecases.Vehicle.UpdateVehicleClientUseCase;
import com.example.lavarapido.usecases.VehicleCategory.DeleteVehicleCategoryUseCase;
import com.example.lavarapido.usecases.VehicleCategory.InsertVehicleCategoryUseCase;
import com.example.lavarapido.usecases.VehicleCategory.UpdateVehicleCategoryUseCase;
import javafx.application.Application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

        //testando use case InactivateServiceUseCase
        //testandoInativarServico();

        //testando use case ReactiveServiceUseCase
        //testandoReativarServico();

        //testando use case AddVehicleClientUseCase
        //testeInsereVeiculo();

        //testando use case UpdateVehicleClientUseCase
        //testeAtualizaVeiculo();

        //testando use case DeleteVehicleClientUseCase
        //testeRemoveVeiculo();

        //testando use case ReactiveVehicleClientUseCase
        //testeReativaVeiculo();

        //testando use case InsertVehicleCategoryUseCase
        //testeInsereCategoriaDeVeiculo();

        //testando use case UpdateVehicleCategoryUseCase
        //testeUpdateCategoriaDeVeiculo();

        //testando use case DeleteVehicleCategoryUseCase
        //testeRemoveCategoriaDeVeiculo();

    }

    public static DeleteClientUseCase deleteClientUseCase;
    public static DeleteVehicleClientUseCase deleteVehicleClientUseCase;
    public static CreateClientUseCase createClientUseCase;
    public static UpdateClientUseCase updateClientUseCase;
    public static AddVehicleClientUseCase addVehicleClientUseCase;
    public static UpdateVehicleClientUseCase updateVehicleClientUseCase;
    public static InsertSchedulingUseCase insertSchedulingUseCase;


    public static void testeInsereCliente() {
        Client c = new Client("Serjão",new Telephone("16 98535-5849"), new CPF("488.089.321-16"), Status.ACTIVE);

        Vehicle v = new Vehicle(new LicensePlate("SD1123"), "Sedan", "Black");
        v.setVehicleCategory(new VehicleCategory("hatch"));
        c.addVehicle(v);

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

        Service lavagem = new Service("Lavagem", Status.ACTIVE);

        lavagem.setPrice(new VehicleCategory("Hatch"), 120.0);
        lavagem.setPrice(new VehicleCategory("Sedan"), 100.0);

        CreateServiceUseCase ucs = new CreateServiceUseCase(sjdbc);

        ucs.insert(lavagem);

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

    public static void testeInsereVeiculo() {

        VehicleDaoJdbc vDaoJdbc = new VehicleDaoJdbc();

        Vehicle v = new Vehicle(new LicensePlate("CJA0562"), "Vw Gol", "Vermelho");

        v.setVehicleCategory(new VehicleCategory("hatch"));

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

    public static void testeInsereCategoriaDeVeiculo() {

        VehicleCategoryDaoJdbc vcDaoJdbc = new VehicleCategoryDaoJdbc();

        VehicleCategory vc = new VehicleCategory("Sedan");

        InsertVehicleCategoryUseCase ivcUc = new InsertVehicleCategoryUseCase(vcDaoJdbc);

        ivcUc.insert(vc);

    }

    public static void testeUpdateCategoriaDeVeiculo() {

        VehicleCategoryDaoJdbc vcDaoJdbc = new VehicleCategoryDaoJdbc();

        VehicleCategory vc = vcDaoJdbc.findOneByName("Sedan").get();

        vc.setName("SUV");

        UpdateVehicleCategoryUseCase uvcUc = new UpdateVehicleCategoryUseCase(vcDaoJdbc);

        uvcUc.update(vc);

    }


    public static void testeRemoveCategoriaDeVeiculo() {

        VehicleCategoryDaoJdbc vcDaoJdbc = new VehicleCategoryDaoJdbc();

        VehicleCategory vc = vcDaoJdbc.findOneByName("SUV").get();

        DeleteVehicleCategoryUseCase dvcUc = new DeleteVehicleCategoryUseCase(vcDaoJdbc);

        dvcUc.delete(vc);

    }


}