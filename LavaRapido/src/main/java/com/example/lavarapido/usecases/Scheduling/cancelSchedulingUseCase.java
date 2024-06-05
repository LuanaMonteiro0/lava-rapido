package com.example.lavarapido.usecases.Scheduling;

import com.example.lavarapido.domain.entities.scheduling.Scheduling;
import com.example.lavarapido.usecases.utils.EntityNotFoundException;

public class cancelSchedulingUseCase {

    private final SchedulingDAO schedulingDAO;

    public cancelSchedulingUseCase(SchedulingDAO schedulingDAO) {
        this.schedulingDAO = schedulingDAO;
    }

    public boolean cancel(Scheduling scheduling) {
        String id = scheduling.getId();
        if (schedulingDAO.findOne(id).isEmpty())
            throw new EntityNotFoundException("Scheduling not found.");

        if (scheduling.verifyDate())
            throw new IllegalArgumentException("The scheduling date must be today or a future date.");

        return schedulingDAO.delete(scheduling);
    }
}
