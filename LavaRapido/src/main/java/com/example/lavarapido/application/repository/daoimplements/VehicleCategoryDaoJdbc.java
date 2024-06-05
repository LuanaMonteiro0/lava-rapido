package com.example.lavarapido.application.repository.daoimplements;

import com.example.lavarapido.application.repository.database.ConnectionFactory;
import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;
import com.example.lavarapido.usecases.VehicleCategory.VehicleCategoryDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VehicleCategoryDaoJdbc implements VehicleCategoryDAO {

    private VehicleCategory createVehicleCategory(ResultSet resultSet) throws SQLException {

        return new VehicleCategory(
                resultSet.getString("id"),
                resultSet.getString("name")
        );
    }

    @Override
    public String create(VehicleCategory vehicleCategory) {
            try {
                String targetVehicleCategory = """
                INSERT INTO VehicleCategories (id, name) VALUES(?, ?)
                """;
                PreparedStatement targetVehicleCategoryStatement = ConnectionFactory.createPreparedStatement(targetVehicleCategory);
                targetVehicleCategoryStatement.setString(1, vehicleCategory.getId());
                targetVehicleCategoryStatement.setString(2, vehicleCategory.getName());

                targetVehicleCategoryStatement.executeUpdate();

                return "VehicleCategory inserted";

            } catch(SQLException e) {
                e.printStackTrace();
            }

            return "VehicleCategory not inserted";
    }

    @Override
    public boolean update(VehicleCategory vehicleCategory) {

        try {
            String targetVehicleCategory = """
                UPDATE VehicleCategories SET name = ? WHERE id = ?
                """;

            PreparedStatement targetVehicleCategoryStatement = ConnectionFactory.createPreparedStatement(targetVehicleCategory);
            targetVehicleCategoryStatement.setString(1, vehicleCategory.getName());
            targetVehicleCategoryStatement.setString(2, vehicleCategory.getId());

            targetVehicleCategoryStatement.executeUpdate();

            return true;

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(VehicleCategory vehicleCategory) {
        return deleteByKey(vehicleCategory.getId());
    }

    @Override
    public boolean deleteByKey(String key) {
        try {
            String targetVehicleCategory = """
                DELETE FROM VehicleCategories WHERE id = ?
                """;
            PreparedStatement targetVehicleCategoryStatement = ConnectionFactory.createPreparedStatement(targetVehicleCategory);
            targetVehicleCategoryStatement.setString(1, key);

            targetVehicleCategoryStatement.executeUpdate();

            return true;

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<VehicleCategory> findAll() {

        List<VehicleCategory> myVehicleCategories = new ArrayList<>();

        try {
            String targetVehicleCategory = """
                SELECT * FROM VehicleCategories
                """;
            PreparedStatement targetVehicleCategoryStatement = ConnectionFactory.createPreparedStatement(targetVehicleCategory);

            ResultSet res = targetVehicleCategoryStatement.executeQuery();

            while(res.next()){
                myVehicleCategories.add(createVehicleCategory(res));
            }
            return myVehicleCategories;

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return myVehicleCategories;
    }

    @Override
    public Optional<VehicleCategory> findOne(String vehicleCategoryId) {
        try {
            String targetVehicleCategory = """
                SELECT * FROM VehicleCategories WHERE id = ?
                """;
            PreparedStatement targetVehicleCategoryStatement = ConnectionFactory.createPreparedStatement(targetVehicleCategory);
            targetVehicleCategoryStatement.setString(1, vehicleCategoryId);

            ResultSet res = targetVehicleCategoryStatement.executeQuery();

            if (res.next()) {
                VehicleCategory myVehicleCategory = createVehicleCategory(res);
                return Optional.of(myVehicleCategory);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<VehicleCategory> findOneByName(String name) {
        try {
            String targetVehicleCategory = """
                SELECT * FROM VehicleCategories WHERE name = ?
                """;
            PreparedStatement targetVehicleCategoryStatement = ConnectionFactory.createPreparedStatement(targetVehicleCategory);
            targetVehicleCategoryStatement.setString(1, name);

            ResultSet res = targetVehicleCategoryStatement.executeQuery();

            return Optional.of(createVehicleCategory(res));

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
