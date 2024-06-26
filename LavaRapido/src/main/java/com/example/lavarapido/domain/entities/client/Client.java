package com.example.lavarapido.domain.entities.client;

import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.scheduling.Scheduling;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Client {

    UUID uuid = UUID.randomUUID();
    private String id = uuid.toString();

    private String name;

    private Telephone phone;

    private CPF cpf;

    private List<Vehicle> vehicles = new ArrayList<>();

    private Status status;

    private List<Scheduling> schedulings = new ArrayList<>();

    public Client(String name, Telephone phone, CPF cpf, Status status) {
        this.name = name;
        this.phone = phone;
        this.cpf = cpf;
        this.status = status;
    }

    public Client(String id, String name, Telephone phone, CPF cpf) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.cpf = cpf;
        this.status = Status.ACTIVE;
        this.vehicles = new ArrayList<>();
    }

    public Client(String id, String name, CPF cpf, Telephone phone, Status status) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.cpf = cpf;
        this.status = status;
    }
    public Client(String name, Telephone phone, CPF cpf) {
        this.name = name;
        this.phone = phone;
        this.cpf = cpf;
        this.status = Status.ACTIVE;
    }


    public void addScheduling(Scheduling scheduling) {
        if (scheduling != null) {
            schedulings.add(scheduling);
        }
    }

    public Client(){
        this.status = Status.ACTIVE;
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone.getTelephone();
    }

    public void setPhone(Telephone phone) {
        this.phone = phone;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getCpfString() {
        return cpf.getCpf();
    }

    public CPF getCpf() {
        return cpf;
    }

    public void setCpf(CPF cpf) {
        this.cpf = cpf;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public List<Scheduling> getSchedulings() {
        return schedulings;
    }

    public void setSchedulings(List<Scheduling> schedulings) {
        this.schedulings = schedulings;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
    }

}