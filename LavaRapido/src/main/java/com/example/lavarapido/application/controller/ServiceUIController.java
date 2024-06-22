package com.example.lavarapido.application.controller;

import com.example.lavarapido.application.repository.daoimplements.ServiceDaoJdbc;
import com.example.lavarapido.application.repository.daoimplements.VehicleCategoryDaoJdbc;
import com.example.lavarapido.application.view.WindowLoader;
import com.example.lavarapido.domain.entities.general.Status;
import com.example.lavarapido.domain.entities.service.Service;
import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.example.lavarapido.application.main.Main.*;

public class ServiceUIController {

    @FXML
    private TextField txtName;
    @FXML
    private ComboBox<VehicleCategory> cbCategory;
    @FXML
    private TextField txtPrice;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;

    private Service service;

    @FXML
    public void initialize() {
        configureCategoryComboBox();
        loadAllCategories();
    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("ServiceManagementUI");
    }

    public void saveOrUpdate(ActionEvent actionEvent) throws IOException {
        getEntityFromView();

        ServiceDaoJdbc serviceDaoJdbc = new ServiceDaoJdbc();

        if (serviceExists(service.getName(), serviceDaoJdbc)) {
            try {
                updateServiceUseCase.update(service);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                createServiceUseCase.insert(service);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        WindowLoader.setRoot("ServiceManagementUI");
    }

    private boolean serviceExists(String name, ServiceDaoJdbc serviceDaoJdbc) {
        return serviceDaoJdbc.findOneByName(name).isPresent();
    }

    private void getEntityFromView() {
        String name = txtName.getText();
        VehicleCategory selectedCategory = cbCategory.getSelectionModel().getSelectedItem();
        Double price = Double.parseDouble(txtPrice.getText());

        if (service == null) {
            service = new Service(name, Status.ACTIVE);
            service.setPrice(selectedCategory, price);
        } else {
            service.setName(name);
            service.setPrice(selectedCategory, price);
        }
    }

    private void setEntityToView() {
        if (service != null) {
            txtName.setText(service.getName());

            cbCategory.getSelectionModel().clearSelection();

            if (!service.getPrice().isEmpty()) {
                cbCategory.setValue(service.getPrice().keySet().iterator().next());
            }

            VehicleCategory selectedCategory = cbCategory.getSelectionModel().getSelectedItem();
            if (selectedCategory != null) {
                Double price = service.getPriceForCategory(selectedCategory);
                if (price != null) {
                    txtPrice.setText(price.toString());
                } else {
                    txtPrice.setText("");
                }
            } else {
                txtPrice.setText("");
            }
        }
    }

    public void setService(Service service, UIMode mode) {
        if (service == null) throw new IllegalArgumentException("Service can not be null.");

        this.service = service;
        setEntityToView();

        if (mode == UIMode.VIEW) configureViewMode();
    }

    private void configureViewMode() {
        btnCancel.setLayoutX(btnSave.getLayoutX());
        btnCancel.setLayoutY(btnSave.getLayoutY());
        btnCancel.setText("Fechar");

        btnSave.setVisible(false);

        txtName.setDisable(true);
        cbCategory.setDisable(true);
        txtPrice.setDisable(true);
    }

    private void configureCategoryComboBox() {
        cbCategory.setConverter(new StringConverter<>() {
            @Override
            public String toString(VehicleCategory category) {
                return category != null ? category.getName() : "";
            }

            @Override
            public VehicleCategory fromString(String string) {
                return cbCategory.getItems().stream()
                        .filter(category -> category.getName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }

    private void loadAllCategories() {
        VehicleCategoryDaoJdbc vehicleCategoryDaoJdbc = new VehicleCategoryDaoJdbc();
        List<VehicleCategory> categories = vehicleCategoryDaoJdbc.findAll();
        cbCategory.getItems().clear();
        cbCategory.getItems().addAll(categories);
    }
}
