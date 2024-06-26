package com.example.lavarapido.application.repository.database;

//import java.sql.DriverManager;
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

    private final String tableVehicleCategories = """
            CREATE TABLE VehicleCategories (
                id TEXT PRIMARY KEY NOT NULL,
                name TEXT NOT NULL
            )
            """;

    private final String tableServices = """
            CREATE TABLE Services (
                id TEXT PRIMARY KEY NOT NULL,
                name TEXT,
                status TEXT
            )
            """;

    private final String tableServicesPrices = """
            CREATE TABLE ServicesPrices (
                idService TEXT NOT NULL,
                idVehicleCategory TEXT NOT NULL,
                price REAL,
                FOREIGN KEY(idVehicleCategory) REFERENCES VehicleCategories(id),
                FOREIGN KEY(idService) REFERENCES Services(id)
                PRIMARY KEY (idService, idVehicleCategory)
            )
            """;


    private final String tableVehicles = """
            CREATE TABLE Vehicles (
                id TEXT PRIMARY KEY NOT NULL,
                color TEXT,
                model TEXT,
                category TEXT,
                licensePlate TEXT,
                status TEXT,
                FOREIGN KEY(category) REFERENCES VehicleCategories(id)
            )
            """;

    private final String tableClientVehicles = """
            CREATE TABLE ClientVehicles (
                clientId TEXT NOT NULL,
                vehicleId TEXT NOT NULL,
                FOREIGN KEY(clientId) REFERENCES Clients(id),
                FOREIGN KEY(vehicleId) REFERENCES Vehicle(id),
                PRIMARY KEY (clientId, vehicleId)
            )
            """;

    private final String tableSchedulings = """
            CREATE TABLE Schedulings (
                id TEXT PRIMARY KEY NOT NULL,
                date TEXT,
                time TEXT,
                totalValue REAL,
                discount REAL,
                formOfPayment TEXT,
                client TEXT NOT NULL,
                vehicle TEXT NOT NULL,
                status TEXT,
                FOREIGN KEY(vehicle) REFERENCES Vehicles(id),
                FOREIGN KEY(client) REFERENCES Clients(id)
            )
            """;

    private final String tableSchedulingsServices = """
            CREATE TABLE SchedulingsServices (
                SchedulingId TEXT NOT NULL,
                ServiceId TEXT NOT NULL,
                FOREIGN KEY(SchedulingId) REFERENCES Schedulings(id),
                FOREIGN KEY(ServiceId) REFERENCES Services(id),
                PRIMARY KEY(SchedulingId, ServiceId)
            )
            """;

    public static void main(String[] args) {
        DatabaseBuilder databaseBuilder = new DatabaseBuilder();
        databaseBuilder.buildTables();
    }


    private void buildTables() {
        try{
            PreparedStatement tableClientsStatement = ConnectionFactory.createPreparedStatement(tableClients);
            tableClientsStatement.execute();

            PreparedStatement tableVehicleCategoriesStatement = ConnectionFactory.createPreparedStatement(tableVehicleCategories);
            tableVehicleCategoriesStatement.execute();

            PreparedStatement tableServicesStatement = ConnectionFactory.createPreparedStatement(tableServices);
            tableServicesStatement.execute();

            PreparedStatement tableServicesPriceStatement = ConnectionFactory.createPreparedStatement(tableServicesPrices);
            tableServicesPriceStatement.execute();

            PreparedStatement tableVehiclesStatement = ConnectionFactory.createPreparedStatement(tableVehicles);
            tableVehiclesStatement.execute();

            PreparedStatement tableClientVehiclesStatement = ConnectionFactory.createPreparedStatement(tableClientVehicles);
            tableClientVehiclesStatement.execute();

            PreparedStatement tableSchedulingsStatement = ConnectionFactory.createPreparedStatement(tableSchedulings);
            tableSchedulingsStatement.execute();

            PreparedStatement tableSchedulingsServicesStatement = ConnectionFactory.createPreparedStatement(tableSchedulingsServices);
            tableSchedulingsServicesStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
