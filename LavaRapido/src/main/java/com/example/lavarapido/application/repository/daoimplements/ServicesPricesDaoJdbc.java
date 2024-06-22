package com.example.lavarapido.application.repository.daoimplements;

import com.example.lavarapido.application.repository.database.ConnectionFactory;
import com.example.lavarapido.domain.entities.service.Service;
import com.example.lavarapido.usecases.utils.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ServicesPricesDaoJdbc {

    public boolean update(Double price, String serviceId, String vehicleId) {
        try {

            //TODO: Submenu de atualização ServicesPrices (criar ServicesPricesDaoJdbc)
            String targetServicesPrices = """
                UPDATE ServicesPrices SET price = ? WHERE serviceId = ? AND vehicleId = ?
                """;

            PreparedStatement targetClientStatement = ConnectionFactory.createPreparedStatement(targetServicesPrices);
            targetClientStatement.setString(1, price.toString());
            targetClientStatement.setString(2, serviceId);
            targetClientStatement.setString(3, vehicleId);


            targetClientStatement.executeUpdate();

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


}
