package com.example.lavarapido.usecases.Client;

import com.example.lavarapido.domain.entities.client.CPF;
import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.usecases.utils.DAO;

import java.util.List;
import java.util.Optional;

public interface ClientDAO extends DAO<Client, String> {
    Optional<List<Client>> findByName(String name);
    Optional<Client> findOneByCPF(CPF cpf);
}
