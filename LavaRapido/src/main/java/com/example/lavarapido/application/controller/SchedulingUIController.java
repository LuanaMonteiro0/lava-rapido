package com.example.lavarapido.application.controller;

import com.example.lavarapido.application.repository.daoimplements.ClientDaoJdbc;
import com.example.lavarapido.application.repository.daoimplements.ClientVehiclesDaoJdbc;
import com.example.lavarapido.application.repository.daoimplements.ServiceDaoJdbc;
import com.example.lavarapido.application.repository.daoimplements.VehicleDaoJdbc;
import com.example.lavarapido.application.view.WindowLoader;
import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.scheduling.FormOfPayment;
import com.example.lavarapido.domain.entities.scheduling.Scheduling;
import com.example.lavarapido.domain.entities.scheduling.SchedulingStatus;
import com.example.lavarapido.domain.entities.service.Service;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class SchedulingUIController implements Initializable {

    @FXML
    private ComboBox<FormOfPayment> boxPayment;

    @FXML
    private ComboBox<SchedulingStatus> boxStatus;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnConfirm;

    @FXML
    private ComboBox<Client> cbClient;

    @FXML
    private ComboBox<Vehicle> cbVehicles;

    @FXML
    private ListView<Service> listService;

    @FXML
    private DatePicker pickerDate;

    @FXML
    private TextField txtDiscount;

    @FXML
    private TextField txtHour;

    private Scheduling scheduling;

    private Client selectedClient;

    private ClientVehiclesDaoJdbc clientVehiclesDaoJdbc = new ClientVehiclesDaoJdbc();
    private ClientDaoJdbc clientDaoJdbc = new ClientDaoJdbc();
    private VehicleDaoJdbc vehicleDaoJdbc = new VehicleDaoJdbc();
    private ServiceDaoJdbc serviceDaoJdbc = new ServiceDaoJdbc();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureVehicleComboBox();
        configureClientComboBox();
        configureListViewServices();
        boxPayment.getItems().addAll(FormOfPayment.values());
        boxStatus.getItems().addAll(SchedulingStatus.values());

        loadAllClients();
    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("MainUI");
    }

    public void saveOrUpdate(ActionEvent actionEvent) throws IOException {
        getEntityToView();
    }

    public void getEntityToView() {
        // Implement logic to get scheduling entity from view fields
    }

    public void setEntityToView() {
        // Implement logic to set view fields based on scheduling entity
    }

    public void setScheduling(Scheduling scheduling, UIMode mode) {
        if (scheduling == null)
            throw new IllegalArgumentException("Scheduling can not be null.");

        this.scheduling = scheduling;
        setEntityToView();

        if (mode == UIMode.VIEW)
            configureViewMode();
    }

    private void configureVehicleComboBox() {
        cbVehicles.setConverter(new StringConverter<>() {
            @Override
            public String toString(Vehicle vehicle) {
                return vehicle != null ? vehicle.getPlate().toString() : "";
            }

            @Override
            public Vehicle fromString(String string) {
                return cbVehicles.getItems().stream()
                        .filter(vehicle -> vehicle.getPlate().toString().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        cbVehicles.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                loadServicesByVehicleCategory(newValue.getVehicleCategory());
            }
        });
    }

    private void configureClientComboBox() {
        cbClient.setConverter(new StringConverter<>() {
            @Override
            public String toString(Client client) {
                return client != null ? client.getName() : "";
            }

            @Override
            public Client fromString(String string) {
                return cbClient.getItems().stream()
                        .filter(client -> client.getName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        cbClient.valueProperty().addListener((observable, oldValue, newValue) -> {
            selectedClient = newValue;
            if (selectedClient != null) {
                loadVehiclesForClient(selectedClient.getId());
            } else {
                loadAllVehicles();
            }
        });
    }

    private void configureListViewServices() {
        listService.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Service service, boolean empty) {
                super.updateItem(service, empty);

                if (empty || service == null) {
                    setText(null);
                } else {
                    setText(service.getName());
                }
            }
        });

        listService.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }


    private void loadAllClients() {
        List<Client> clients = clientDaoJdbc.findAll();
        cbClient.getItems().addAll(clients);
    }

    private void loadVehiclesForClient(String clientId) {
        List<Vehicle> vehicles = clientVehiclesDaoJdbc.findVehiclesByClientId(clientId);
        ObservableList<Vehicle> vehicleList = FXCollections.observableArrayList(vehicles);
        cbVehicles.setItems(vehicleList);
    }

    private void loadAllVehicles() {
        List<Vehicle> vehicles = vehicleDaoJdbc.findAll();
        ObservableList<Vehicle> vehicleList = FXCollections.observableArrayList(vehicles);
        cbVehicles.setItems(vehicleList);
    }

    private void loadServicesByVehicleCategory(VehicleCategory category) {
        List<Service> services = serviceDaoJdbc.findServicesByVehicleCategory(category);
        ObservableList<Service> serviceList = FXCollections.observableArrayList(services);
        listService.setItems(serviceList);
    }

    private void configureViewMode() {
        btnCancel.setLayoutX(btnConfirm.getLayoutX());
        btnCancel.setLayoutY(btnConfirm.getLayoutY());
        btnCancel.setText("Fechar");

        btnConfirm.setVisible(false);

        boxPayment.setDisable(true);
        pickerDate.setDisable(true);
        txtDiscount.setDisable(true);
        boxStatus.setDisable(true);
    }
}
