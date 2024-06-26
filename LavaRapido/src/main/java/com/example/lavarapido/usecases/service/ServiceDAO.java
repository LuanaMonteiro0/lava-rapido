package com.example.lavarapido.usecases.service;

import com.example.lavarapido.domain.entities.service.Service;
import com.example.lavarapido.usecases.utils.DAO;

import java.util.Optional;

public interface ServiceDAO extends DAO<Service, String> {
    Optional<Service> findOneByName(String name);
}
