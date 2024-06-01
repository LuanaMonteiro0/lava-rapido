package com.example.lavarapido.usecases.Report.ClientsReport;

import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.scheduling.Scheduling;
import com.example.lavarapido.domain.entities.scheduling.SchedulingStatus;
import com.example.lavarapido.usecases.Client.ClientDAO;
import com.example.lavarapido.usecases.Scheduling.SchedulingDAO;
import com.example.lavarapido.usecases.utils.EntityNotFoundException;
import com.example.lavarapido.usecases.utils.Notification;
import com.example.lavarapido.usecases.utils.Validator;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class absentClientReportUseCase {
    private final SchedulingDAO schedulingDAO;
    private final ClientDAO clientDAO;

    public absentClientReportUseCase(SchedulingDAO schedulingDAO, ClientDAO clientDAO) {
        this.schedulingDAO = schedulingDAO;
        this.clientDAO = clientDAO;
    }

    public List<Client> generateReport(String clientName, LocalDate initialDate, LocalDate finalDate) {
        Validator<ReportRequestClient> validator = new ReportRequestClientValidator();
        Notification notification = validator.validate(new ReportRequestClient(clientName, initialDate, finalDate));

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        List<Scheduling> schedules = schedulingDAO.findBetween(initialDate, finalDate);

        if (clientName != null && !clientName.isEmpty()) {
            Optional<List<Client>> clients = clientDAO.findByName(clientName);
            if (clients.isEmpty()) {
                throw new EntityNotFoundException("No clients found with the name: " + clientName);
            }
            return filterAbsentClients(schedules, clients.orElse(null));
        } else {
            return filterAbsentClients(schedules, null);
        }
    }

    private List<Client> filterAbsentClients(List<Scheduling> schedulings, List<Client> clients) {
        return schedulings.stream()
                .filter(scheduling -> scheduling.getStatus() == SchedulingStatus.ABSENT)
                .map(Scheduling::getClient)
                .filter(client -> clients == null || clients.contains(client))
                .distinct().toList();
    }

    private void exportReport(List<Client> clients) {
        // ver como exporta para PDF
        // https://www.youtube.com/watch?v=ylaP8LyoKog&list=PLz3sH_KSH-y_hyudbNhHk3Egdsn9Zj5SJ

    }
}
