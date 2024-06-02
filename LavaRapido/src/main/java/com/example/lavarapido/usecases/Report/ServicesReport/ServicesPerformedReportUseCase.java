package com.example.lavarapido.usecases.Report.ServicesReport;

import com.example.lavarapido.domain.entities.scheduling.FormOfPayment;
import com.example.lavarapido.domain.entities.scheduling.Scheduling;
import com.example.lavarapido.domain.entities.service.Service;
import com.example.lavarapido.usecases.Scheduling.SchedulingDAO;
import com.example.lavarapido.usecases.Service.ServiceDAO;
import com.example.lavarapido.usecases.utils.EntityNotFoundException;
import com.example.lavarapido.usecases.utils.Notification;
import com.example.lavarapido.usecases.utils.Validator;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ServicesPerformedReportUseCase {
    private final SchedulingDAO schedulingDAO;
    private final ServiceDAO serviceDAO;

    public ServicesPerformedReportUseCase(SchedulingDAO schedulingDAO, ServiceDAO serviceDAO) {
        this.schedulingDAO = schedulingDAO;
        this.serviceDAO = serviceDAO;
    }

    public void createReport(List<Service> services, LocalDate initialDate, LocalDate finalDate) {
        Validator<ReportRequestService> validator = new ReportRequestServiceValidator();
        Notification notification = validator.validate(new ReportRequestService(services, initialDate, finalDate));

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        Map<FormOfPayment, Double> totalsByPaymentMethod = new EnumMap<>(FormOfPayment.class);
        double totalRevenue = 0;

        for (Service service : services) {
            if (serviceDAO.findOne(service.getId()).isEmpty()) {
                throw new EntityNotFoundException("Service with ID " + service.getId() + " not found.");
            }

            List<Scheduling> schedules = schedulingDAO.findByServiceId(service.getId());

            for (Scheduling scheduling : schedules) {
                if (scheduling.getDate().isBefore(initialDate) || scheduling.getDate().isAfter(finalDate)) {
                    continue;
                }
                scheduling.calculateTotal();
                double totalValue = scheduling.getTotalValue();
                totalRevenue += totalValue;
                totalsByPaymentMethod.merge(scheduling.getFormOfPayment(), totalValue, Double::sum);
            }
        }

        exportReport(initialDate, finalDate, totalRevenue, totalsByPaymentMethod);
    }

    private void exportReport(LocalDate initialDate, LocalDate finalDate, double totalRevenue,
                              Map<FormOfPayment, Double> totalsByPaymentMethod) {
        // ver como exporta para PDF
        // https://www.youtube.com/watch?v=ylaP8LyoKog&list=PLz3sH_KSH-y_hyudbNhHk3Egdsn9Zj5SJ

    }
}
