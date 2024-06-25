package com.example.lavarapido.application.controller;

import com.example.lavarapido.application.repository.daoimplements.ClientDaoJdbc;
import com.example.lavarapido.application.repository.daoimplements.SchedulingDaoJdbc;
import com.example.lavarapido.application.repository.daoimplements.SchedulingServicesDaoJdbc;
import com.example.lavarapido.application.repository.daoimplements.ServiceDaoJdbc;
import com.example.lavarapido.application.view.SchedulingView;
import com.example.lavarapido.application.view.WindowLoader;
import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.scheduling.Scheduling;
import com.example.lavarapido.domain.entities.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainUIController implements Initializable {

    @FXML
    private TableColumn<SchedulingView, Client> cClient;

    @FXML
    private TableColumn<SchedulingView, String> cDateHour;

    @FXML
    private TableColumn<SchedulingView, Double> cDiscount;

    @FXML
    private TableColumn<SchedulingView, String> cFormOfPayment;

    @FXML
    private TableColumn<SchedulingView, String> cService;

    @FXML
    private TableColumn<SchedulingView, String> cStatus;

    @FXML
    private TableColumn<SchedulingView, Double> cValue;

    @FXML
    private TableColumn<SchedulingView, String> cVehicle;

    @FXML
    private TableView<SchedulingView> tableView;

    private ObservableList<SchedulingView> tableData;
    private SchedulingDaoJdbc schedulingDaoJdbc = new SchedulingDaoJdbc();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureTableColumns();
        bindTableViewToItemsList();
        loadDataAndShow();
    }

    private void configureTableColumns() {
        cClient.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        cVehicle.setCellValueFactory(new PropertyValueFactory<>("vehiclePlate"));
        cFormOfPayment.setCellValueFactory(new PropertyValueFactory<>("formOfPayment"));
        cDateHour.setCellValueFactory(new PropertyValueFactory<>("dateHour"));
        cValue.setCellValueFactory(new PropertyValueFactory<>("totalValue"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        cDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        cService.setCellValueFactory(new PropertyValueFactory<>("serviceNamesAsString"));
    }

    private void bindTableViewToItemsList() {
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);
    }

    private void loadDataAndShow() {
        SchedulingServicesDaoJdbc schedulingServicesDaoJdbc = new SchedulingServicesDaoJdbc();
        ServiceDaoJdbc serviceDaoJdbc = new ServiceDaoJdbc(); // Suponho que você tenha um DAO para serviços

        List<Scheduling> schedulings = schedulingDaoJdbc.findAll();

        tableData.clear();

        for (Scheduling scheduling : schedulings) {
            List<String> serviceIds = schedulingServicesDaoJdbc.findAll(scheduling.getId());
            List<String> serviceNames = new ArrayList<>();

            for (String serviceId : serviceIds) {
                Optional<Service> serviceOptional = serviceDaoJdbc.findOne(serviceId);
                serviceOptional.ifPresent(service -> serviceNames.add(service.getName()));
            }

            SchedulingView data = new SchedulingView(scheduling, serviceNames);
            tableData.add(data);
        }

        System.out.println("Total de agendamentos carregados: " + schedulings.size());
    }

    public void clientManegement(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("ClientManegementUI");
    }

    public void vehicleManegement(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("VehicleManegementUI");
    }

    public void vehicleCategoryManegement(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("VehicleCategoryManegementUI");
    }

    public void serviceManegement(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("ServiceManegementUI");
    }

    public void schedulingManegement(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("SchedulingUI");
    }
}
