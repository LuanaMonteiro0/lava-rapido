package com.example.lavarapido.usecases.Report.ClientsReport;

import com.example.lavarapido.usecases.utils.Notification;
import com.example.lavarapido.usecases.utils.Validator;

public class ReportRequestClientValidator extends Validator<ReportRequestClient> {
    @Override
    public Notification validate(ReportRequestClient request) {

        Notification notification = new Notification();

        if (request.getInitialDate() == null)
            notification.addError("Initial date cannot be null.");

        if (request.getFinalDate() == null)
            notification.addError("Final date cannot be null.");

        if (request.getInitialDate().isAfter(request.getFinalDate()))
            notification.addError("Initial date cannot be after final date.");

        return notification;
    }
}
