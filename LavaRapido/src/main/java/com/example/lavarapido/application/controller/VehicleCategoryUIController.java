package com.example.lavarapido.application.controller;

import com.example.lavarapido.application.repository.daoimplements.VehicleCategoryDaoJdbc;
import com.example.lavarapido.application.view.WindowLoader;
import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

import static com.example.lavarapido.application.main.Main.*;

public class VehicleCategoryUIController {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnConfirm;

    @FXML
    private TextField txtCategoryName;

    private VehicleCategory vehicleCategory;

    public void saveOrUpdate(ActionEvent actionEvent) throws IOException {
        getEntityFromView();

        VehicleCategoryDaoJdbc vehicleCategoryDaoJdbc = new VehicleCategoryDaoJdbc();

        if (vehicleCategory.getId() == null) {
            try {
                insertVehicleCategoryUseCase.insert(vehicleCategory);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                updateVehicleCategoryUseCase.update(vehicleCategory);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        WindowLoader.setRoot("VehicleCategoryManegementUI");
    }

    private boolean categoryExists(String name, VehicleCategoryDaoJdbc vehicleCategoryDaoJdbc) {
        return vehicleCategoryDaoJdbc.findOneByName(name).isPresent();
    }

    private void getEntityFromView() {
        if (vehicleCategory == null) {
            vehicleCategory = new VehicleCategory(null);
        } else {
            vehicleCategory.setName(txtCategoryName.getText());
        }
    }

    private void setEntityToView() {
        if (vehicleCategory != null) {
            txtCategoryName.setText(vehicleCategory.getName());
        }
    }

    public void setCategory(VehicleCategory vehicleCategory, UIMode mode) {
        if (vehicleCategory == null) throw new IllegalArgumentException("Category can not be null.");

        this.vehicleCategory = vehicleCategory;
        setEntityToView();

        //if (mode == UIMode.UPDATE) configureUpdateMode();
    }

//    private void configureUpdateMode() {
//        txtCategoryName.setDisable(true);
//    }


    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("VehicleCategoryManegementUI");
    }
}
