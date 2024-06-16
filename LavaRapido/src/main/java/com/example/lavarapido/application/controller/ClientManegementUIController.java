package com.example.lavarapido.application.controller;

import com.example.lavarapido.application.view.WindowLoader;
import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.scheduling.Scheduling;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import com.example.lavarapido.application.repository.daoimplements.ClientDaoJdbc;
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
import java.util.stream.Collectors;

import static br.edu.ifps.luana.application.main.Main.deleteClientUseCase;

public class ClientManegementUIController {

    @FXML
    private TableView<Client> tableView;
    @FXML
    private TableColumn<Client, String> cName;
    @FXML
    private TableColumn<Client, String> cPhone;
    @FXML
    private TableColumn<Client, String> cCPF;
    @FXML
    private TableColumn<Client, String> cVehicle;
    @FXML
    private TableColumn<Client, String> cStatus;
    @FXML
    private TableColumn<Client, String> cScheduling;

    private ObservableList<Client> tableData;

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
        cName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        cCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        //TODO: Veiculo Não é obrigatório existir em cliente.
        /*cVehicle.setCellValueFactory(param -> {
            String models = param.getValue().getVehicles().stream()
                    .map(Vehicle::getModel)
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(models);
        });*/
        cScheduling.setCellValueFactory(param -> {
            List<Scheduling> schedulings = param.getValue().getSchedulings();
            if (schedulings != null && !schedulings.isEmpty()) {
                String schedulingInfo = schedulings.stream()
                        .map(scheduling -> scheduling.getServices().stream()
                                .map(service -> service.getName() + " (" + scheduling.getDate() + ")")
                                .collect(Collectors.joining(", ")))
                        .collect(Collectors.joining("; "));
                return new SimpleStringProperty(schedulingInfo);
            } else {
                return new SimpleStringProperty("No Scheduling");
            }
        });
    }

    private void loadDataAndShow() {
        ClientDaoJdbc clientDaoJdbc = new ClientDaoJdbc();
        List<Client> clients = clientDaoJdbc.findAll();
        tableData.clear();
        tableData.addAll(clients);
    }

    private void showClientInMode(UIMode mode) throws IOException {
        Client selectedItem = tableView.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            WindowLoader.setRoot("ClientUI");
            ClientUIController controller = (ClientUIController) WindowLoader.getController();
            controller.setClient(selectedItem, mode);
        }
    }

    public void deleteClient(ActionEvent actionEvent) {
        Client selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem !=null) {
            deleteClientUseCase.delete(selectedItem);
            loadDataAndShow();
        }
    }

    public void editClient(ActionEvent actionEvent) throws IOException {
        showClientInMode(UIMode.UPDATE);
    }

    public void createClient(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("ClientUI");
    }

    public void detailClient(ActionEvent actionEvent) throws IOException {
        showClientInMode(UIMode.VIEW);
    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("MainUI");
    }

}