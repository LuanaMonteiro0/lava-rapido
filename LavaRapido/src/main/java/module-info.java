module com.example.lavarapido {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.lavarapido to javafx.fxml;
    exports com.example.lavarapido;
}