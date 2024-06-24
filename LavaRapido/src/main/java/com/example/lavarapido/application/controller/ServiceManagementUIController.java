package com.example.lavarapido.application.controller;

import com.example.lavarapido.application.repository.daoimplements.ServiceDaoJdbc;
import com.example.lavarapido.application.view.WindowLoader;
import com.example.lavarapido.domain.entities.general.Status;
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
import static com.example.lavarapido.application.main.Main.reactiveServiceUseCase;

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
    private TableColumn<Service, Double> cStatus;
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
//        cCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        cPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
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

    public void changeStatusService(ActionEvent actionEvent) {
        Service selectedService = tableView.getSelectionModel().getSelectedItem();
        if (selectedService == null) {
            showAlert(Alert.AlertType.WARNING, "Nenhum Serviço Selecionado", "Por favor, selecione um serviço da lista.");
            return;
        }

        try {
            if (selectedService.getStatus() == Status.ACTIVE) {
                inactivateServiceUseCase.inactivate(selectedService);
                showAlert(Alert.AlertType.INFORMATION, "Serviço Inativado", "O serviço foi inativado com sucesso.");
            } else {
                reactiveServiceUseCase.reactive(selectedService);
                showAlert(Alert.AlertType.INFORMATION, "Serviço Ativado", "O serviço foi ativado com sucesso.");
            }
            loadServices();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao inativar/reativar o serviço.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
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
