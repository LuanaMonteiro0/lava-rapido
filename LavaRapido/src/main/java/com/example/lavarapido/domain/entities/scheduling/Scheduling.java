package com.example.lavarapido.domain.entities.scheduling;

import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.service.Service;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Scheduling {

    UUID uuid = UUID.randomUUID();
    private String id = uuid.toString();

    private Client client; //final?

    private Vehicle vehicle; //final?

    private FormOfPayment formOfPayment;

    private LocalDate date;

    private LocalDate hour; //optional

    private double totalValue;

    private double discount; //optional

    private SchedulingStatus schedulingStatus;

    private final List<Service> services = new ArrayList<>();



    public Scheduling(LocalDate date, double totalValue, FormOfPayment formOfPayment, Service service, Client client, Vehicle vehicle) {
        this.date = date;
        this.totalValue = totalValue;
        this.formOfPayment = formOfPayment;
        this.schedulingStatus = SchedulingStatus.PENDING;
        services.add(service);
        this.client = client;
        this.vehicle = vehicle;
    }

    public Scheduling(String id, double totalValue, double discount, LocalDate date) {
        this.id = id;
        this.totalValue = totalValue;
        this.discount = discount;
        this.date = date;
    }

    public boolean verifyDate() {
        return date.isBefore(LocalDate.now());
    }

    public void changeStatus(SchedulingStatus schedulingStatus){
        if(this.schedulingStatus != schedulingStatus){
            this.schedulingStatus = schedulingStatus;
        }
    }

    public void addService(Service service){
        //TODO:Verify if service is not already in list
        services.add(service);
    }

    public void calculateTotal(){
        VehicleCategory vc = this.vehicle.getVehicleCategory();
        totalValue = services.stream().mapToDouble(service -> service.getPrice().get(vc)).sum();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getHour() {
        return hour;
    }

    public void setHour(LocalDate hour) {
        this.hour = hour;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FormOfPayment getFormOfPayment() {
        return formOfPayment;
    }

    public void setFormOfPayment(FormOfPayment formOfPayment) {
        this.formOfPayment = formOfPayment;
    }

    public Client getClient() {
        return client;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public SchedulingStatus getSchedulingStatus() {
        return schedulingStatus;
    }

    public void setSchedulingStatus(SchedulingStatus schedulingStatus) {
        this.schedulingStatus = schedulingStatus;
    }

    public List<Service> getServices() {
        return services;
    }
}
