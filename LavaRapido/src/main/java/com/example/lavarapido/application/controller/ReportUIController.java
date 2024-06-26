package com.example.lavarapido.application.controller;

import com.example.lavarapido.application.repository.daoimplements.ServiceDaoJdbc;
import com.example.lavarapido.application.view.WindowLoader;
import com.example.lavarapido.domain.entities.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static com.example.lavarapido.application.main.Main.daysBillingReportUseCase;
import static com.example.lavarapido.application.main.Main.servicesPerformedReportUseCase;

public class ReportUIController {

    public void createReportAbsentClients(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("ReportAbsentClients");
    }

    public void createServicesPerformedReport(ActionEvent actionEvent) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Selecione Data e Serviços");

        DatePicker initialDatePicker = new DatePicker();
        initialDatePicker.setPromptText("Selecione a data inicial");

        DatePicker finalDatePicker = new DatePicker();
        finalDatePicker.setPromptText("Selecione a data final");

        ServiceDaoJdbc serviceDaoJdbc = new ServiceDaoJdbc();
        List<Service> allServices = serviceDaoJdbc.findAll();
        ListView<Service> servicesListView = new ListView<>();
        servicesListView.getItems().addAll(allServices);
        servicesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Configurar ListCell para exibir apenas o nome do serviço
        servicesListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Service> call(ListView<Service> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Service item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.getName());
                        }
                    }
                };
            }
        });

        Button confirmButton = new Button("Gerar Relatório");
        confirmButton.setOnAction(e -> {
            LocalDate initialDate = initialDatePicker.getValue();
            LocalDate finalDate = finalDatePicker.getValue();
            List<Service> selectedServices = servicesListView.getSelectionModel().getSelectedItems();

            if (initialDate != null && finalDate != null && !selectedServices.isEmpty()) {
                servicesPerformedReportUseCase.createReport(selectedServices, initialDate, finalDate);
                stage.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Relatório gerado com sucesso!", ButtonType.OK);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Por favor, selecione ambas as datas e pelo menos um serviço.", ButtonType.OK);
                alert.showAndWait();
            }
        });

        VBox vbox = new VBox(initialDatePicker, finalDatePicker, servicesListView, confirmButton);
        vbox.setSpacing(10);

        Scene scene = new Scene(vbox, 400, 300);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void createDaysBillingReport(ActionEvent actionEvent) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Selecione Data");

        DatePicker datePicker = new DatePicker();

        Button confirmButton = new Button("Gerar Relatório");
        confirmButton.setOnAction(e -> {
            LocalDate selectedDate = datePicker.getValue();
            if (selectedDate != null) {
                daysBillingReportUseCase.generateReport(selectedDate);
                stage.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Relatório gerado com sucesso!", ButtonType.OK);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Por favor, selecione uma data.", ButtonType.OK);
                alert.showAndWait();
            }
        });

        VBox vbox = new VBox(datePicker, confirmButton);
        vbox.setSpacing(10);

        Scene scene = new Scene(vbox, 300, 200);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("MainUI");
    }
}
