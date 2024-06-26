package com.example.lavarapido.usecases.client;

import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.usecases.utils.Notification;
import com.example.lavarapido.usecases.utils.Validator;

public class ClientInputRequestValidator extends Validator<Client> {
    @Override
    public Notification validate(Client client) {

        Notification notification = new Notification();

        if (client == null) {
            notification.addError("Cliente é nulo.");
            return notification;
        }

        if (nullOrEmpty(client.getName()))
            notification.addError("Nome é nulo ou vazio.");
        if (nullOrEmpty(client.getCpfString()))
            notification.addError("CPF é nulo ou vazio.");
        if (nullOrEmpty(client.getPhone()))
            notification.addError("Telefone é nulo ou vazio.");

        return notification;
    }
}
