package com.example.lavarapido.usecases.utils;

import java.time.LocalDate;
import java.util.Objects;

public class DateValidator extends Validator<LocalDate> {
    @Override
    public Notification validate(LocalDate date) {
        Notification notification = new Notification();

        if (date == null)
            notification.addError("Date is null.");

        if (Objects.requireNonNull(date).isAfter(LocalDate.now()))
            notification.addError("Date cannot be in the future");

        return notification;
    }
}
