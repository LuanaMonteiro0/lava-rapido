package com.example.lavarapido.usecases.Report;

import com.example.lavarapido.domain.entities.scheduling.FormOfPayment;
import com.example.lavarapido.domain.entities.scheduling.Scheduling;
import com.example.lavarapido.usecases.Scheduling.SchedulingDAO;
import com.example.lavarapido.usecases.utils.DateValidator;
import com.example.lavarapido.usecases.utils.Notification;
import com.example.lavarapido.usecases.utils.Validator;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class DaysBillingReportUseCase {
    private final SchedulingDAO schedulingDAO;

    public DaysBillingReportUseCase(SchedulingDAO schedulingDAO) {
        this.schedulingDAO = schedulingDAO;
    }

    public void generateReport(LocalDate date) {
        Validator<LocalDate> validator = new DateValidator();
        Notification notification = validator.validate(date);

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        List<Scheduling> schedules = schedulingDAO.findByDate(date);
        if (schedules.isEmpty())
            throw new RuntimeException("No schedule performed on this date");


        Map<FormOfPayment, Double> totalByPaymentMethod = new EnumMap<>(FormOfPayment.class);
        double totalRevenue = 0;

        for (Scheduling scheduling : schedules) {
            scheduling.calculateTotal();
            double totalValue = scheduling.getTotalValue();

            totalRevenue += totalValue;

            totalByPaymentMethod.merge(scheduling.getFormOfPayment(), totalValue, Double::sum);
        }

        exportReport(date, totalRevenue, totalByPaymentMethod, schedules);

    }

    private void exportReport(LocalDate date, double totalRevenue, Map<FormOfPayment,
            Double> totalsByPaymentMethod, List<Scheduling> schedules) {
        // ver como exporta para PDF
        // https://www.youtube.com/watch?v=ylaP8LyoKog&list=PLz3sH_KSH-y_hyudbNhHk3Egdsn9Zj5SJ

    }
}
