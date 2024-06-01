package com.example.lavarapido.usecases.Report.ServicesReport;

import com.example.lavarapido.usecases.utils.Notification;
import com.example.lavarapido.usecases.utils.Validator;

public class ReportRequestServiceValidator extends Validator<ReportRequestService> {
    @Override
    public Notification validate(ReportRequestService request) {

        Notification notification = new Notification();

        if (nullOrEmpty(request.getServices()))
            notification.addError("No services selected.");

        if (request.getInitialDate() == null)
            notification.addError("Initial date cannot be null.");

        if (request.getFinalDate() == null)
            notification.addError("Final date cannot be null.");

        if (request.getInitialDate().isAfter(request.getFinalDate()))
            notification.addError("Initial date cannot be after final date.");

        return null;
    }
}
