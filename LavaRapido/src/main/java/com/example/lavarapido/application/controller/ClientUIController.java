package com.example.lavarapido.application.controller;

import com.example.lavarapido.application.repository.daoimplements.ClientDaoJdbc;
import com.example.lavarapido.application.repository.daoimplements.ClientVehiclesDaoJdbc;
import com.example.lavarapido.application.repository.daoimplements.VehicleDaoJdbc;
import com.example.lavarapido.application.view.WindowLoader;
import com.example.lavarapido.domain.entities.client.CPF;
import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.client.Telephone;
import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.List;

import static com.example.lavarapido.application.main.Main.createClientUseCase;
import static com.example.lavarapido.application.main.Main.updateClientUseCase;
import static com.example.lavarapido.application.main.Main.reactiveClientUseCase;

public class ClientUIController {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnConfirm;

    @FXML
    private TableColumn<Vehicle, String> cClientVehicles;

    @FXML
    private Label lblAddVehicle;

    @FXML
    private ComboBox<Vehicle> cbVehicles;

    @FXML
    private TableView<Vehicle> tableView;

    @FXML
    private TextField txtCPF;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    @FXML
    private Button btnReactive;

    private Client client;

    private ObservableList<Vehicle> tableData;

    private UIMode uiMode;

    @FXML
    public void initialize() {
        configureVehicleComboBox();
        loadAllVehicles();
        configureTableColumns();
        bindTableViewToItemsList();
        if (client == null) {
            btnReactive.setVisible(false);
        }
    }

    private void bindTableViewToItemsList() {
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);
    }

    private void configureTableColumns() {
        cClientVehicles.setCellValueFactory(new PropertyValueFactory<>("plateString"));
    }

    private void loadTableDataAndShow() {
        ClientVehiclesDaoJdbc clientVehiclesDaoJdbc = new ClientVehiclesDaoJdbc();

        if (client != null) {
            List<Vehicle> vehiclesByClientId = clientVehiclesDaoJdbc.findVehiclesByClientId(client.getId());
            tableData.addAll(vehiclesByClientId);
        }
    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("ClientManegementUI");
    }

    public void saveOrUpdate(ActionEvent actionEvent) throws IOException {
        getEntityFromView();

        if (uiMode == UIMode.UPDATE) {
            try {
                updateClientUseCase.update(client);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                createClientUseCase.insert(client);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        WindowLoader.setRoot("ClientManegementUI");
    }


    private void getEntityFromView() {
        if (client == null) {
            client = new Client();
        }
        client.setName(txtName.getText());
        client.setPhone(new Telephone(txtPhone.getText()));
        client.setCpf(new CPF(txtCPF.getText()));

        Vehicle selectedVehicle = cbVehicles.getSelectionModel().getSelectedItem();
        if (selectedVehicle != null && !client.getVehicles().contains(selectedVehicle)) {
            client.addVehicle(selectedVehicle);
        }
    }

    private void setEntityToView() {
        if (client != null) {
            txtName.setText(client.getName());
            txtPhone.setText(client.getPhone());
            txtCPF.setText(client.getCpf().toString());
            cbVehicles.getSelectionModel().clearSelection();
            loadTableDataAndShow();

            btnReactive.setVisible(client.getStatus() == Status.INACTIVE);
        }
    }

    public void setClient(Client client, UIMode mode) {
        if (client == null) throw new IllegalArgumentException("Client can not be null.");

        this.client = client;
        setEntityToView();
        this.uiMode = mode;

        if (mode == UIMode.VIEW) configureViewMode();
        if (mode == UIMode.UPDATE) txtCPF.setDisable(true);
    }

    private void configureViewMode() {
        btnCancel.setLayoutX(btnConfirm.getLayoutX());
        btnCancel.setLayoutY(btnConfirm.getLayoutY());
        btnCancel.setText("Fechar");

        btnReactive.setVisible(false);
        btnConfirm.setVisible(false);
        cbVehicles.setVisible(false);
        lblAddVehicle.setVisible(false);

        txtName.setDisable(true);
        txtPhone.setDisable(true);
        txtCPF.setDisable(true);
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
    }

    private void loadAllVehicles() {
        VehicleDaoJdbc vehicleDaoJdbc = new VehicleDaoJdbc();
        List<Vehicle> vehicles = vehicleDaoJdbc.findAll();
        cbVehicles.getItems().clear();
        cbVehicles.getItems().addAll(vehicles);
    }

    public void reactiveClient(ActionEvent actionEvent) {
        if (client != null && client.getStatus() == Status.INACTIVE) {
            reactiveClientUseCase.reactive(client);
        }
    }
}
