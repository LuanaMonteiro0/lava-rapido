package com.example.lavarapido.application.controller;

import com.example.lavarapido.application.repository.daoimplements.VehicleDaoJdbc;
import com.example.lavarapido.application.view.WindowLoader;
import com.example.lavarapido.domain.entities.scheduling.Scheduling;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

import static com.example.lavarapido.application.main.Main.deleteVehicleClientUseCase;

public class VehicleManegementUIController {

    @FXML
    private TableView<Vehicle> tableView;
    @FXML
    private TableColumn<Vehicle, String> cModel;
    @FXML
    private TableColumn<Vehicle, String> cCategory;
    @FXML
    private TableColumn<Vehicle, String> cColor;
    @FXML
    private TableColumn<Vehicle, String> cPlate;
    @FXML
    private TableColumn<Vehicle, String> cStatus;

    private ObservableList<Vehicle> tableData;

    @FXML
    private void initialize(){
        bindTableViewToItemsList();
        bindColumnsToValueSources();
        loadDataAndShow();
    }

    private void bindTableViewToItemsList() {
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);
    }

    private void bindColumnsToValueSources() {
        cModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        cCategory.setCellValueFactory(param -> {
            Vehicle vehicle = param.getValue();
            if (vehicle != null && vehicle.getVehicleCategory() != null) {
                return new SimpleStringProperty(vehicle.getVehicleCategory().toString());
            } else {
                return new SimpleStringProperty("");
            }
        });
        cColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        cPlate.setCellValueFactory(param -> {
            Vehicle vehicle = param.getValue();
            if (vehicle != null && vehicle.getPlate() != null) {
                return new SimpleStringProperty(vehicle.getPlate().toString());
            } else {
                return new SimpleStringProperty("");
            }
        });
        cStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

    }

    private void loadDataAndShow() {
        VehicleDaoJdbc vehicleDaoJdbc = new VehicleDaoJdbc();
        List<Vehicle> vehicles = vehicleDaoJdbc.findAll();
        tableData.clear();
        tableData.addAll(vehicles);
    }

    private void showVehicleInMode(UIMode mode) throws IOException {
        Vehicle selectedItem = tableView.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            WindowLoader.setRoot("VehicleUI");
            VehicleUIController controller = (VehicleUIController) WindowLoader.getController();
            controller.setVehicle(selectedItem, mode);
        }
    }

    public void deleteVehicle() {
        Vehicle selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem !=null) {
            deleteVehicleClientUseCase.delete(selectedItem);
            loadDataAndShow();
        }
    }

    public void createVehicle() throws IOException {
        WindowLoader.setRoot("VehicleUI");
    }

    public void editVehicle() throws IOException {
        showVehicleInMode(UIMode.UPDATE);
    }

    public void detailVehicle() throws IOException {
        showVehicleInMode(UIMode.VIEW);
    }

    public void backToPreviousScene() throws IOException {
        WindowLoader.setRoot("MainUI");
    }
}
