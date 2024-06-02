package com.example.lavarapido.usecases.Vehicle;

import com.example.lavarapido.domain.entities.vehicle.Plate;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import com.example.lavarapido.usecases.utils.DAO;

import java.util.Optional;

public interface VehicleDAO extends DAO<Vehicle, String> {
    Optional<Vehicle> findByPlate(Plate plate);
}
