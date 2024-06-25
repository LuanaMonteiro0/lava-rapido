package com.example.lavarapido.application.controller;

import com.example.lavarapido.application.repository.daoimplements.*;
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
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static com.example.lavarapido.application.main.Main.insertSchedulingUseCase;

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

    private final ClientVehiclesDaoJdbc clientVehiclesDaoJdbc = new ClientVehiclesDaoJdbc();
    private final ClientDaoJdbc clientDaoJdbc = new ClientDaoJdbc();
    private final VehicleDaoJdbc vehicleDaoJdbc = new VehicleDaoJdbc();
    private final ServiceDaoJdbc serviceDaoJdbc = new ServiceDaoJdbc();
    private final ServicesPricesDaoJdbc servicesPricesDaoJdbc = new ServicesPricesDaoJdbc();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureVehicleComboBox();
        configureClientComboBox();
        configureListViewServices();
        boxPayment.getItems().addAll(FormOfPayment.values());
        boxStatus.getItems().addAll(SchedulingStatus.values());

        loadAllClients();
    }

    public void backToPreviousScene() throws IOException {
        WindowLoader.setRoot("MainUI");
    }

    public void saveOrUpdate(ActionEvent actionEvent) throws IOException {
        getEntityToView();

        try {
            insertSchedulingUseCase.insert(scheduling);
            backToPreviousScene();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void getEntityToView() {
        Client clientSelected = cbClient.getSelectionModel().getSelectedItem();
        Vehicle vehicleSelected = cbVehicles.getSelectionModel().getSelectedItem();
        FormOfPayment formOfPaymentSelected = boxPayment.getSelectionModel().getSelectedItem();
        SchedulingStatus statusSelected = boxStatus.getSelectionModel().getSelectedItem();
        LocalDate selectedDate = pickerDate.getValue();
        String selectedHour = txtHour.getText().trim();

        double discount = 0.0;
        String discountText = txtDiscount.getText().trim();

        if (!discountText.isEmpty()) {
            try {
                discount = Double.parseDouble(discountText);
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        }

        if (scheduling == null) {
            scheduling = new Scheduling();
        }

        scheduling.setClient(clientSelected);
        scheduling.setVehicle(vehicleSelected);
        scheduling.setFormOfPayment(formOfPaymentSelected);
        scheduling.setSchedulingStatus(statusSelected);
        scheduling.setDate(selectedDate);
        scheduling.setHour(LocalTime.parse(selectedHour));
        scheduling.setDiscount(discount);

        List<Service> selectedServices = listService.getSelectionModel().getSelectedItems();

        scheduling.addAllServices(selectedServices);

        double totalValue = 0.0;

        for (Service service : selectedServices) {
            Map<VehicleCategory, Double> servicePrices = servicesPricesDaoJdbc.findPricesByServiceId(service.getId());

            for (Map.Entry<VehicleCategory, Double> entry : servicePrices.entrySet()) {
                double price = entry.getValue();

                totalValue += price;

            }
        }

        totalValue -= discount;

        scheduling.setTotalValue(totalValue);

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
        List<Client> clients = clientDaoJdbc.findAllActive();
        cbClient.getItems().addAll(clients);
    }

    private void loadVehiclesForClient(String clientId) {
        List<Vehicle> vehicles = clientVehiclesDaoJdbc.findActiveVehiclesByClientId(clientId);
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
}
