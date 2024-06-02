package com.example.lavarapido.application.repository.daoimplements;

import com.example.lavarapido.domain.entities.client.Client;
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

    private Service instanciarServico(ResultSet rs) throws SQLException {
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

            Service s = instanciarServico(res);
            return Optional.of(s);

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public String create(Service type) {
        return null;
    }

    @Override
    public Optional<Service> findOne(String key) {
        try {
            String targetService = """
                SELECT * FROM Services WHERE id = ?
                """;
            PreparedStatement targetServiceStatement = ConnectionFactory.createPreparedStatement(targetService);
            targetServiceStatement.setString(1, key);

            ResultSet res = targetServiceStatement.executeQuery();

            Service s = instanciarServico(res);
            return Optional.of(s);

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<Service> findAll() {
        List<Service> services = new ArrayList<>();

        try {
            String targetServices = """
                SELECT * FROM Services
                """;
            PreparedStatement targetServiceStatement = ConnectionFactory.createPreparedStatement(targetServices);

            ResultSet res = targetServiceStatement.executeQuery();

            while(res.next()){
                services.add(instanciarServico(res));
            }
            return services;

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return services;
    }

    @Override
    public boolean update(Service type) {
        return false;
    }

    @Override
    public boolean deleteByKey(String key) {
        try {
            String targetService = """
                DELETE FROM Services WHERE id = ?
                """;
            PreparedStatement targetServiceStatement = ConnectionFactory.createPreparedStatement(targetService);
            targetServiceStatement.setString(1, key);

            targetServiceStatement.executeQuery();
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
