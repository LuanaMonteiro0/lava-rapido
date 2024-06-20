package com.example.lavarapido.application.controller;

import com.example.lavarapido.application.view.WindowLoader;
import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.scheduling.Scheduling;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;


public class MainUIController {

    @FXML
    private TableView<Scheduling> tableView;
    @FXML
    private TableColumn<Scheduling, String> cClient;
    @FXML
    private TableColumn<Scheduling, String> cVehicle;


    private ObservableList<Scheduling> tableData;
    private Client client;
    private Vehicle selectedVehicle;

    public void findClient(ActionEvent actionEvent) {
    }

    public void clientManegement(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("ClientManegementUI");
    }

    public void vehicleManegement(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("VehicleManegementUI");
    }

    public void vehicleCategoryManegement(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("VehicleCategoryManegementUI");
    }
}
