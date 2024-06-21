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

    protected VehicleCategory createVehicleCategoryFromDbQuery(ResultSet resultSet) throws SQLException {

        return new VehicleCategory(
                resultSet.getString("id"),
                resultSet.getString("name")
        );
    }

    @Override
    public String create(VehicleCategory vehicleCategory) {

        Optional<VehicleCategory> existCategory = findOneByName(vehicleCategory.getName());

        if (existCategory.isPresent()) {
            return "Catedory already exists";
        }

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

            return null;
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
                myVehicleCategories.add(createVehicleCategoryFromDbQuery(res));
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
                VehicleCategory myVehicleCategory = createVehicleCategoryFromDbQuery(res);
                return Optional.of(myVehicleCategory);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<VehicleCategory> findOneByName(String name) {
        String targetVehicleCategory = """
        SELECT * FROM VehicleCategories WHERE name = ?
        """;

        try (PreparedStatement targetVehicleCategoryStatement = ConnectionFactory.createPreparedStatement(targetVehicleCategory)) {
            targetVehicleCategoryStatement.setString(1, name);

            try (ResultSet res = targetVehicleCategoryStatement.executeQuery()) {
                if (res.next()) {
                    return Optional.of(createVehicleCategoryFromDbQuery(res));
                } else {
                    return Optional.empty();
                }
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

}
