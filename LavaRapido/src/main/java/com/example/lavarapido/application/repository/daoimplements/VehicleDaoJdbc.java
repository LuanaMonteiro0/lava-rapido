package com.example.lavarapido.application.repository.daoimplements;

import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.vehicle.LicensePlate;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import com.example.lavarapido.usecases.Vehicle.VehicleDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VehicleDaoJdbc implements VehicleDAO {

    private Vehicle createVehicle(ResultSet resultSet) throws SQLException {
        Vehicle vehicle = new Vehicle(
                new LicensePlate(resultSet.getString("licensePlate")),
                resultSet.getString("color"),
                resultSet.getString("model"),
                resultSet.getString("id")
        );
        vehicle.setStatus(Status.valueOf(resultSet.getString("status")));

        return vehicle;
    }

    @Override
    public String create(Vehicle vehicle) {
        try {
            String targetVehicle = """
                INSERT INTO Vehicles (id, status, model, color, plate) VALUES(?, ?, ?, ?, ?);
                """;
            PreparedStatement targetVehicleStatement = ConnectionFactory.createPreparedStatement(targetVehicle);
            targetVehicleStatement.setString(1, vehicle.getId());
            targetVehicleStatement.setString(2, String.valueOf(vehicle.getStatus()));
            targetVehicleStatement.setString(3, vehicle.getModel());
            targetVehicleStatement.setString(4, vehicle.getColor());
            targetVehicleStatement.setString(5, String.valueOf(vehicle.getPlate()));

            targetVehicleStatement.executeUpdate();

            return "Vehicle inserted";

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return "Vehicle not inserted";
    }

    @Override
    public boolean update(Vehicle vehicle) {

        try {
            String targetVehicle = """
                UPDATE Vehicles SET status = ?, color = ?, model = ? WHERE id = ?
                """;

            PreparedStatement targetVehicleStatement = ConnectionFactory.createPreparedStatement(targetVehicle);
            targetVehicleStatement.setString(1, String.valueOf(vehicle.getStatus()));
            targetVehicleStatement.setString(2, vehicle.getColor());
            targetVehicleStatement.setString(3, vehicle.getModel());

            targetVehicleStatement.executeUpdate();

            return true;

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    @Override
    public boolean delete(Vehicle vehicle) {
        return deleteByKey(vehicle.getId());
    }

    @Override
    public boolean deleteByKey(String key) {
        try {
            String targetVehicle = """
                DELETE FROM Vehicles WHERE id = ?
                """;
            PreparedStatement targetVehicleStatement = ConnectionFactory.createPreparedStatement(targetVehicle);
            targetVehicleStatement.setString(1, key);

            targetVehicleStatement.executeUpdate();

            return true;

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Vehicle> findAll() {

        List<Vehicle> myVehicles = new ArrayList<>();

        try {
            String targetVehicle = """
                SELECT * FROM Vehicles
                """;
            PreparedStatement targetVehicleStatement = ConnectionFactory.createPreparedStatement(targetVehicle);

            ResultSet res = targetVehicleStatement.executeQuery();

            while(res.next()){
                myVehicles.add(createVehicle(res));
            }
            return myVehicles;

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return myVehicles;
    }

    @Override
    public Optional<Vehicle> findOne(String vehicleId) {
        try {
            String targetVehicle = """
                SELECT * FROM Vehicles WHERE id = ?
                """;
            PreparedStatement targetVehicleStatement = ConnectionFactory.createPreparedStatement(targetVehicle);
            targetVehicleStatement.setString(1, vehicleId);

            ResultSet res = targetVehicleStatement.executeQuery();
            if (res.next()) {
                Vehicle myVehicle = createVehicle(res);
                return Optional.of(myVehicle);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Vehicle> findByLicensePlate(LicensePlate licensePlate) {

        try {
            String targetVehicle = """
                SELECT * FROM Vehicles WHERE licensePlate = ?
                """;
            PreparedStatement targetVehicleStatement = ConnectionFactory.createPreparedStatement(targetVehicle);
            targetVehicleStatement.setString(1, licensePlate.getLicensePlate());

            ResultSet res = targetVehicleStatement.executeQuery();
            Vehicle myVehicle = createVehicle(res);

            return Optional.of(myVehicle);

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();

    }

}
