package com.example.lavarapido.application.repository.daoimplements;

import com.example.lavarapido.application.repository.database.ConnectionFactory;
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

    private Client createClientFromDbQuery(ResultSet resultSet) throws SQLException {
        Client client = new Client(
                resultSet.getString("id"),
                resultSet.getString("name"),
                new CPF(resultSet.getString("cpf")),
                new Telephone(resultSet.getString("phone"))
        );

        if (client.getVehicles() == null) {
            client.setVehicles(new ArrayList<>());
        }

        return client;
    }

    @Override
    public Optional<List<Client>> findByName(String name) {

        try {
            String targetClient = """
                SELECT * FROM Clients WHERE name = ? AND status = "ACTIVE"
                """;
            PreparedStatement targetClientStatement = ConnectionFactory.createPreparedStatement(targetClient);
            targetClientStatement.setString(1, name);

            ResultSet res = targetClientStatement.executeQuery();
            List<Client> myClients = new ArrayList<>();

            while(res.next()){
                Client c = createClientFromDbQuery(res);
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
                SELECT * FROM Clients WHERE cpf = ? AND status = "ACTIVE" 
                """;
            PreparedStatement targetClientStatement = ConnectionFactory.createPreparedStatement(targetClient);
            targetClientStatement.setString(1, cpf.getCpf());

            ResultSet res = targetClientStatement.executeQuery();
            if (res.next()) {
                Client myClient = createClientFromDbQuery(res);
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

            targetClientStatement.executeUpdate();

            return "Client inserted";

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return "Client not inserted";

    }

    @Override
    public Optional<Client> findOne(String clientId) {
        try {
            String targetClient = """
                SELECT * FROM Clients WHERE id = ? AND status = "ACTIVE"
                """;
            PreparedStatement targetClientStatement = ConnectionFactory.createPreparedStatement(targetClient);
            targetClientStatement.setString(1, clientId);

            ResultSet res = targetClientStatement.executeQuery();
            if (res.next()) {
                Client myClient = createClientFromDbQuery(res);
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
                SELECT * FROM Clients WHERE status = "ACTIVE"
                """;
            PreparedStatement targetClientStatement = ConnectionFactory.createPreparedStatement(targetClient);

            ResultSet res = targetClientStatement.executeQuery();

            while(res.next()){
                myClients.add(createClientFromDbQuery(res));
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

                targetClientStatement.executeUpdate();

                return true;

            } catch(SQLException e) {
                e.printStackTrace();
            }

            return false;

    }

    @Override
    public boolean deleteByKey(String clientId) {
        try {
            String targetClient = """
                DELETE FROM Clients WHERE id = ?
                """;
            PreparedStatement targetClientStatement = ConnectionFactory.createPreparedStatement(targetClient);
            targetClientStatement.setString(1, clientId);

            targetClientStatement.executeUpdate();
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
