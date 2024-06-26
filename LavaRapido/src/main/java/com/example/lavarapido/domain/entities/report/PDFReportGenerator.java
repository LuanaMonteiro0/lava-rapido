package com.example.lavarapido.domain.entities.report;

import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.scheduling.Scheduling;
import com.example.lavarapido.domain.entities.service.Service;
import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class PDFReportGenerator {

    //REPORT SERVICE
    public void generateServiceReport(String dest, List<Service> services) throws IOException {
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        document.setMargins(20, 20, 20, 20);

        // Add title
        Paragraph title = new Paragraph("Service Report")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(20);
        document.add(title);

        // Add table
        Table table = new Table(UnitValue.createPercentArray(new float[]{3, 3}))
                .useAllAvailableWidth();
        addServiceTableHeader(table);
        addServiceRows(table, services);

        document.add(table);
        document.close();
    }

    private void addServiceTableHeader(Table table) {
        table.addHeaderCell(new Cell().add(new Paragraph("Service Name")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("Price")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        //falta pegar a categoria
    }

    private void addServiceRows(Table table, List<Service> services) {
        for (Service service : services) {
            table.addCell(new Cell().add(new Paragraph(service.getName())));
        }
    }

    //REPORT SCHEDULING
    public void generateSchedulingReport(String dest, List<Scheduling> schedules) throws IOException {
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        document.setMargins(20, 20, 20, 20);

        Paragraph title = new Paragraph("Scheduling Report")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(20);
        document.add(title);

        Table table = new Table(UnitValue.createPercentArray(new float[]{3, 3, 3, 3, 3}))
                .useAllAvailableWidth();
        addSchedulingTableHeader(table);
        addSchedulingRows(table, schedules);

        document.add(table);
        document.close();
    }

    private void addSchedulingTableHeader(Table table) {
        table.addHeaderCell(new Cell().add(new Paragraph("Client")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("Vehicle")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("Date")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("Total Value")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("Payment Method")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
    }

    private void addSchedulingRows(Table table, List<Scheduling> schedules) {
        for (Scheduling scheduling : schedules) {
            table.addCell(new Cell().add(new Paragraph(scheduling.getClient().getName())));
            table.addCell(new Cell().add(new Paragraph(scheduling.getVehicle().toString())));
            table.addCell(new Cell().add(new Paragraph(scheduling.getDate().toString())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(scheduling.getTotalValue()))));
            table.addCell(new Cell().add(new Paragraph(scheduling.getFormOfPayment().toString())));
        }
    }

    //REPORT DE CLIENT
    public void generateClientReport(String dest, List<Client> clients) throws IOException {
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        document.setMargins(20, 20, 20, 20);

        Paragraph title = new Paragraph("Client Report")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(20);
        document.add(title);

        Table table = new Table(UnitValue.createPercentArray(new float[]{4, 4, 4, 4, 4, 4, 4}))
                .useAllAvailableWidth();
        addClientTableHeader(table);
        addClientRows(table, clients);

        document.add(table);
        document.close();
    }

    private void addClientTableHeader(Table table) {
        table.addHeaderCell(new Cell().add(new Paragraph("ID")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("Name")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("CPF")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("Phone")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("Status")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("Schedulings")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("Vehicles")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
    }

    private void addClientRows(Table table, List<Client> clients) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Client client : clients) {
            StringBuilder vehiclesList = new StringBuilder();
            for (int i = 0; i < client.getVehicles().size(); i++) {
                vehiclesList.append(client.getVehicles().get(i).getModel());
                if (i < client.getVehicles().size() - 1) {
                    vehiclesList.append(", ");
                }
            }

            table.addCell(new Cell().add(new Paragraph(client.getId())));
            table.addCell(new Cell().add(new Paragraph(client.getName())));
            table.addCell(new Cell().add(new Paragraph(client.getCpfString())));
            table.addCell(new Cell().add(new Paragraph(client.getPhone())));
            table.addCell(new Cell().add(new Paragraph(client.getStatus().toString())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(client.getSchedulings().size()))));
            table.addCell(new Cell().add(new Paragraph(vehiclesList.toString())));
        }
    }
}
