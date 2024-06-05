package com.example.lavarapido.usecases.Client;

import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.usecases.utils.Notification;
import com.example.lavarapido.usecases.utils.Validator;

public class ClientInputRequestValidator extends Validator<Client> {
    @Override
    public Notification validate(Client client) {

        Notification notification = new Notification();

        if (client == null) {
            notification.addError("Client is null");
            return notification;
        }

        if (nullOrEmpty(client.getName()))
            notification.addError("Name is null or empty");
        if (nullOrEmpty(client.getCpfString()))
            notification.addError("CPF is null or empty");
        if (nullOrEmpty(client.getPhone()))
            notification.addError("Phone is null or empty");

        return notification;
    }
}
