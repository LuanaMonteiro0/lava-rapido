package com.example.lavarapido.application.controller;

import com.example.lavarapido.application.view.WindowLoader;
import com.example.lavarapido.domain.entities.client.CPF;
import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.client.Telephone;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

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
    private Button btnCancel;
    @FXML
    private Button btnConfirm;

    private Client client;

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("ClientManegementUI");
    }

    public void saveOrUpdate(ActionEvent actionEvent) throws IOException {
        getEntityToView();

        if (client.getId() == null) {
            try {
                createClientUseCase.insert(client);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            updateClientUseCase.update(client);
        }

        WindowLoader.setRoot("ClientManegementUI");
    }

    public void getEntityToView() {
        if(client == null) {
            client = new Client(txtName.getText(), new Telephone(txtPhone.getText()), new CPF(txtCPF.getText()));
        }
        else {
            client.setName(txtName.getText());
            client.setPhone(new Telephone(txtPhone.getText()));
        }
    }

    public void setEntityToview() {
        txtName.setText(client.getName());
        txtPhone.setText(client.getPhone());
        txtCPF.setText(client.getCpfString());
    }

    public void setClient(Client client, UIMode mode) {
        if(client == null)
            throw new IllegalArgumentException("Client can not be null.");

        this.client = client;
        setEntityToview();

        if(mode == UIMode.VIEW)
            configureViewMode();
    }

    private void configureViewMode() {
        btnCancel.setLayoutX(btnConfirm.getLayoutX());
        btnCancel.setLayoutY(btnConfirm.getLayoutY());
        btnCancel.setText("Fechar");

        btnConfirm.setVisible(false);

        txtName.setDisable(true);
        txtPhone.setDisable(true);
        txtCPF.setDisable(true);
    }
}
