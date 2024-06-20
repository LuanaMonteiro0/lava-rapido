package com.example.lavarapido.application.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
public class WindowLoader extends Application {

    private static Scene scene;
    private static Object controller;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("MainUI"), 817, 510);
        stage.setTitle("Lava-rápido");
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        URL fxmlLocation = WindowLoader.class.getResource("/com/example/lavarapido/application/view/" + fxml + ".fxml");
        if (fxmlLocation == null) {
            throw new IOException("Cannot load FXML file: " + fxml);
        }
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        Parent parent = fxmlLoader.load();
        controller = fxmlLoader.getController();
        return parent;
    }

    public static void main(String[] args) {
        launch();
    }

    public static Object getController() {
        return controller;
    }
}
