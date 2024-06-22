package com.example.lavarapido.application.repository.daoimplements;

import com.example.lavarapido.application.repository.database.ConnectionFactory;
import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.service.Service;
import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;
import com.example.lavarapido.usecases.Service.ServiceDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ServiceDaoJdbc implements ServiceDAO {

    protected Service createServiceFromDbQuery(ResultSet rs) throws SQLException {
        Service service = new Service(
            rs.getString("id"), rs.getString("name")
        );
        service.setStatus(Status.valueOf(rs.getString("status")));
        return service;
    }

    @Override
    public Optional<Service> findOneByName(String name) {
        try {
            String targetService = """
                SELECT id FROM Services WHERE name = ? AND status LIKE 'A%'
                """;
            PreparedStatement targetServiceStatement = ConnectionFactory.createPreparedStatement(targetService);
            targetServiceStatement.setString(1, name);

            ResultSet res = targetServiceStatement.executeQuery();
            if (res.next()) {
                Optional<Service> myServiceOptional = findOne(res.getString("id"));
                return myServiceOptional;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public String create(Service service) {

        try {
            String targetService = """
               INSERT INTO Services(id, name, status) VALUES(?, ?, ?)
               """;

            PreparedStatement targetServiceStatement = ConnectionFactory.createPreparedStatement(targetService);
            targetServiceStatement.setString(1, service.getId());
            targetServiceStatement.setString(2, service.getName());
            targetServiceStatement.setString(3, String.valueOf(service.getStatus()));

            targetServiceStatement.executeUpdate();

            Map<VehicleCategory, Double> servicesPrices = service.getPrice();

            List<VehicleCategory> servicesVehicleCategoriesList = new ArrayList<>();
            List<Double> servicesPricesList = new ArrayList<>();

            servicesPrices.forEach((key, value) -> {
                servicesVehicleCategoriesList.add(key);
                servicesPricesList.add(value);
            });

            for (int i = 0; i < servicesVehicleCategoriesList.size(); i++) {
                String targetServicesPrices = """
               INSERT INTO ServicesPrices(idService, idVehicleCategory, price) VALUES(?, ?, ?)
               """;
                PreparedStatement targetServicesPricesStatement = ConnectionFactory.createPreparedStatement(targetServicesPrices);
                targetServicesPricesStatement.setString(1, service.getId());
                targetServicesPricesStatement.setString(2, servicesVehicleCategoriesList.get(i).getId());
                targetServicesPricesStatement.setString(3, servicesPricesList.get(i).toString());

                targetServicesPricesStatement.executeUpdate();
            }

            return "Service inserted";

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return "Service not inserted";
    }

    @Override
    public Optional<Service> findOne(String serviceId) {
        try {
            String targetService = """
                SELECT * FROM Services WHERE id = ? AND status LIKE 'A%' 
                """;
            PreparedStatement targetServiceStatement = ConnectionFactory.createPreparedStatement(targetService);
            targetServiceStatement.setString(1, serviceId);

            ResultSet res = targetServiceStatement.executeQuery();

            if (res.next()) {

                Service service = createServiceFromDbQuery(res);

                String targetServicesPrices = """
                SELECT * FROM ServicesPrices WHERE idService = ?
                """;
                PreparedStatement targetServicesPricesStatement = ConnectionFactory.createPreparedStatement(targetServicesPrices);
                targetServicesPricesStatement.setString(1, serviceId);
                ResultSet resultSetServicesPrices = targetServicesPricesStatement.executeQuery();

                while (resultSetServicesPrices.next()) {

                    String targetVehicleCategories = """
                    SELECT * FROM VehicleCategories WHERE idVehicleCategory = ?
                    """;
                    PreparedStatement targetVehicleCategoriesStatement = ConnectionFactory.createPreparedStatement(targetVehicleCategories);
                    targetVehicleCategoriesStatement.setString(1, serviceId);
                    ResultSet resultSetVehicleCategory = targetServicesPricesStatement.executeQuery();

                    VehicleCategoryDaoJdbc vcDaoJdbc = new VehicleCategoryDaoJdbc();
                    VehicleCategory vCat = vcDaoJdbc.findOne(resultSetVehicleCategory.getString("id")).get();

                    service.setPrice(vCat, resultSetServicesPrices.getDouble("price"));

                }

                return Optional.of(service);

            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<Service> findAll() {

        List<Service> myServices = new ArrayList<>();

        try {
            String targetServices = """
                SELECT * FROM Services
                """;
            PreparedStatement targetServiceStatement = ConnectionFactory.createPreparedStatement(targetServices);

            ResultSet res = targetServiceStatement.executeQuery();

            while(res.next()){
                myServices.add(createServiceFromDbQuery(res));
            }
            return myServices;

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return myServices;
    }

    @Override
    public boolean update(Service service) {
        try {

            //TODO: Submenu de atualização ServicesPrices (criar ServicesPricesDaoJdbc)
            String targetService = """
                UPDATE Services SET name = ?, status = ? WHERE id = ?
                """;

            PreparedStatement targetClientStatement = ConnectionFactory.createPreparedStatement(targetService);
            targetClientStatement.setString(1, service.getName());
            targetClientStatement.setString(2, String.valueOf(service.getStatus()));
            targetClientStatement.setString(3, service.getId());

            targetClientStatement.executeUpdate();

            return true;

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteByKey(String serviceId) {
        try {
            String targetService = """
                DELETE FROM Services WHERE id = ?
                """;
            PreparedStatement targetServiceStatement = ConnectionFactory.createPreparedStatement(targetService);
            targetServiceStatement.setString(1, serviceId);

            targetServiceStatement.executeUpdate();
            return true;

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Service service) {
        return deleteByKey(service.getId());
    }
}
