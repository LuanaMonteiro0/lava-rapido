package com.example.lavarapido.application.controller;

import com.example.lavarapido.application.repository.daoimplements.ClientDaoJdbc;
import com.example.lavarapido.application.repository.daoimplements.VehicleDaoJdbc;
import com.example.lavarapido.application.view.WindowLoader;
import com.example.lavarapido.domain.entities.client.CPF;
import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.client.Telephone;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.List;

import static com.example.lavarapido.application.main.Main.createClientUseCase;
import static com.example.lavarapido.application.main.Main.updateClientUseCase;

public class ClientUIController {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtCPF;
    @FXML
    private ComboBox<Vehicle> cbVehicles;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnConfirm;

    private Client client;

    @FXML
    public void initialize() {
        configureVehicleComboBox();
        loadAllVehicles();
    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("ClientManegementUI");
    }

    public void saveOrUpdate(ActionEvent actionEvent) throws IOException {
        getEntityFromView();

        ClientDaoJdbc clientDaoJdbc = new ClientDaoJdbc();

        if (clientExists(client.getCpf(), clientDaoJdbc)) {
            try {
                updateClientUseCase.update(client);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                createClientUseCase.insert(client);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        WindowLoader.setRoot("ClientManegementUI");
    }

    private boolean clientExists(CPF cpf, ClientDaoJdbc clientDaoJdbc) {
        return clientDaoJdbc.findOneByCPF(cpf).isPresent();
    }

    private void getEntityFromView() {
        if (client == null) {
            client = new Client(txtName.getText(), new Telephone(txtPhone.getText()), new CPF(txtCPF.getText()));
        } else {
            client.setName(txtName.getText());
            client.setPhone(new Telephone(txtPhone.getText()));
        }
        Vehicle selectedVehicle = cbVehicles.getSelectionModel().getSelectedItem();
        if (selectedVehicle != null && !client.getVehicles().contains(selectedVehicle)) {
            client.addVehicle(selectedVehicle);
        }
    }

    private void setEntityToView() {
        if (client != null) {
            txtName.setText(client.getName());
            txtPhone.setText(client.getPhone());
            txtCPF.setText(client.getCpfString());
            cbVehicles.getSelectionModel().clearSelection();
//            if (!client.getVehicles().isEmpty()) {
//                cbVehicles.setValue(client.getVehicles().getFirst());
//            }
            /*nao faz sentido isso aqui. ou ele tem carro ou nao tem, não podemos apenas selecionar um automaticamente para ele.
            Ex: e se o veículo não for dele??*/
        }
    }

    public void setClient(Client client, UIMode mode) {
        if (client == null) throw new IllegalArgumentException("Client can not be null.");

        this.client = client;
        setEntityToView();

        if (mode == UIMode.VIEW) configureViewMode();
        if (mode == UIMode.UPDATE) txtCPF.setDisable(true);
    }

    private void configureViewMode() {
        btnCancel.setLayoutX(btnConfirm.getLayoutX());
        btnCancel.setLayoutY(btnConfirm.getLayoutY());
        btnCancel.setText("Fechar");

        btnConfirm.setVisible(false);

        txtName.setDisable(true);
        txtPhone.setDisable(true);
        txtCPF.setDisable(true);
        cbVehicles.setDisable(true);
    }

    /*private void loadUnassociatedVehicles() {
        VehicleDaoJdbc vehicleDaoJdbc = new VehicleDaoJdbc();
        List<Vehicle> vehicles = vehicleDaoJdbc.findAllUnassociated();
        cbVehicles.getItems().addAll(vehicles);
    }*/

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
}
