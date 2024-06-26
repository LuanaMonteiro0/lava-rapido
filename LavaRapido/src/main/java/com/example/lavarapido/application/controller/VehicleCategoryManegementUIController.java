package com.example.lavarapido.application.controller;

import com.example.lavarapido.application.repository.daoimplements.VehicleCategoryDaoJdbc;
import com.example.lavarapido.application.view.WindowLoader;
import com.example.lavarapido.domain.entities.vehicle.VehicleCategory;
import com.example.lavarapido.usecases.vehicleCategory.InsertVehicleCategoryUseCase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.example.lavarapido.application.main.Main.deleteVehicleCategoryUseCase;

public class VehicleCategoryManegementUIController {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> cCategoryName;

    @FXML
    private TableView<VehicleCategory> tableView;

    private ObservableList<VehicleCategory> tableData;


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
        cCategoryName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private void loadDataAndShow() {
        VehicleCategoryDaoJdbc vehicleCategoryDaoJdbc = new VehicleCategoryDaoJdbc();
        List<VehicleCategory> vehicleCategories = vehicleCategoryDaoJdbc.findAll();
        tableData.clear();
        tableData.addAll(vehicleCategories);

        System.out.println("Total de categorias carregadas: " + vehicleCategories.size());
    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("MainUI");
    }

    public void deleteCategory(ActionEvent actionEvent) {
        VehicleCategory selectedCategory = tableView.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            deleteVehicleCategoryUseCase.delete(selectedCategory);
        }
        loadDataAndShow();
    }

    public void updateCategory(ActionEvent actionEvent) {
        VehicleCategory selectedCategory = tableView.getSelectionModel().getSelectedItem();

        if (selectedCategory != null) {
            TextInputDialog dialog = new TextInputDialog(selectedCategory.getName());
            dialog.setTitle("Edit Category");
            dialog.setHeaderText("Enter the new name for the category:");
            dialog.setContentText("Name:");

            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                String newName = result.get();

                VehicleCategoryDaoJdbc vcDaoJdbc = new VehicleCategoryDaoJdbc();
                Optional<VehicleCategory> existingCategory = vcDaoJdbc.findOneByName(newName);

                if (existingCategory.isPresent()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("This name is already in use. Please enter a different name.");

                    alert.showAndWait();
                } else {
                    selectedCategory.setName(newName);
                    vcDaoJdbc.update(selectedCategory);

                    tableView.refresh();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("No category selected.");

            alert.showAndWait();
        }
    }

    public void createCategory(ActionEvent actionEvent) throws IOException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Category");
        dialog.setHeaderText("Enter the name for the new category:");
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            String categoryName = result.get();

            VehicleCategoryDaoJdbc vcDaoJdbc = new VehicleCategoryDaoJdbc();
            Optional<VehicleCategory> existingCategory = vcDaoJdbc.findOneByName(categoryName);

            if (existingCategory.isPresent()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText(null);
                alert.setContentText("This name is already in use. Please enter a different name.");

                alert.showAndWait();
            } else {
                VehicleCategory newCategory = new VehicleCategory(categoryName);
                InsertVehicleCategoryUseCase ivcUc = new InsertVehicleCategoryUseCase(vcDaoJdbc);
                ivcUc.insert(newCategory);

                loadDataAndShow();
            }
        }
    }

}
