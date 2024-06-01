package com.example.lavarapido.domain.entities.report;

import com.example.lavarapido.domain.entities.scheduling.Scheduling;

import java.util.List;

public class ScheduleReport implements Report {
    @Override
    public List<String> report() {
        return null;
    }

    public void addAllRows(List<Scheduling> rows) {
        for (Scheduling scheduling : rows) {

        }
    }
}
