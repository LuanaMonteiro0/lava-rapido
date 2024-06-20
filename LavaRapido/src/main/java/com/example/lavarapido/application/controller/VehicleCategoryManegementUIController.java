package com.example.lavarapido.application.controller;

import com.example.lavarapido.application.repository.daoimplements.ClientDaoJdbc;
import com.example.lavarapido.application.repository.daoimplements.VehicleCategoryDaoJdbc;
import com.example.lavarapido.application.view.WindowLoader;
import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class VehicleCategoryManegementUIController {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> cCategoryName;

    @FXML
    private TableView<VehicleCategory> tableView;

    private ObservableList<VehicleCategory> tableData;


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
        cCategoryName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private void loadDataAndShow() {
        VehicleCategoryDaoJdbc vehicleCategoryDaoJdbc = new VehicleCategoryDaoJdbc();
        List<VehicleCategory> vehicleCategories = vehicleCategoryDaoJdbc.findAll();
        tableData.clear();
        tableData.addAll(vehicleCategories);

        System.out.println("Total de categorias carregadas: " + vehicleCategories.size());
    }

    private void showClientInMode(UIMode mode) throws IOException {
        VehicleCategory selectedItem = tableView.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            WindowLoader.setRoot("VehicleCategoryUI");
            VehicleCategoryUIController controller = (VehicleCategoryUIController) WindowLoader.getController();
            controller.setCategory(selectedItem, mode);
        }
    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("MainUI");
    }

    public void deleteCategory(ActionEvent actionEvent) {

    }

    public void updateCategory(ActionEvent actionEvent) {

    }

    public void createCategory(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("VehicleCategoryUI");
    }
}
