package com.example.lavarapido.usecases.Report.ClientsReport;

import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.scheduling.Scheduling;
import com.example.lavarapido.domain.entities.scheduling.SchedulingStatus;
import com.example.lavarapido.domain.entities.service.Service;
import com.example.lavarapido.usecases.Scheduling.SchedulingDAO;
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
import java.util.List;
import java.util.stream.Collectors;

public class AbsentClientReportUseCase {
    private final SchedulingDAO schedulingDAO;

    public AbsentClientReportUseCase(SchedulingDAO schedulingDAO) {
        this.schedulingDAO = schedulingDAO;
    }

    public void generateReport(LocalDate initialDate, LocalDate finalDate) {
        Validator<ReportRequestClient> validator = new ReportRequestClientValidator();
        Notification notification = validator.validate(new ReportRequestClient(initialDate, finalDate));

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        List<Scheduling> schedules = schedulingDAO.findBetween(initialDate, finalDate);

        List<Scheduling> filteredSchedules = filterAbsentSchedules(schedules);

        exportReport(filteredSchedules, initialDate, finalDate);
    }

    private List<Scheduling> filterAbsentSchedules(List<Scheduling> schedules) {
        return schedules.stream()
                .filter(scheduling -> scheduling.getSchedulingStatus() == SchedulingStatus.ABSENT)
                .toList();
    }

    private void exportReport(List<Scheduling> schedules, LocalDate initialDate, LocalDate finalDate) {
        String dest = "absent_clients_report.pdf";
        try {
            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Absent Clients Report")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold()
                    .setFontSize(16));

            document.add(new Paragraph("Period: " + formatDate(initialDate) + " to " + formatDate(finalDate))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(12));

            Table table = new Table(3);
            table.addCell("Client Name");
            table.addCell("Service Name");
            table.addCell("Service Date");

            for (Scheduling scheduling : schedules) {
                table.addCell(scheduling.getClient().getName());
                String serviceNames = scheduling.getServices().stream()
                        .map(Service::getName)
                        .collect(Collectors.joining(", "));
                table.addCell(serviceNames);
                table.addCell(formatDate(scheduling.getDate()));
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
