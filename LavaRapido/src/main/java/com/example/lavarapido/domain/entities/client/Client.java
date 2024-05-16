package com.example.lavarapido.domain.entities.client;

public class Client {

    private long id;

    private String name;


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
