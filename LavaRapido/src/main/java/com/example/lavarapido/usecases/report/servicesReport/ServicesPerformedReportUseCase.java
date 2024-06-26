package com.example.lavarapido.usecases.report.servicesReport;

import com.example.lavarapido.domain.entities.scheduling.FormOfPayment;
import com.example.lavarapido.domain.entities.scheduling.Scheduling;
import com.example.lavarapido.domain.entities.service.Service;
import com.example.lavarapido.usecases.scheduling.SchedulingDAO;
import com.example.lavarapido.usecases.service.ServiceDAO;
import com.example.lavarapido.usecases.utils.EntityNotFoundException;
import com.example.lavarapido.usecases.utils.Notification;
import com.example.lavarapido.usecases.utils.Validator;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ServicesPerformedReportUseCase {
    private final SchedulingDAO schedulingDAO;
    private final ServiceDAO serviceDAO;

    public ServicesPerformedReportUseCase(SchedulingDAO schedulingDAO, ServiceDAO serviceDAO) {
        this.schedulingDAO = schedulingDAO;
        this.serviceDAO = serviceDAO;
    }

    public void createReport(List<Service> services, LocalDate initialDate, LocalDate finalDate) {
        Validator<ReportRequestService> validator = new ReportRequestServiceValidator();
        Notification notification = validator.validate(new ReportRequestService(services, initialDate, finalDate));

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        Map<FormOfPayment, Double> totalsByPaymentMethod = new EnumMap<>(FormOfPayment.class);
        double totalRevenue = 0;

        for (Service service : services) {
            if (serviceDAO.findOne(service.getId()).isEmpty()) {
                throw new EntityNotFoundException("Service with ID " + service.getId() + " not found.");
            }

            List<Scheduling> schedules = schedulingDAO.findByServiceId(service.getId());

            for (Scheduling scheduling : schedules) {
                if (scheduling.getDate().isBefore(initialDate) || scheduling.getDate().isAfter(finalDate)) {
                    continue;
                }
                double totalValue = scheduling.getTotalValue();
                totalRevenue += totalValue;
                totalsByPaymentMethod.merge(scheduling.getFormOfPayment(), totalValue, Double::sum);
            }
        }

        exportReport(initialDate, finalDate, totalRevenue, totalsByPaymentMethod, services);
    }

    private void exportReport(LocalDate initialDate, LocalDate finalDate, double totalRevenue,
                              Map<FormOfPayment, Double> totalsByPaymentMethod, List<Service> services) {
        String dest = "services_performed_report.pdf";
        try {
            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Services Performed Report")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold()
                    .setFontSize(16));

            document.add(new Paragraph("Period: " + formatDate(initialDate) + " to " + formatDate(finalDate))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(12));

            document.add(new Paragraph("Total Revenue: " + totalRevenue)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFontSize(12));

            Table table = new Table(2);
            table.addCell("Payment Method");
            table.addCell("Total Amount");

            for (Map.Entry<FormOfPayment, Double> entry : totalsByPaymentMethod.entrySet()) {
                table.addCell(entry.getKey().toString());
                table.addCell(entry.getValue().toString());
            }

            document.add(table);

            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
