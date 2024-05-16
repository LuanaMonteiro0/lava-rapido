module com.example.lavarapido {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lavarapido to javafx.fxml;
    exports com.example.lavarapido;
}