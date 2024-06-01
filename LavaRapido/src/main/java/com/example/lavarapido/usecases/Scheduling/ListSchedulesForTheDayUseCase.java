package com.example.lavarapido.usecases.Scheduling;

import com.example.lavarapido.domain.entities.scheduling.Scheduling;
import com.example.lavarapido.usecases.utils.DateValidator;
import com.example.lavarapido.usecases.utils.Notification;
import com.example.lavarapido.usecases.utils.Validator;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ListSchedulesForTheDayUseCase {
    private final SchedulingDAO schedulingDAO;

    public ListSchedulesForTheDayUseCase(SchedulingDAO schedulingDAO) {
        this.schedulingDAO = schedulingDAO;
    }

    public List<Scheduling> findAllForDate(LocalDate date) {
        Validator<LocalDate> validator = new DateValidator();
        Notification notification = validator.validate(date);

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        return schedulingDAO.findAll().stream()
                .filter(scheduling -> scheduling.getDate().isEqual(date))
                .collect(Collectors.toList());
    }
}
