package com.example.lavarapido.usecases.VehicleCategory;

import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;
import com.example.lavarapido.usecases.utils.DAO;

import java.util.Optional;

public interface VehicleCategoryDAO extends DAO<VehicleCategory, Long> {
    Optional<VehicleCategory> findOneByName(String name);
}
