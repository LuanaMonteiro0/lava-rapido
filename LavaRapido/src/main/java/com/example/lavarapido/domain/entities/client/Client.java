package com.example.lavarapido.domain.entities.client;

import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.scheduling.Scheduling;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;

import java.util.List;

public class Client {

    private final Integer id;

    private String name;

    private Telephone phone;

    private final CPF cpf;

    private List<Vehicle> vehicles;

    private Status status;

    private List<Scheduling> schedulings;

    public Client(Integer id, String name, Telephone phone, CPF cpf) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.cpf = cpf;
        this.status = Status.ACTIVE;
    }

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

    public Integer getId() {
        return id;
    }

    public String getCpfString() {
        return cpf.getCpf();
    }

    public CPF getCpf() {
        return cpf;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public List<Scheduling> getSchedulings() {
        return schedulings;
    }
}
