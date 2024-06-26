package com.example.lavarapido.usecases.utils;

import javafx.scene.control.Alert;

public class ShowAlert {
    public ShowAlert(){}

    public static void showErrorAlert(String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Erro");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    public static void showInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info Dialog");
        alert.setHeaderText("Info");
        alert.setContentText(message);
        alert.showAndWait();
    }

}
