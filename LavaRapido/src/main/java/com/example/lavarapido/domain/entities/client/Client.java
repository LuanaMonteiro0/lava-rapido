package com.example.lavarapido.domain.entities.client;

import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.scheduling.Scheduling;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;

import java.util.List;

public class Client {

    private long id;

    private String name;

    private Telephone phone;

    private CPF cpf;

    private List<Vehicle> vehicles;

    private Status status; // Tem valor padrão?

    private List<Scheduling> schedulings;

    public Client() {};


    public void changeClientStatus(Status status) {

    }

    public void addVehicle(VehicleDTO vDTO) {

    }

    public void deleteVehicle(VehicleDTO vDTO) {

    }

    public void changeVehicleStatus(long idVehicle, Status status) {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
