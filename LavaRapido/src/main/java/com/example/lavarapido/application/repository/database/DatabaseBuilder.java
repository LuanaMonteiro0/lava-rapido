package com.example.lavarapido.application.repository.database;

import com.example.lavarapido.usecases.utils.ConnectionFactory;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseBuilder {

    private final String tableClients = """
            CREATE TABLE Clients (
                id TEXT PRIMARY KEY NOT NULL,
                name TEXT,
                cpf TEXT,
                phone TEXT,
                status TEXT
            )
            """;

    private final String tableServices = """
            CREATE TABLE Services (
                id TEXT PRIMARY KEY NOT NULL,
                name TEXT,
                status TEXT,
                vehicleCategory TEXT,
                FOREIGN KEY(vehicleCategory) REFERENCES VehicleCategories(id)
            )
            """;

    private final String tableVehicleCategories = """
            CREATE TABLE VehicleCategories (
                id TEXT PRIMARY KEY NOT NULL,
                name TEXT NOT NULL
            )
            """;

    private final String tableVehicles = """
            CREATE TABLE Vehicles (
                id LONG PRIMARY KEY NOT NULL,
                name TEXT,
                category TEXT,
                licensePlate TEXT,
                status TEXT,
                FOREIGN KEY(category) REFERENCES VehicleCategories(name)
            )
            """;

    /*private final String tableVehiclesClients = """
            CREATE TABLE Vehicles-Clients (
                id TEXT PRIMARY KEY NOT NULL,
                name TEXT NOT NULL
            )
            """;*/

    private final String tableSchedulings = """
            CREATE TABLE Schedulings (
                id TEXT PRIMARY KEY NOT NULL,
                date TEXT,
                time TEXT,
                totalValue REAL,
                discount REAL,
                formOfPayment REAL,
                client TEXT NOT NULL,
                vehicle TEXT NOT NULL,
                status TEXT,
                FOREIGN KEY(vehicle) REFERENCES Vehicles(id)
                FOREIGN KEY(client) REFERENCES Clients(id)
            )
            """;


    /*private final String sqlUpdateVehicle = """
            ALTER TABLE Vehicles ADD COLUMN plate VARCHAR
            """;*/

    public static void main(String[] args) {
        DatabaseBuilder databaseBuilder = new DatabaseBuilder();
        //databaseBuilder.buildTables();
        databaseBuilder.updateVehicle();
    }

    private void buildTables(){
        try{
            PreparedStatement statement1 = ConnectionFactory.createPreparedStatement(tableClient);
            PreparedStatement statement2 = ConnectionFactory.createPreparedStatement(sqlVehicle);
            statement1.execute();
            statement2.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void updateVehicle(){
        try{
            PreparedStatement statement = ConnectionFactory.createPreparedStatement(sqlUpdateVehicle);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
