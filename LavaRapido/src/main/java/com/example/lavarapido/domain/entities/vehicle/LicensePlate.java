package com.example.lavarapido.domain.entities.vehicle;

public class LicensePlate {

    private final String licensePlate;

    public LicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    @Override
    public String toString() {
        return  licensePlate;
    }
}
