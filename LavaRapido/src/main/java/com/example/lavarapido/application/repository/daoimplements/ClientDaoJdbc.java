package com.example.lavarapido.application.repository.daoimplements;

import com.example.lavarapido.domain.entities.client.CPF;
import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.client.Telephone;
import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.usecases.Client.ClientDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDaoJdbc implements ClientDAO {

    private Client instanciarCliente(ResultSet resultSet) throws SQLException {
        Client client = new Client(
                resultSet.getString("id"),
                resultSet.getString("name"),
                new CPF(resultSet.getString("cpf")),
                new Telephone(resultSet.getString("phone"))
        );
        client.setStatus(Status.valueOf(resultSet.getString("status")));

        return client;
    }

    @Override
    public Optional<List<Client>> findByName(String name) {

        try {
            String targetClient = """
                SELECT * FROM Clients WHERE name = ?
                """;
            PreparedStatement targetClientStatement = ConnectionFactory.createPreparedStatement(targetClient);
            targetClientStatement.setString(1, name);

            ResultSet res = targetClientStatement.executeQuery();
            List<Client> myClients = new ArrayList<>();
            while(res.next()){
                Client c = instanciarCliente(res);
                myClients.add(c);
            }
            return Optional.of(myClients);

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<Client> findOneByCPF(CPF cpf) {
        try {
            String targetClient = """
                SELECT * FROM Clients WHERE cpf = ?
                """;
            PreparedStatement targetClientStatement = ConnectionFactory.createPreparedStatement(targetClient);
            targetClientStatement.setString(1, cpf.getCpf());

            ResultSet res = targetClientStatement.executeQuery();
            if (res.next()) {
                Client myClient = instanciarCliente(res);
                return Optional.of(myClient);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public String create(Client client) {
        try {
            String targetClient = """
                INSERT INTO Clients (id, name, cpf, phone, status) VALUES(?, ?, ?, ?, ?);
                """;
            PreparedStatement targetClientStatement = ConnectionFactory.createPreparedStatement(targetClient);
            targetClientStatement.setString(1, client.getId());
            targetClientStatement.setString(2, client.getName());
            targetClientStatement.setString(3, client.getCpfString());
            targetClientStatement.setString(4, client.getPhone());
            targetClientStatement.setString(5, String.valueOf(client.getStatus()));

            ResultSet res = targetClientStatement.executeQuery();

            return "Sucess";

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return "Not inserted";

    }

    @Override
    public Optional<Client> findOne(String key) {
        try {
            String targetClient = """
                SELECT * FROM Clients WHERE id = ?
                """;
            PreparedStatement targetClientStatement = ConnectionFactory.createPreparedStatement(targetClient);
            targetClientStatement.setString(1, key);

            ResultSet res = targetClientStatement.executeQuery();
            if (res.next()) {
                Client myClient = instanciarCliente(res);
                return Optional.of(myClient);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();

    }

    @Override
    public List<Client> findAll() {

        List<Client> myClients = new ArrayList<>();

        try {
            String targetClient = """
                SELECT * FROM Clients
                """;
            PreparedStatement targetClientStatement = ConnectionFactory.createPreparedStatement(targetClient);

            ResultSet res = targetClientStatement.executeQuery();

            while(res.next()){
                myClients.add(instanciarCliente(res));
            }
            return myClients;

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return myClients;
    }

    @Override
    public boolean update(Client client) {

            try {
                String targetClient = """
                UPDATE Clients SET name = ?, phone = ?, status = ? WHERE id = ?
                """;

                PreparedStatement targetClientStatement = ConnectionFactory.createPreparedStatement(targetClient);
                targetClientStatement.setString(1, client.getName());
                targetClientStatement.setString(2, client.getPhone());
                targetClientStatement.setString(3, String.valueOf(client.getStatus()));
                targetClientStatement.setString(4, client.getId());

                ResultSet res = targetClientStatement.executeQuery();

                return true;

            } catch(SQLException e) {
                e.printStackTrace();
            }

            return false;

    }

    @Override
    public boolean deleteByKey(String key) {

        try {
            String targetClient = """
                DELETE FROM Clients WHERE id = ?
                """;
            PreparedStatement targetClientStatement = ConnectionFactory.createPreparedStatement(targetClient);
            targetClientStatement.setString(1, key);

            targetClientStatement.executeQuery();
            return true;

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Client client) {
        return deleteByKey(client.getId());
    }

}
