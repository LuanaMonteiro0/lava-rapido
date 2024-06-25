package com.example.lavarapido.application.main;

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
import com.example.lavarapido.usecases.Client.*;
import com.example.lavarapido.usecases.Scheduling.CancelSchedulingUseCase;
import com.example.lavarapido.usecases.Scheduling.InsertSchedulingUseCase;
import com.example.lavarapido.usecases.Scheduling.ListSchedulesForTheDayUseCase;
import com.example.lavarapido.usecases.Service.*;
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
        configureInjection();
        WindowLoader.main(args);
    }

    public static CreateClientUseCase createClientUseCase;
    public static DeleteClientUseCase deleteClientUseCase;
    public static UpdateClientUseCase updateClientUseCase;
    public static ReactiveClientUseCase reactiveClientUseCase;

    public static DeleteVehicleClientUseCase deleteVehicleClientUseCase;
    public static AddVehicleClientUseCase addVehicleClientUseCase;
    public static ReactiveVehicleClientUseCase reactiveVehicleClientUseCase;
    public static UpdateVehicleClientUseCase updateVehicleClientUseCase;

    public static InsertVehicleCategoryUseCase insertVehicleCategoryUseCase;
    public static UpdateVehicleCategoryUseCase updateVehicleCategoryUseCase;
    public static DeleteVehicleCategoryUseCase deleteVehicleCategoryUseCase;

    public static CreateServiceUseCase createServiceUseCase;
    public static InactivateServiceUseCase inactivateServiceUseCase;
    public static ReactiveServiceUseCase reactiveServiceUseCase;
    public static UpdateServiceUseCase updateServiceUseCase;

    public static InsertSchedulingUseCase insertSchedulingUseCase;
    public static CancelSchedulingUseCase cancelSchedulingUseCase;
    public static ListSchedulesForTheDayUseCase listSchedulesForTheDayUseCase;

    private static void configureInjection() {
        ClientDaoJdbc clientDaoJdbc = new ClientDaoJdbc();
        VehicleDaoJdbc vehicleDaoJdbc = new VehicleDaoJdbc();
        VehicleCategoryDaoJdbc vehicleCategoryDaoJdbc = new VehicleCategoryDaoJdbc();
        ServicesPricesDaoJdbc servicesPricesDaoJdbc = new ServicesPricesDaoJdbc();
        ServiceDaoJdbc serviceDaoJdbc = new ServiceDaoJdbc();
        SchedulingDaoJdbc schedulingDaoJdbc = new SchedulingDaoJdbc();

        createClientUseCase = new CreateClientUseCase(clientDaoJdbc);
        updateClientUseCase = new UpdateClientUseCase(clientDaoJdbc);
        deleteClientUseCase = new DeleteClientUseCase(clientDaoJdbc, schedulingDaoJdbc);
        reactiveClientUseCase = new ReactiveClientUseCase(clientDaoJdbc);

        addVehicleClientUseCase = new AddVehicleClientUseCase(vehicleDaoJdbc);
        reactiveVehicleClientUseCase = new ReactiveVehicleClientUseCase(vehicleDaoJdbc);
        updateVehicleClientUseCase = new UpdateVehicleClientUseCase(vehicleDaoJdbc);
        deleteVehicleClientUseCase = new DeleteVehicleClientUseCase(vehicleDaoJdbc, schedulingDaoJdbc);

        insertVehicleCategoryUseCase = new InsertVehicleCategoryUseCase(vehicleCategoryDaoJdbc);
        updateVehicleCategoryUseCase = new UpdateVehicleCategoryUseCase(vehicleCategoryDaoJdbc);
        deleteVehicleCategoryUseCase = new DeleteVehicleCategoryUseCase(
                vehicleCategoryDaoJdbc, servicesPricesDaoJdbc, vehicleDaoJdbc);

        createServiceUseCase = new CreateServiceUseCase(serviceDaoJdbc);
        updateServiceUseCase = new UpdateServiceUseCase(serviceDaoJdbc);
        inactivateServiceUseCase = new InactivateServiceUseCase(serviceDaoJdbc);
        reactiveServiceUseCase = new ReactiveServiceUseCase(serviceDaoJdbc);

        insertSchedulingUseCase = new InsertSchedulingUseCase(
                schedulingDaoJdbc, clientDaoJdbc, vehicleDaoJdbc, serviceDaoJdbc);
        cancelSchedulingUseCase = new CancelSchedulingUseCase(schedulingDaoJdbc);
        listSchedulesForTheDayUseCase = new ListSchedulesForTheDayUseCase(schedulingDaoJdbc);
    }
}