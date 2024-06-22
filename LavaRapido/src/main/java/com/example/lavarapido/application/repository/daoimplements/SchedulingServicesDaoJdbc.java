package com.example.lavarapido.application.repository.daoimplements;

import com.example.lavarapido.application.repository.database.ConnectionFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SchedulingServicesDaoJdbc {

    public void create(String schedulingId, String serviceId) {
        try {
            String targetSchedulingServices = """
                INSERT INTO SchedulingsServices (SchedulingId, ServiceId) VALUES(?, ?);
                """;
            PreparedStatement targetSchedulingServicesStatement = ConnectionFactory.createPreparedStatement(targetSchedulingServices);
            targetSchedulingServicesStatement.setString(1, schedulingId);
            targetSchedulingServicesStatement.setString(2, serviceId);

            targetSchedulingServicesStatement.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }



    public List<String> findAll(String schedulingId) {

        List<String> myServicesId = new ArrayList<>();

        try {
            String targetSchedulingServices = """
                SELECT ServiceId FROM SchedulingsServices WHERE SchedulingId = ?
                """;
            PreparedStatement targetSchedulingServicesStatement = ConnectionFactory.createPreparedStatement(targetSchedulingServices);
            targetSchedulingServicesStatement.setString(1, schedulingId);

            ResultSet res = targetSchedulingServicesStatement.executeQuery();

            while(res.next()){
                myServicesId.add(res.getString("ServiceId"));
            }
            return myServicesId;

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return myServicesId;

    }

    public boolean deleteByKey(String schedulingId) {
        try {
            String targetSchedulingServices = """
                    DELETE FROM SchedulingsServices WHERE SchedulingId = ?
                    """;
            PreparedStatement targetSchedulingServicesStatement = ConnectionFactory.createPreparedStatement(targetSchedulingServices);
            targetSchedulingServicesStatement.setString(1, schedulingId);

            targetSchedulingServicesStatement.executeUpdate();

            return true;

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
