package com.example.lavarapido.application.controller;

import com.example.lavarapido.application.repository.daoimplements.ClientDaoJdbc;
import com.example.lavarapido.application.repository.daoimplements.ServiceDaoJdbc;
import com.example.lavarapido.application.view.WindowLoader;
import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.util.List;

import static com.example.lavarapido.application.main.Main.inactivateServiceUseCase;

public class ServiceManagementUIController {

    @FXML
    private TableView<Service> tableView;
    @FXML
    private TableColumn<Service, String> cName;
    @FXML
    private TableColumn<Service, String> cCategory;
    @FXML
    private TableColumn<Service, Double> cPrice;
    @FXML
    private ObservableList<Service> tableData;

    @FXML
    public void initialize() {
        bindTableViewToItemsList();
        bindColumnsToValueSources();
        loadServices();
    }

    private void bindTableViewToItemsList() {
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);
    }

    private void bindColumnsToValueSources() {
        cName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        cPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void loadServices() {
        ServiceDaoJdbc serviceDaoJdbc = new ServiceDaoJdbc();
        List<Service> services = serviceDaoJdbc.findAll();
        tableData.clear();
        tableData.addAll(services);

        System.out.println("Total de serviços carregados: " + services.size());
    }

    private void showServiceInMode(UIMode mode) throws IOException {
        Service selectedItem = tableView.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            WindowLoader.setRoot("ServiceUI");
            ServiceUIController controller = (ServiceUIController) WindowLoader.getController();
            controller.setService(selectedItem, mode);
        }
    }

    public void inactivateService(ActionEvent actionEvent) {
        Service selectedService = tableView.getSelectionModel().getSelectedItem();
        if (selectedService != null) {
            try {
                inactivateServiceUseCase.inactivate(selectedService);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Serviço Inativado");
                alert.showAndWait();
                loadServices();
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Erro ao inativar o serviço.");
                alert.showAndWait();
            }
        }
    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("MainUI");
    }

    public void detailService(ActionEvent actionEvent) throws IOException {
        showServiceInMode(UIMode.VIEW);
    }

    public void editService(ActionEvent actionEvent) throws IOException {
        showServiceInMode(UIMode.VIEW);
    }

    public void createService(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("ServiceUI");
    }
}
