package com.example.lavarapido.usecases.Scheduling;

import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.scheduling.Scheduling;
import com.example.lavarapido.domain.entities.service.Service;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import com.example.lavarapido.usecases.utils.Notification;
import com.example.lavarapido.usecases.utils.Validator;

import java.util.List;

public class SchedulingInputRequestValidator extends Validator<Scheduling> {
    @Override
    public Notification validate(Scheduling scheduling) {

        Notification notification = new Notification();

        if (scheduling == null) {
            notification.addError("Scheduling is null.");
            return notification;
        }

        if (scheduling.verifyDate())
            notification.addError("The scheduling date is invalid. It must be today or a future date.");

        Client client = scheduling.getClient();
        if (client == null)
            notification.addError("Client is null.");
        else if (client.getStatus().equals(Status.INACTIVE))
            notification.addError("Client is not active.");


        Vehicle vehicle = scheduling.getVehicle();
        if (vehicle == null)
            notification.addError("Vehicle is null.");

        List<Service> services = scheduling.getServices();
        if (nullOrEmpty(services))
            notification.addError("Services is null or empty.");
        else {
            for (Service service : services) {
                if (service == null) {
                    notification.addError("Service cannot be null.");
                }
            }
        }

        if (scheduling.getFormOfPayment() == null) {
            notification.addError("Form of payment cannot be null.");
        }

        if (scheduling.getTotalValue() < 0) {
            notification.addError("Total value cannot be negative.");
        }

        if (scheduling.getDiscount() < 0) {
            notification.addError("Discount cannot be negative.");
        }

        return notification;
    }
}
