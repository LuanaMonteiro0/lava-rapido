package com.example.lavarapido.usecases.report.servicesReport;

import com.example.lavarapido.domain.entities.service.Service;

import java.time.LocalDate;
import java.util.List;

public class ReportRequestService {
    private final List<Service> services;
    private final LocalDate initialDate;
    private final LocalDate finalDate;

    public ReportRequestService(List<Service> services, LocalDate initialDate, LocalDate finalDate) {
        this.services = services;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
    }

    public List<Service> getServices() {
        return services;
    }

    public LocalDate getInitialDate() {
        return initialDate;
    }

    public LocalDate getFinalDate() {
        return finalDate;
    }
}
