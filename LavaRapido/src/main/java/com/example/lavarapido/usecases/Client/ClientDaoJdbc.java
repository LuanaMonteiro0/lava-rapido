package com.example.lavarapido.usecases.Client;

import com.example.lavarapido.domain.entities.client.CPF;
import com.example.lavarapido.domain.entities.client.Client;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

public class ClientDaoJdbc implements ClientDAO {

    private Client instanciarCliente(ResultSet resultSet) {
        Client client = new Client(
                resultSet.getString("id"),
                resultSet.getString("name"),
                
        );
    }


    @Override
    public Optional<List<Client>> findByName(String name) {



        return Optional.empty();
    }

    @Override
    public Optional<Client> findOneByCPF(CPF cpf) {
        return Optional.empty();
    }

    @Override
    public String create(Client type) {
        return "";
    }

    @Override
    public Optional<Client> findOne(String key) {
        return Optional.empty();
    }

    @Override
    public List<Client> findAll() {
        return List.of();
    }

    @Override
    public boolean update(Client type) {
        return false;
    }

    @Override
    public boolean deleteByKey(String key) {
        return false;
    }

    @Override
    public boolean delete(Client type) {
        return false;
    }
}
