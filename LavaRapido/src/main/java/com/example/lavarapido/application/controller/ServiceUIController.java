package com.example.lavarapido.application.controller;

import com.example.lavarapido.application.repository.daoimplements.ServicesPricesDaoJdbc;
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
    @FXML
    private Button btnReactive;

    private Service service;

    private UIMode uiMode;

    @FXML
    public void initialize() {
        configureCategoryComboBox();
        loadAllCategories();

        if (service == null) {
            btnReactive.setVisible(false);
        }
    }

    public void backToPreviousScene() throws IOException {
        WindowLoader.setRoot("ServiceManegementUI");
    }

    public void saveOrUpdate(ActionEvent actionEvent) throws IOException {
        getEntityFromView();

        if (uiMode == UIMode.UPDATE) {
            try {
                updateServiceUseCase.update(service);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                createServiceUseCase.insert(service);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        WindowLoader.setRoot("ServiceManegementUI");
    }


    private void getEntityFromView() {
        String name = txtName.getText();
        VehicleCategory selectedCategory = cbCategory.getSelectionModel().getSelectedItem();
        Double price = Double.parseDouble(txtPrice.getText());

        if (service == null) {
            service = new Service();
        }
        service.setName(name);
        service.setPrice(selectedCategory, price);
    }

    private void setEntityToView() {
        if (service != null) {
            txtName.setText(service.getName());
            btnReactive.setVisible(service.getStatus() == Status.INACTIVE);

            ServicesPricesDaoJdbc servicesPricesDaoJdbc = new ServicesPricesDaoJdbc();
            Map<VehicleCategory, Double> pricesMap = servicesPricesDaoJdbc.findPricesByServiceId(service.getId());

            cbCategory.getSelectionModel().clearSelection();

            for (Map.Entry<VehicleCategory, Double> entry : pricesMap.entrySet()) {
                VehicleCategory category = entry.getKey();
                Double price = entry.getValue();

                cbCategory.getSelectionModel().select(category);

                if (price != null) {
                    txtPrice.setText(price.toString());
                } else {
                    txtPrice.setText("");
                }
                break;
            }
        }
    }


    public void setService(Service service, UIMode mode) {
        if (service == null) throw new IllegalArgumentException("Service can not be null.");

        this.service = service;
        setEntityToView();
        this.uiMode = mode;

        if (mode == UIMode.VIEW) configureViewMode();
    }

    private void configureViewMode() {
        btnCancel.setLayoutX(btnSave.getLayoutX());
        btnCancel.setLayoutY(btnSave.getLayoutY());
        btnCancel.setText("Fechar");

        btnSave.setVisible(false);
        btnReactive.setVisible(false);

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

    public void reactiveService(ActionEvent actionEvent) {
        try {
            reactiveServiceUseCase.reactive(service);
            backToPreviousScene();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
