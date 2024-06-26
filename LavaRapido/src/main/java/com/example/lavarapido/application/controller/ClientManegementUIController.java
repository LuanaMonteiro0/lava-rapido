package com.example.lavarapido.application.controller;

import com.example.lavarapido.application.view.WindowLoader;
import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.application.repository.daoimplements.ClientDaoJdbc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


import java.io.IOException;
import java.util.List;

import static com.example.lavarapido.application.main.Main.deleteClientUseCase;


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
    private TableColumn<Client, String> cStatus;

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

    public void deleteClient() {
        Client selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem !=null) {
            deleteClientUseCase.delete(selectedItem);
            loadDataAndShow();
        }
    }

    public void editClient() throws IOException {
        showClientInMode(UIMode.UPDATE);
    }

    public void createClient() throws IOException {
        WindowLoader.setRoot("ClientUI");

    }

    public void detailClient() throws IOException {
        showClientInMode(UIMode.VIEW);
    }

    public void backToPreviousScene() throws IOException {
        WindowLoader.setRoot("MainUI");
    }

}
