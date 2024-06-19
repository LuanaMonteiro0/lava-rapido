package com.example.lavarapido.application.repository.daoimplements;

import com.example.lavarapido.application.repository.database.ConnectionFactory;
import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.vehicle.LicensePlate;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import com.example.lavarapido.usecases.Vehicle.VehicleDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VehicleDaoJdbc implements VehicleDAO {

    protected Vehicle createVehicleFromDbQuery(ResultSet resultSet) throws SQLException {
        Vehicle vehicle = new Vehicle(
                new LicensePlate(resultSet.getString("licensePlate")),
                resultSet.getString("color"),
                resultSet.getString("model"),
                resultSet.getString("id")
        );
        vehicle.setStatus(Status.toEnum(resultSet.getString("status")));

        return vehicle;
    }

    @Override
    public String create(Vehicle vehicle) {
        try {
            String targetVehicle = """
                INSERT INTO Vehicles (id, status, model, color, licensePlate) VALUES(?, ?, ?, ?, ?);
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
    public boolean deleteByKey(String vehicleId) {
        try {
            String targetVehicle = """
                DELETE FROM Vehicles WHERE id = ?
                """;
            PreparedStatement targetVehicleStatement = ConnectionFactory.createPreparedStatement(targetVehicle);
            targetVehicleStatement.setString(1, vehicleId);

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
                SELECT * FROM Vehicles WHERE status LIKE 'A%'
                """;
            PreparedStatement targetVehicleStatement = ConnectionFactory.createPreparedStatement(targetVehicle);

            ResultSet res = targetVehicleStatement.executeQuery();

            while(res.next()){
                myVehicles.add(createVehicleFromDbQuery(res));
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
                SELECT * FROM Vehicles WHERE id = ? AND status LIKE 'A%'
                """;
            PreparedStatement targetVehicleStatement = ConnectionFactory.createPreparedStatement(targetVehicle);
            targetVehicleStatement.setString(1, vehicleId);

            ResultSet res = targetVehicleStatement.executeQuery();

            if (res.next()) {
                Vehicle myVehicle = createVehicleFromDbQuery(res);
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
                SELECT * FROM Vehicles WHERE licensePlate = ? AND status LIKE 'A%'
                """;
            PreparedStatement targetVehicleStatement = ConnectionFactory.createPreparedStatement(targetVehicle);
            targetVehicleStatement.setString(1, licensePlate.getLicensePlate());

            ResultSet res = targetVehicleStatement.executeQuery();

            if (res.next()) {
                Vehicle myVehicle = createVehicleFromDbQuery(res);
                return Optional.of(myVehicle);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();

    }

    /*public List<Vehicle> findAllUnassociated() {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = """
        SELECT v.* FROM Vehicles v
        LEFT JOIN ClientVehicles cv ON v.id = cv.vehicleId
        WHERE cv.clientId IS NULL
        """;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Vehicle vehicle = createVehicleFromDbQuery(rs);
                vehicles.add(vehicle);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicles;
    }*/


}
