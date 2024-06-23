package com.example.lavarapido.application.controller;

import com.example.lavarapido.application.repository.daoimplements.VehicleCategoryDaoJdbc;
import com.example.lavarapido.application.repository.daoimplements.VehicleDaoJdbc;
import com.example.lavarapido.application.view.WindowLoader;
import com.example.lavarapido.domain.entities.vehicle.LicensePlate;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.List;

//import static br.edu.ifps.luana.application.main.Main.*;
import static com.example.lavarapido.application.main.Main.addVehicleClientUseCase;
import static com.example.lavarapido.application.main.Main.updateVehicleClientUseCase;

public class VehicleUIController {

    @FXML
    private TextField txtModel;
    @FXML
    private ComboBox<VehicleCategory> cbCategory;
    @FXML
    private TextField txtColor;
    @FXML
    private TextField txtPlate;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnConfirm;

    private Vehicle vehicle;
    private UIMode uiMode;

    @FXML
    public void initialize() {
        configureCategoryComboBox();
        loadAllCategories();
    }


    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("VehicleManegementUI");
    }

    public void saveOrUpdate(ActionEvent actionEvent) throws IOException {

        getEntityToView();
        if (uiMode == UIMode.UPDATE)
            updateVehicleClientUseCase.update(vehicle);
        else
            addVehicleClientUseCase.insert(vehicle);

        WindowLoader.setRoot("VehicleManegementUI");
    }

    public void getEntityToView() {
        if(vehicle == null) {
            vehicle = new Vehicle();
        }

        vehicle.setModel(txtModel.getText());
        vehicle.setColor(txtColor.getText());
        vehicle.setPlate(new LicensePlate(txtPlate.getText()));

        VehicleCategory selectedCategory = cbCategory.getSelectionModel().getSelectedItem();
        if (selectedCategory != null)
            vehicle.setVehicleCategory(selectedCategory);

    }

    public void setVehicle(Vehicle vehicle, UIMode mode) {
        if(vehicle == null)
            throw new IllegalArgumentException("Client can not be null.");

        this.vehicle = vehicle;
        setEntityToview();
        this.uiMode = mode;

        if(mode == UIMode.VIEW)
            configureViewMode();
        if (mode == UIMode.UPDATE)
            configureUpdateMode();

    }

    public void setEntityToview() {
        txtModel.setText(vehicle.getModel());
        selectCategoryInComboBox(vehicle.getVehicleCategory());
        txtColor.setText(vehicle.getColor());
        txtPlate.setText(vehicle.getPlate().toString());
    }

    private void selectCategoryInComboBox(VehicleCategory category) {
        if (category != null) {
            cbCategory.getItems().stream()
                    .filter(c -> c.getId().equals(category.getId()))
                    .findFirst()
                    .ifPresent(c -> cbCategory.getSelectionModel().select(c));
        }
    }

    private void configureUpdateMode() {
        txtPlate.setDisable(true);
    }

    private void configureViewMode() {
        btnCancel.setLayoutX(btnConfirm.getLayoutX());
        btnCancel.setLayoutY(btnConfirm.getLayoutY());
        btnCancel.setText("Fechar");

        btnConfirm.setVisible(false);

        txtModel.setDisable(true);
        cbCategory.setDisable(true);
        txtColor.setDisable(true);
        txtPlate.setDisable(true);
    }

    private void configureCategoryComboBox() {
        cbCategory.setConverter(new StringConverter<>() {
            @Override
            public String toString(VehicleCategory vehicleCategory) {
                return vehicleCategory != null ? vehicleCategory.getName() : "";
            }

            @Override
            public VehicleCategory fromString(String string) {
                return cbCategory.getItems().stream()
                        .filter(vehicleCategory -> vehicleCategory.getName().equals(string))
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
