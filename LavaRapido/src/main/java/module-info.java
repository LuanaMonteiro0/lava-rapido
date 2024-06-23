module com.example.lavarapido {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.lavarapido.application.view to javafx.fxml;
    opens com.example.lavarapido.application.controller to javafx.fxml;
    opens com.example.lavarapido.domain.entities.client to javafx.base;
    opens com.example.lavarapido.domain.entities.vehicle to javafx.base;
    opens com.example.lavarapido.domain.entities.service to javafx.base;
    opens com.example.lavarapido.domain.entities.scheduling to javafx.base;

    exports com.example.lavarapido.application.view;
    exports com.example.lavarapido.application.controller;

}