package com.example.lavarapido.application.repository.daoimplements;

import com.example.lavarapido.application.repository.database.ConnectionFactory;
import com.example.lavarapido.domain.entities.client.CPF;
import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.client.Telephone;
import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import com.example.lavarapido.usecases.Client.ClientDAO;

import java.lang.annotation.Documented;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDaoJdbc implements ClientDAO {

    protected Client createClientFromDbQuery(ResultSet resultSet) throws SQLException {
        Client client = new Client(
                resultSet.getString("id"),
                resultSet.getString("name"),
                new CPF(resultSet.getString("cpf")),
                new Telephone(resultSet.getString("phone")),
                Status.fromLabel(resultSet.getString("status"))
        );

        if (client.getVehicles() == null) {
            client.setVehicles(new ArrayList<>());
        }

        return client;
    }

    //Não usar o método abaixo
    @Override
    public Optional<List<Client>> findByName(String name) {

        try {
            String targetClient = """
                SELECT * FROM Clients WHERE name = ? AND status LIKE 'A%'
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
                SELECT id FROM Clients WHERE cpf = ? AND status LIKE 'A%'
                """;
            PreparedStatement targetClientStatement = ConnectionFactory.createPreparedStatement(targetClient);
            targetClientStatement.setString(1, cpf.getCpf());

            ResultSet res = targetClientStatement.executeQuery();

            if (res.next()) {
                Optional<Client> myClientOptional = findOne(res.getString("id"));
                return myClientOptional;
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public String create(Client client) {
        String targetClient = """
                INSERT INTO Clients (id, name, cpf, phone, status) VALUES(?, ?, ?, ?, ?);
                """;
        try(PreparedStatement targetClientStatement = ConnectionFactory.createPreparedStatement(targetClient)) {

            targetClientStatement.setString(1, client.getId());
            targetClientStatement.setString(2, client.getName());
            targetClientStatement.setString(3, client.getCpfString());
            targetClientStatement.setString(4, client.getPhone());
            targetClientStatement.setString(5, String.valueOf(client.getStatus()));

            targetClientStatement.execute();

            ResultSet resultSet = targetClientStatement.getGeneratedKeys();
            //This Vehicle was already persisted in DB
            /*
            Vehicle vehicle = client.getVehicles().getLast();


            String targetClientVehicles = """
                INSERT INTO ClientVehicles (clientId, vehicleId) VALUES(?, ?);
                """;
            PreparedStatement targetClientVehiclesStatement = ConnectionFactory.createPreparedStatement(targetClientVehicles);
            targetClientVehiclesStatement.setString(1, client.getId());
            targetClientVehiclesStatement.setString(2, vehicle.getId());

            targetClientVehiclesStatement.executeUpdate();
               */
            return resultSet.getString(1);

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Client> findOne(String clientId) {
        try {
            String targetClient = """
                SELECT * FROM Clients WHERE id = ? AND status LIKE 'A%'
                """;
            PreparedStatement targetClientStatement = ConnectionFactory.createPreparedStatement(targetClient);
            targetClientStatement.setString(1, clientId);

            ResultSet res = targetClientStatement.executeQuery();
            if (res.next()) {
                Client myClient = createClientFromDbQuery(res);

                String targetVehicles = """
                SELECT vehicleId FROM ClientVehicles WHERE clientId = ?
                """;
                PreparedStatement targetVehiclesStatement = ConnectionFactory.createPreparedStatement(targetVehicles);
                targetVehiclesStatement.setString(1, clientId);

                ResultSet resVehicles = targetVehiclesStatement.executeQuery();
                while (resVehicles.next()) {
                    VehicleDaoJdbc vdaoJdbc = new VehicleDaoJdbc();
                    Optional<Vehicle> optionalVehicle = vdaoJdbc.findOne(resVehicles.getString("vehicleId"));

                    if (optionalVehicle.isPresent()) {
                        Vehicle vTarget = optionalVehicle.get();
                        myClient.addVehicle(vTarget);
                    }
                }

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
                SELECT * FROM Clients WHERE status LIKE 'A%'
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
