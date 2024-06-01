package com.example.lavarapido.usecases.Scheduling;

import com.example.lavarapido.domain.entities.scheduling.Scheduling;
import com.example.lavarapido.usecases.utils.DAO;

import java.time.LocalDate;
import java.util.List;

public interface SchedulingDAO extends DAO<Scheduling, String> {
    List<Scheduling> findByDate(LocalDate date);
    List<Scheduling> findByServiceId(String id);
    List<Scheduling> findBetween(LocalDate initialDate, LocalDate finalDate);
}
