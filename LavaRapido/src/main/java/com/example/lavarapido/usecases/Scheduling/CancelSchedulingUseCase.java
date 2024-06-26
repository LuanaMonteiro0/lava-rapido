package com.example.lavarapido.usecases.Scheduling;

import com.example.lavarapido.domain.entities.scheduling.Scheduling;
import com.example.lavarapido.usecases.utils.EntityNotFoundException;
import com.example.lavarapido.usecases.utils.ShowAlert;

public class CancelSchedulingUseCase {

    private final SchedulingDAO schedulingDAO;

    public CancelSchedulingUseCase(SchedulingDAO schedulingDAO) {
        this.schedulingDAO = schedulingDAO;
    }

    public boolean cancel(Scheduling scheduling) {
        String id = scheduling.getId();
        if (schedulingDAO.findOne(id).isEmpty()) {
            ShowAlert.showErrorAlert("Agendamento não encontrado.");
            throw new EntityNotFoundException("Agendamento não encontrado.");
        }

        if (scheduling.verifyDate()) {
            ShowAlert.showErrorAlert("A data do agendamento deve ser hoje ou uma data futura.");
            throw new IllegalArgumentException("A data do agendamento deve ser hoje ou uma data futura.");
        }

        return schedulingDAO.delete(scheduling);
    }
}
