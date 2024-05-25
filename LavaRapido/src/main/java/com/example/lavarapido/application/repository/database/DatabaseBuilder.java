package com.example.lavarapido.application.repository.database;

import com.example.lavarapido.usecases.utils.ConnectionFactory;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseBuilder {

    private final String tableClient = """
            CREATE TABLE Client (
                id TEXT PRIMARY KEY NOT NULL,
                name TEXT,
                cpf TEXT,
                telefone TEXT,
                status TEXT
            )
            """;
    private final String tableService = """
            CREATE TABLE Client (
                id TEXT PRIMARY KEY NOT NULL,
                name TEXT
            )
            """;
    private final String tableVehicleCategory = """
            CREATE TABLE Client (
                id TEXT PRIMARY KEY NOT NULL,
                name TEXT
            )
            """;
    private final String sqlVehicle = """
            CREATE TABLE Vehicles (
                id LONG PRIMARY KEY NOT NULL,
                name VARCHAR
            )
            """;
    private final String sqlUpdateVehicle = """
            ALTER TABLE Vehicles ADD COLUMN plate VARCHAR
            """;

    public static void main(String[] args) {
        DatabaseBuilder databaseBuilder = new DatabaseBuilder();
        //databaseBuilder.buildTables();
        databaseBuilder.updateVehicle();
    }

    private void buildTables(){
        try{
            PreparedStatement statement = ConnectionFactory.createPreparedStatement(tableClient);
            PreparedStatement statement2 = ConnectionFactory.createPreparedStatement(sqlVehicle);
            statement.execute();
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
