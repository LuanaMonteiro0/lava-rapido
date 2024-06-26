package com.example.lavarapido.application.main;

import com.example.lavarapido.application.repository.daoimplements.*;
import com.example.lavarapido.application.view.WindowLoader;
import com.example.lavarapido.usecases.client.*;
import com.example.lavarapido.usecases.report.clientsReport.AbsentClientReportUseCase;
import com.example.lavarapido.usecases.report.DaysBillingReportUseCase;
import com.example.lavarapido.usecases.report.servicesReport.ServicesPerformedReportUseCase;
import com.example.lavarapido.usecases.scheduling.CancelSchedulingUseCase;
import com.example.lavarapido.usecases.scheduling.InsertSchedulingUseCase;
import com.example.lavarapido.usecases.scheduling.ListSchedulesForTheDayUseCase;
import com.example.lavarapido.usecases.service.*;
import com.example.lavarapido.usecases.vehicle.AddVehicleClientUseCase;
import com.example.lavarapido.usecases.vehicle.DeleteVehicleClientUseCase;
import com.example.lavarapido.usecases.vehicle.ReactiveVehicleClientUseCase;
import com.example.lavarapido.usecases.vehicle.UpdateVehicleClientUseCase;
import com.example.lavarapido.usecases.vehicleCategory.DeleteVehicleCategoryUseCase;
import com.example.lavarapido.usecases.vehicleCategory.InsertVehicleCategoryUseCase;
import com.example.lavarapido.usecases.vehicleCategory.UpdateVehicleCategoryUseCase;

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

    public static DaysBillingReportUseCase daysBillingReportUseCase;
    public static AbsentClientReportUseCase absentClientReportUseCase;
    public static ServicesPerformedReportUseCase servicesPerformedReportUseCase;

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

        daysBillingReportUseCase = new DaysBillingReportUseCase(schedulingDaoJdbc);
        absentClientReportUseCase = new AbsentClientReportUseCase(schedulingDaoJdbc);
        servicesPerformedReportUseCase = new ServicesPerformedReportUseCase(schedulingDaoJdbc, serviceDaoJdbc);

    }
}