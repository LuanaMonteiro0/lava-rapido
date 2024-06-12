package com.example.lavarapido.application.controller;

import com.example.lavarapido.application.view.WindowLoader;
import com.example.lavarapido.domain.entities.vehicle.LicensePlate;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

import static br.edu.ifps.luana.application.main.Main.*;

public class VehicleUIController {

    @FXML
    private TextField txtModel;
    @FXML
    private TextField txtCategory;
    @FXML
    private TextField txtColor;
    @FXML
    private TextField txtPlate;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnConfirm;

    private Vehicle vehicle;

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("VehicleManegementUI");
    }

    public void saveOrUpadate(ActionEvent actionEvent) throws IOException {

        getEntityToView();
        if(vehicle.getPlate() == null) {
            addVehicleClientUseCase.insert(vehicle);
        } else {
            updateVehicleClientUseCase.update(vehicle);
        }
        WindowLoader.setRoot("VehicleManegementUI");
    }

    public void getEntityToView() {
        if(vehicle == null) {
            vehicle = new Vehicle();
        }
        else {
            vehicle.setModel(txtModel.getText());
            vehicle.setVehicleCategory(new VehicleCategory(txtCategory.getText()));
            vehicle.setColor(txtColor.getText());
            vehicle.setPlate(new LicensePlate(txtPlate.getText()));
        }
    }

    public void setVehicle(Vehicle vehicle, UIMode mode) {
        if(vehicle == null)
            throw new IllegalArgumentException("Client can not be null.");

        this.vehicle = vehicle;
        setEntityToview();

        if(mode == UIMode.VIEW)
            configureViewMode();
    }

    public void setEntityToview() {
        txtModel.setText(vehicle.getModel());
        txtCategory.setText(vehicle.getVehicleCategory().getName());
        txtColor.setText(vehicle.getColor());
        txtPlate.setText(vehicle.getPlate().getLicensePlate());
    }

    private void configureViewMode() {
        btnCancel.setLayoutX(btnConfirm.getLayoutX());
        btnCancel.setLayoutY(btnConfirm.getLayoutY());
        btnCancel.setText("Fechar");

        btnConfirm.setVisible(false);

        txtModel.setDisable(true);
        txtCategory.setDisable(true);
        txtColor.setDisable(true);
        txtPlate.setDisable(true);
    }
}
