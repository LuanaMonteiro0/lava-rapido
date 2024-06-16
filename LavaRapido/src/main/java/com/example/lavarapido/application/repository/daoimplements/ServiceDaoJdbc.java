package com.example.lavarapido.application.repository.daoimplements;

import com.example.lavarapido.application.repository.database.ConnectionFactory;
import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.service.Service;
import com.example.lavarapido.usecases.Service.ServiceDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                SELECT * FROM Services WHERE name = ?
                """;
            PreparedStatement targetServiceStatement = ConnectionFactory.createPreparedStatement(targetService);
            targetServiceStatement.setString(1, name);

            ResultSet res = targetServiceStatement.executeQuery();
            if (res.next()) {
                Service s = createServiceFromDbQuery(res);
                return Optional.of(s);
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

            PreparedStatement targetClientStatement = ConnectionFactory.createPreparedStatement(targetService);
            targetClientStatement.setString(1, service.getId());
            targetClientStatement.setString(2, service.getName());
            targetClientStatement.setString(3, String.valueOf(service.getStatus()));

            targetClientStatement.executeUpdate();

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
                SELECT * FROM Services WHERE id = ?
                """;
            PreparedStatement targetServiceStatement = ConnectionFactory.createPreparedStatement(targetService);
            targetServiceStatement.setString(1, serviceId);

            ResultSet res = targetServiceStatement.executeQuery();

            Service s = createServiceFromDbQuery(res);
            return Optional.of(s);

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
