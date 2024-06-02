package com.example.lavarapido.usecases.Report.ClientsReport;

import java.time.LocalDate;

public class ReportRequestClient {

    private final String client;
    private final LocalDate initialDate;
    private final LocalDate finalDate;

    public ReportRequestClient(String client, LocalDate initialDate, LocalDate finalDate) {
        this.client = client;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
    }

    public String getClient() {
        return client;
    }

    public LocalDate getInitialDate() {
        return initialDate;
    }

    public LocalDate getFinalDate() {
        return finalDate;
    }
}