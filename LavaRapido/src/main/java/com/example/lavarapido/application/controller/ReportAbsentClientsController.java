package com.example.lavarapido.application.controller;

import com.example.lavarapido.application.view.WindowLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;

import java.io.IOException;
import java.time.LocalDate;

import static com.example.lavarapido.application.main.Main.absentClientReportUseCase;

public class ReportAbsentClientsController {
    @FXML
    private DatePicker dpFinalDate;

    @FXML
    private DatePicker dpInitialDate;

    public void create(ActionEvent actionEvent) {

        LocalDate initialDate = dpInitialDate.getValue();
        LocalDate finalDate = dpFinalDate.getValue();

        if (initialDate == null || finalDate == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, preencha todos os campos.");
            alert.showAndWait();

        } else {

            absentClientReportUseCase.generateReport(initialDate, finalDate);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Relatório Gerado");
            alert.setHeaderText(null);
            alert.setContentText("Relatório de clientes ausentes gerado com sucesso.");
            alert.showAndWait();

        }
    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("ReportUI");
    }
}
