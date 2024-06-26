package com.example.lavarapido.usecases.vehicleCategory;

import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;
import com.example.lavarapido.usecases.utils.DAO;

import java.util.Optional;

public interface VehicleCategoryDAO extends DAO<VehicleCategory, String> {

    Optional<VehicleCategory> findOneByName(String name);

}
