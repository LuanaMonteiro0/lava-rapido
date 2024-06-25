package com.example.lavarapido.application.view;

import com.example.lavarapido.domain.entities.scheduling.Scheduling;

import java.util.List;

public class SchedulingView {
    private Scheduling scheduling;
    private List<String> serviceNames;

    public SchedulingView(Scheduling scheduling, List<String> serviceNames) {
        this.scheduling = scheduling;
        this.serviceNames = serviceNames;
    }

    public Scheduling getScheduling() {
        return scheduling;
    }

    public void setScheduling(Scheduling scheduling) {
        this.scheduling = scheduling;
    }

    public List<String> getServiceNames() {
        return serviceNames;
    }

    public void setServiceNames(List<String> serviceNames) {
        this.serviceNames = serviceNames;
    }

    public String getFormOfPayment() {
        return scheduling.getFormOfPayment().toString();
    }

    public String getDateHour() {
        return scheduling.getDate() + " " + scheduling.getHour();
    }

    public Double getTotalValue() {
        return scheduling.getTotalValue();
    }

    public String getStatus() {
        return scheduling.getSchedulingStatus().toString();
    }

    public Double getDiscount() {
        return scheduling.getDiscount();
    }

    public String getClientName() {
        return scheduling.getClient().getName();
    }

    public String getVehiclePlate() {
        return scheduling.getVehicle().getPlate().getLicensePlate();
    }

    public String getServiceNamesAsString() {
        return String.join(", ", serviceNames);
    }
}
