package com.example.lavarapido.domain.entities.scheduling;

import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.service.Service;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;

import java.time.LocalDate;
import java.util.List;

public class Scheduling {
    private LocalDate date;
    private LocalDate hour; //optional
    private double totalValue;
    private double discount; //optional
    private long id; //olhar depois
    private FormOfPayment formOfPayment;

    private SchedulingStatus status;

    private List<Service> services;

    private Client client;

    private Vehicle vehicle;

    public Scheduling(LocalDate date, double totalValue, FormOfPayment formOfPayment, SchedulingStatus status, Service service, Client client, Vehicle vehicle) {
        this.date = date;
        this.totalValue = totalValue;
        //TODO: Automatizar geração do ID
        this.formOfPayment = formOfPayment;
        this.status = status;
        services.add(service);
        this.client = client;
        this.vehicle = vehicle;
    }

    public boolean verifyDate(){
        if(date == LocalDate.now() || date <= LocalDate.now()){
            return false;
        }else{
            return true;
        }
    }

    public void changeStatus(SchedulingStatus ss){
        if(ss != status){
            this.status = ss;
        }
    }

    public void addService(Service service){
        //TODO:Verify if service is not already in list
        services.add(service);
    }

    public double calculateTotal(){
        VehicleCategory vc = this.vehicle.getVehicleCategory();
        return services.stream().mapToDouble(service -> service.getPrice().get(vc)).sum();
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public FormOfPayment getFormOfPayment() {
        return formOfPayment;
    }

    public void setFormOfPayment(FormOfPayment formOfPayment) {
        this.formOfPayment = formOfPayment;
    }
}
