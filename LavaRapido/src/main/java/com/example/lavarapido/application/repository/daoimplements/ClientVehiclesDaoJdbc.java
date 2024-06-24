package com.example.lavarapido.application.repository.daoimplements;

import com.example.lavarapido.application.repository.database.ConnectionFactory;
import com.example.lavarapido.domain.entities.vehicle.LicensePlate;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;
import com.example.lavarapido.domain.entities.general.Status;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientVehiclesDaoJdbc {

    public List<Vehicle> findVehiclesByClientId(String clientId) {
        String query = """
                SELECT v.id, v.color, v.model, v.category, v.licensePlate, v.status
                FROM ClientVehicles cv
                JOIN Vehicles v ON cv.vehicleId = v.id
                WHERE cv.clientId = ?
                """;

        List<Vehicle> vehicles = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(query)) {
            stmt.setString(1, clientId);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                // Retrieve the category from the VehicleCategories table
                String categoryId = resultSet.getString("category");
                VehicleCategoryDaoJdbc vehicleCategoryDaoJdbc = new VehicleCategoryDaoJdbc();
                VehicleCategory category = vehicleCategoryDaoJdbc.findOne(categoryId).orElse(new VehicleCategory(categoryId, "Unknown"));
                LicensePlate licensePlate = new LicensePlate(resultSet.getString("licensePlate"));

                Vehicle vehicle = new Vehicle(
                        resultSet.getString("id"),
                        licensePlate,
                        resultSet.getString("color"),
                        resultSet.getString("model"),
                        category
                );

                vehicle.setStatus(Status.toEnum(resultSet.getString("status")));
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return vehicles;
    }
}
