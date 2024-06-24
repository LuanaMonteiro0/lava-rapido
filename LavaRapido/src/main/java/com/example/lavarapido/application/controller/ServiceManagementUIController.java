package com.example.lavarapido.application.controller;

import com.example.lavarapido.application.repository.daoimplements.ServiceDaoJdbc;
import com.example.lavarapido.application.repository.daoimplements.ServicesPricesDaoJdbc;
import com.example.lavarapido.application.repository.daoimplements.VehicleCategoryDaoJdbc;
import com.example.lavarapido.application.view.ServiceView;
import com.example.lavarapido.application.view.WindowLoader;
import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.service.Service;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;
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
import java.util.Map;
import java.util.Optional;

import static com.example.lavarapido.application.main.Main.inactivateServiceUseCase;
import static com.example.lavarapido.application.main.Main.reactiveServiceUseCase;

public class ServiceManagementUIController {

    @FXML
    private TableView<ServiceView> tableView;
    @FXML
    private TableColumn<ServiceView, String> cName;
    @FXML
    private TableColumn<ServiceView, String> cCategory;
    @FXML
    private TableColumn<ServiceView, Double> cPrice;
    @FXML
    private TableColumn<ServiceView, String> cStatus;
    @FXML
    private ObservableList<ServiceView> tableData;

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
        cStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadServices() {
        ServiceDaoJdbc serviceDaoJdbc = new ServiceDaoJdbc();
        ServicesPricesDaoJdbc servicesPricesDaoJdbc = new ServicesPricesDaoJdbc();
        List<Service> services = serviceDaoJdbc.findAll();
        tableData.clear();

        for (Service service : services) {
            var pricesMap = servicesPricesDaoJdbc.findPricesByServiceId(service.getId());
            for (Map.Entry<VehicleCategory, Double> entry : pricesMap.entrySet()) {
                ServiceView view = new ServiceView(service, entry.getKey(), entry.getValue());
                tableData.add(view);
            }
        }
        System.out.println("Total de serviços carregados: " + services.size());
    }

    private void showServiceInMode(UIMode mode) throws IOException {
        ServiceView selectedItem = tableView.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            WindowLoader.setRoot("ServiceUI");
            ServiceUIController controller = (ServiceUIController) WindowLoader.getController();
            controller.setService(selectedItem.getService(), mode);
        }
    }

    public void changeStatusService(ActionEvent actionEvent) {
        ServiceView selectedServiceView = tableView.getSelectionModel().getSelectedItem();
        if (selectedServiceView != null) {
            Service selectedService = selectedServiceView.getService();
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

//    public void detailService(ActionEvent actionEvent) throws IOException {
//        showServiceInMode(UIMode.VIEW);
//    }

    public void editService(ActionEvent actionEvent) throws IOException {
        showServiceInMode(UIMode.UPDATE);
    }

    public void createService(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("ServiceUI");
    }

}
