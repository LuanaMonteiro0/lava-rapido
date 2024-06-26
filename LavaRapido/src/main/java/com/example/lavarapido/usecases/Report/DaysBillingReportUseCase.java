package com.example.lavarapido.usecases.Report;

import com.example.lavarapido.domain.entities.scheduling.FormOfPayment;
import com.example.lavarapido.domain.entities.scheduling.Scheduling;
import com.example.lavarapido.usecases.Scheduling.SchedulingDAO;
import com.example.lavarapido.usecases.utils.DateValidator;
import com.example.lavarapido.usecases.utils.Notification;
import com.example.lavarapido.usecases.utils.ShowAlert;
import com.example.lavarapido.usecases.utils.Validator;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class DaysBillingReportUseCase {
    private final SchedulingDAO schedulingDAO;

    public DaysBillingReportUseCase(SchedulingDAO schedulingDAO) {
        this.schedulingDAO = schedulingDAO;
    }

    public void generateReport(LocalDate date) {
        Validator<LocalDate> validator = new DateValidator();
        Notification notification = validator.validate(date);

        if (notification.hasErrors()) {
            ShowAlert.showErrorAlert(notification.errorMessage());
            throw new IllegalArgumentException(notification.errorMessage());
        }

        List<Scheduling> schedules = schedulingDAO.findByDate(date);
        if (schedules.isEmpty())
            throw new RuntimeException("No schedule performed on this date");

        Map<FormOfPayment, Double> totalByPaymentMethod = new EnumMap<>(FormOfPayment.class);
        double totalRevenue = 0;

        for (Scheduling scheduling : schedules) {
            double totalValue = scheduling.getTotalValue();

            totalRevenue += totalValue;

            totalByPaymentMethod.merge(scheduling.getFormOfPayment(), totalValue, Double::sum);
        }

        exportReport(date, totalRevenue, totalByPaymentMethod, schedules);

    }

    private void exportReport(LocalDate date, double totalRevenue, Map<FormOfPayment, Double> totalsByPaymentMethod, List<Scheduling> schedules) {
        String dest = "billing_report_" + date.toString() + ".pdf";
        try {
            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Billing Report for " + date.toString())
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold()
                    .setFontSize(16));

            document.add(new Paragraph("Total Revenue: $" + totalRevenue)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(12));

            Table table = new Table(2);
            table.addCell("Form of Payment");
            table.addCell("Total Amount");

            for (Map.Entry<FormOfPayment, Double> entry : totalsByPaymentMethod.entrySet()) {
                table.addCell(entry.getKey().toString());
                table.addCell("$" + entry.getValue());
            }

            document.add(table);

            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
