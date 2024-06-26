package com.example.lavarapido.usecases.Report.ClientsReport;

import java.time.LocalDate;

public class ReportRequestClient {

    private final LocalDate initialDate;
    private final LocalDate finalDate;

    public ReportRequestClient(LocalDate initialDate, LocalDate finalDate) {

        this.initialDate = initialDate;
        this.finalDate = finalDate;
    }

    public LocalDate getInitialDate() {
        return initialDate;
    }

    public LocalDate getFinalDate() {
        return finalDate;
    }
}