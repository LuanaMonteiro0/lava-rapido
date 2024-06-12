package com.example.lavarapido.application.controller;

import com.example.lavarapido.application.view.WindowLoader;
import com.example.lavarapido.domain.entities.client.CPF;
import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.client.Telephone;
import com.example.lavarapido.domain.entities.scheduling.Scheduling;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static br.edu.ifps.luana.application.main.Main.createClientUseCase;
import static br.edu.ifps.luana.application.main.Main.updateClientUseCase;

public class SchedulingUIController implements Initializable {

    @FXML
    private TextField txtClient;
    @FXML
    private TextField txtVehicle;
    @FXML
    private ComboBox<String> boxPayment;
    @FXML
    private DatePicker pickerDate;
    @FXML
    private TextField txtHour;
    @FXML
    private TextField txtValue;
    @FXML
    private TextField txtDiscount;
    @FXML
    private ComboBox<String> boxStatus;
    @FXML
    private ListView<String> listService;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnConfirm;

    private Scheduling scheduling;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<String> paymentOptions = Arrays.asList("PIX", "MONEY", "CREDIT", "DEBIT");
        boxPayment.getItems().addAll(paymentOptions);

        List<String> statusOptions = Arrays.asList("PENDING", "PAID", "ABSENT");
        boxStatus.getItems().addAll(statusOptions);
    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("MainUI");
    }

    public void saveOrUpadte(ActionEvent actionEvent) throws IOException {
        getEntityToView();
    }  //todo

    public void getEntityToView() {
    } //todo

    public void setEntityToview() {
        txtClient.setText(scheduling.getClient().getName());
        txtVehicle.setText(scheduling.getVehicle().getModel());
        boxPayment.setValue(scheduling.getFormOfPayment().toString());
        pickerDate.setValue(scheduling.getDate());
        if (scheduling.getHour() != null) {
            LocalDate date = LocalDate.from(scheduling.getHour());
            LocalTime midnight = LocalTime.MIDNIGHT;
            LocalDateTime dateTime = LocalDateTime.of(date, midnight);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm"); //ta meio esquisito
            txtHour.setText(dateTime.format(dateTimeFormatter));
        } else {
            txtHour.setText("");
        }
        txtValue.setText(String.valueOf(scheduling.getTotalValue()));
        txtDiscount.setText(String.valueOf(scheduling.getDiscount()));
        boxStatus.setValue(scheduling.getSchedulingStatus().toString());
        listService.getItems().clear();
        scheduling.getServices().forEach(service -> listService.getItems().add(service.getName()));
    }

    public void setScheduling(Scheduling scheduling, UIMode mode) {
        if(scheduling == null)
            throw new IllegalArgumentException("Scheduling can not be null.");

        this.scheduling = scheduling;
        setEntityToview();

        if(mode == UIMode.VIEW)
            configureViewMode();
    }

    private void configureViewMode() {
        btnCancel.setLayoutX(btnConfirm.getLayoutX());
        btnCancel.setLayoutY(btnConfirm.getLayoutY());
        btnCancel.setText("Fechar");

        btnConfirm.setVisible(false);

        txtClient.setDisable(true);
        txtVehicle.setDisable(true);
        boxPayment.setDisable(true);
        pickerDate.setDisable(true);
        txtValue.setDisable(true);
        txtDiscount.setDisable(true);
        boxStatus.setDisable(true);
    }


}
