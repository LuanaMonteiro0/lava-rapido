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
            notification.addError("Agendamento é nulo.");
            return notification;
        }

        if (nullOrEmpty(String.valueOf(scheduling.getDate())))
            notification.addError("Data não pode ser vazia ou nula.");

        if (scheduling.verifyDate())
            notification.addError("Data inválida. Deve ser hoje ou uma data futura.");

        Client client = scheduling.getClient();
        if (client == null)
            notification.addError("Cliente é nulo.");
        else if (client.getStatus().equals(Status.INACTIVE))
            notification.addError("Cliente não está ativo.");

        Vehicle vehicle = scheduling.getVehicle();
        if (vehicle == null)
            notification.addError("Veículo é nulo.");

        List<Service> services = scheduling.getServices();
        if (nullOrEmpty(services))
            notification.addError("Serviços são nulos ou vazios.");
        else {
            for (Service service : services) {
                if (service == null) {
                    notification.addError("Serviço não pode ser nulo.");
                }
            }
        }

        if (scheduling.getFormOfPayment() == null) {
            notification.addError("Forma de pagamento não pode ser nula ou vazia.");
        }

        if (scheduling.getTotalValue() < 0 || Double.isNaN(scheduling.getTotalValue())) {
            notification.addError("O valor total não pode ser negativo e deve ser um número válido.");
        }

        if (scheduling.getDiscount() < 0 || Double.isNaN(scheduling.getDiscount())) {
            notification.addError("O desconto não pode ser negativo e deve ser um número válido.");
        }

        return notification;
    }
}
