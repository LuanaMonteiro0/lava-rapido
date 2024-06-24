package com.example.lavarapido.application.repository.daoimplements;

import com.example.lavarapido.application.repository.database.ConnectionFactory;
import com.example.lavarapido.domain.entities.service.Service;
import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;
import com.example.lavarapido.usecases.utils.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ServicesPricesDaoJdbc {

    public boolean update(Double price, String serviceId, String vehicleId) {
        try {

            String targetServicesPrices = """
                UPDATE ServicesPrices SET price = ? WHERE serviceId = ? AND vehicleId = ?
                """;

            PreparedStatement targetServicesPricesStatement = ConnectionFactory.createPreparedStatement(targetServicesPrices);
            targetServicesPricesStatement.setString(1, price.toString());
            targetServicesPricesStatement.setString(2, serviceId);
            targetServicesPricesStatement.setString(3, vehicleId);

            targetServicesPricesStatement.executeUpdate();

            return true;

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteByKey(String serviceId) {
        try {
            String targetServicesPrices = """
                DELETE FROM ServicesPrices WHERE serviceId = ?
                """;
            PreparedStatement targetServicesPricesStatement = ConnectionFactory.createPreparedStatement(targetServicesPrices);
            targetServicesPricesStatement.setString(1, serviceId);

            targetServicesPricesStatement.executeUpdate();

            return true;

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Map<VehicleCategory, Double> findPricesByServiceId(String serviceId) {
        Map<VehicleCategory, Double> prices = new HashMap<>();
        try {
            String query = """
                SELECT idVehicleCategory, price FROM ServicesPrices WHERE idService = ?
                """;
            PreparedStatement statement = ConnectionFactory.createPreparedStatement(query);
            statement.setString(1, serviceId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                VehicleCategory category = new VehicleCategory(rs.getString("idVehicleCategory"));
                double price = rs.getDouble("price");
                prices.put(category, price);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return prices;
    }
}
