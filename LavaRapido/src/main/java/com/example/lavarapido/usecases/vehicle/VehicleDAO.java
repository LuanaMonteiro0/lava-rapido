package com.example.lavarapido.usecases.vehicle;

import com.example.lavarapido.domain.entities.vehicle.LicensePlate;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import com.example.lavarapido.usecases.utils.DAO;

import java.util.Optional;

public interface VehicleDAO extends DAO<Vehicle, String> {
    Optional<Vehicle> findByLicensePlate(LicensePlate licensePlate);
}
