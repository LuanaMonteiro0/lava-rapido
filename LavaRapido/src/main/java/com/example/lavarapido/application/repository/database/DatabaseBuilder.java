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

    public static void main(String[] args) {
        DatabaseBuilder databaseBuilder = new DatabaseBuilder();
        databaseBuilder.buildPiupiu();
    }

    private void buildPiupiu(){
        try{
            PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sqlPiupiu);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
