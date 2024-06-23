package com.example.lavarapido.application.repository.daoimplements;

import com.example.lavarapido.application.repository.database.ConnectionFactory;
import com.example.lavarapido.domain.entities.client.Client;
import com.example.lavarapido.domain.entities.scheduling.Scheduling;
import com.example.lavarapido.domain.entities.scheduling.SchedulingStatus;
import com.example.lavarapido.domain.entities.service.Service;
import com.example.lavarapido.domain.entities.vehicle.Vehicle;
import com.example.lavarapido.usecases.Scheduling.SchedulingDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SchedulingDaoJdbc implements SchedulingDAO {

    protected Scheduling createSchedulingFromDbQuery(ResultSet resultSet) throws SQLException {

        Client client = new Client();
        ClientDaoJdbc clientDaoJdbc = new ClientDaoJdbc();
        client = clientDaoJdbc.findOne(resultSet.getString("client")).get();

        Vehicle vehicle = new Vehicle();
        VehicleDaoJdbc vehicleDaoJdbc = new VehicleDaoJdbc();
        vehicle = vehicleDaoJdbc.findOne(resultSet.getString("vehicle")).get();


        String dateDbResult = resultSet.getString("date");//"2024-06-03"
        String timeDbResult = resultSet.getString("time");

        String [] localDateArgumentsInStringFormat = dateDbResult.split("-");
        int year = Integer.parseInt(localDateArgumentsInStringFormat[0]);
        int month = Integer.parseInt(localDateArgumentsInStringFormat[1]);
        int day = Integer.parseInt(localDateArgumentsInStringFormat[2]);

        String [] localTimeArgumentsInStringFormat = timeDbResult.split(":");
        int hour = Integer.parseInt(localTimeArgumentsInStringFormat[0]);
        int minute = Integer.parseInt(localTimeArgumentsInStringFormat[1]);
        int second = Integer.parseInt(localTimeArgumentsInStringFormat[2]);

        Scheduling scheduling = new Scheduling(
                resultSet.getString("id"),
                resultSet.getDouble("totalValue"),
                resultSet.getDouble("discount"),
                LocalDate.of(year, month, day),
                LocalTime.of(hour, minute, second)
        );

        scheduling.setSchedulingStatus(SchedulingStatus.valueOf(resultSet.getString("schedulingStatus")));
        scheduling.setClient(client);
        scheduling.setVehicle(vehicle);

        return scheduling;
    }

    @Override
    public String create(Scheduling scheduling) {
        try {

            String targetScheduling = """
                INSERT INTO Schedulings(id, formOfPayment, date, totalValue, schedulingStatus, discount, time, client, vehicle) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;
            PreparedStatement targetSchedulingStatement = ConnectionFactory.createPreparedStatement(targetScheduling);
            targetSchedulingStatement.setString(1, scheduling.getId());
            targetSchedulingStatement.setString(2, String.valueOf(scheduling.getFormOfPayment()));
            targetSchedulingStatement.setString(3, String.valueOf(scheduling.getDate()));
            targetSchedulingStatement.setString(4, String.valueOf(scheduling.getTotalValue()));
            targetSchedulingStatement.setString(5, String.valueOf(scheduling.getSchedulingStatus()));
            targetSchedulingStatement.setString(6, String.valueOf(scheduling.getDiscount()));
            targetSchedulingStatement.setString(7, String.valueOf(scheduling.getHour()));
            targetSchedulingStatement.setString(8, scheduling.getClient().getId());
            targetSchedulingStatement.setString(9, scheduling.getVehicle().getId());

            targetSchedulingStatement.executeUpdate();

            scheduling.getServices().forEach(service -> {
                SchedulingServicesDaoJdbc ssDaoJdbc = new SchedulingServicesDaoJdbc();
                ssDaoJdbc.create(scheduling.getId(), service.getId());
            });

            return "Scheduling inserted";

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return "Scheduling not inserted";
    }

    // Não existe no documento do projeto o Caso de Uso Editar Agendamento,
    // nem nos Requisitos Funcionais consta tal funcionalidade
    @Override
    public boolean update(Scheduling type) {
        return false;
    }

    @Override
    public List<Scheduling> findAll() {

        List<Scheduling> mySchedulings = new ArrayList<>();

        try {
            String targetScheduling = """
                SELECT * FROM Schedulings
                """;
            PreparedStatement targetSchedulingStatement = ConnectionFactory.createPreparedStatement(targetScheduling);

            ResultSet res = targetSchedulingStatement.executeQuery();

            while(res.next()){
                mySchedulings.add(createSchedulingFromDbQuery(res));
            }
            return mySchedulings;

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return mySchedulings;
    }

    @Override
    public Optional<Scheduling> findOne(String schedulingId) {
        try {
            String targetScheduling = """
                SELECT * FROM Schedulings WHERE id = ?
                """;
            PreparedStatement targetSchedulingStatement = ConnectionFactory.createPreparedStatement(targetScheduling);
            targetSchedulingStatement.setString(1, schedulingId);

            ResultSet res = targetSchedulingStatement.executeQuery();
            if (res.next()) {
                Scheduling myScheduling = createSchedulingFromDbQuery(res);

                SchedulingServicesDaoJdbc ssDaoJdbc = new SchedulingServicesDaoJdbc();

                List<String> servicesId = ssDaoJdbc.findAll(myScheduling.getId());

                ServiceDaoJdbc serviceDaoJdbc = new ServiceDaoJdbc();

                List<Service> services = new ArrayList<>();

                for (String id : servicesId) {
                    services.add(serviceDaoJdbc.findOne(id).get());
                }

                myScheduling.addAllServices(services);

                return Optional.of(myScheduling);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Scheduling> findBetween(LocalDate initialDate, LocalDate finalDate) {

        List<Scheduling> mySchedulingsForTheSpecifiedPeriod = new ArrayList<>();

        //formato padrão do LocalDate: "yyyy-MM-dd"
        String initialDateOnStringFormat = initialDate.toString(); //"yyyy-MM-dd"
        String finalDateOnStringFormat = finalDate.toString(); //"yyyy-MM-dd"

        try {
            String targetScheduling = """
                SELECT * FROM Schedulings WHERE date(date) BETWEEN ? AND ?
                """;
            PreparedStatement targetSchedulingStatement = ConnectionFactory.createPreparedStatement(targetScheduling);
            targetSchedulingStatement.setString(1, initialDateOnStringFormat);
            targetSchedulingStatement.setString(2, finalDateOnStringFormat);

            ResultSet res = targetSchedulingStatement.executeQuery();

            while (res.next()) {
                Scheduling s = createSchedulingFromDbQuery(res);
                mySchedulingsForTheSpecifiedPeriod.add(s) ;
            }

            return mySchedulingsForTheSpecifiedPeriod;

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return mySchedulingsForTheSpecifiedPeriod;

    }

    @Override
    public List<Scheduling> findByServiceId(String serviceId) {

        List<Scheduling> mySchedulingsForTheSpecifiedServiceId = new ArrayList<>();

        try {
            //Mais complexo -> envolve subconsulta
            String targetScheduling = """
                SELECT * FROM Schedulings WHERE id IN (
                SELECT SchedulingId FROM SchedulingsServices WHERE ServiceId = ?)
                """;
            PreparedStatement targetSchedulingStatement = ConnectionFactory.createPreparedStatement(targetScheduling);
            targetSchedulingStatement.setString(1, serviceId);

            ResultSet res = targetSchedulingStatement.executeQuery();

            while (res.next()) {
                Scheduling s = createSchedulingFromDbQuery(res);
                mySchedulingsForTheSpecifiedServiceId.add(s) ;
            }

            return mySchedulingsForTheSpecifiedServiceId;

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return mySchedulingsForTheSpecifiedServiceId;

    }

    @Override
    public List<Scheduling> findByDate(LocalDate date) {

        List<Scheduling> mySchedulingsForTheSpecifiedDate = new ArrayList<>();

        String dateOnStringFormat = date.toString(); //"yyyy-MM-dd"

        try {
            String targetScheduling = """
                SELECT * FROM Schedulings WHERE date(date) = ?
                """;
            PreparedStatement targetSchedulingStatement = ConnectionFactory.createPreparedStatement(targetScheduling);
            targetSchedulingStatement.setString(1, dateOnStringFormat);

            ResultSet res = targetSchedulingStatement.executeQuery();

            while (res.next()) {
                Scheduling s = createSchedulingFromDbQuery(res);
                mySchedulingsForTheSpecifiedDate.add(s);
            }

            return mySchedulingsForTheSpecifiedDate;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mySchedulingsForTheSpecifiedDate;

    }

    @Override
    public boolean delete(Scheduling scheduling) {
        return deleteByKey(scheduling.getId());
    }

    @Override
    public boolean deleteByKey(String schedulingId) {
        try {
            String targetScheduling = """
                DELETE FROM Schedulings WHERE id = ?
                """;
            PreparedStatement targetSchedulingStatement = ConnectionFactory.createPreparedStatement(targetScheduling);
            targetSchedulingStatement.setString(1, schedulingId);

            targetSchedulingStatement.executeUpdate();

            SchedulingServicesDaoJdbc ssDaoJdbc = new SchedulingServicesDaoJdbc();

            ssDaoJdbc.deleteByKey(schedulingId);

            return true;

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}