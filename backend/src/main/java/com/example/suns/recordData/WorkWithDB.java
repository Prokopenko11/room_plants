package com.example.suns.recordData;

import java.sql.*;
import java.time.LocalDate;
import java.util.Locale;

public class WorkWithDB {

    private static final String DB_URL = "jdbc:postgresql://localhost/room_plants";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "123";
    private static Connection connection = null;

    public WorkWithDB() {
    }

    private static void doConnection() {
        // Устанавливаем соединение с базой данных PostgreSQL
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void closeConnection() {
        // Закрываем соединение с базой данных PostgreSQL
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateCurrentTemperature(int hour, int min, int year,
                                                int month, int day, String data)
            throws SQLException {
        doConnection();

        double temperature = Double.parseDouble(data);
        String updateSql = "UPDATE current_temperature " +
                "SET timestamp_ = to_timestamp(?, 'HH24:MI DD.MM.YYYY'), " +
                "   temperature = ? " +
                "WHERE timestamp_ = (SELECT min(timestamp_) FROM current_temperature);";
        PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
        preparedStatement.setString(1, hour + ":" + min + " "
                + day + "." + month + "." + year);
        preparedStatement.setDouble(2, temperature);
        preparedStatement.executeUpdate();

        closeConnection();
    }

    public static void insertTodayData(int hour, String data) throws SQLException {
        doConnection();

        double temperature = Double.parseDouble(data);

        String updateSql = "UPDATE today_temperature " +
                "SET temperature = ? " +
                "WHERE id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
        preparedStatement.setDouble(1, temperature);
        preparedStatement.setInt(2, hour);
        preparedStatement.executeUpdate();

        closeConnection();
    }

    public static void deleteTodayData() throws SQLException {
        doConnection();

        String insertSql = "DELETE FROM today_temperature;\n" +
                "INSERT INTO today_temperature(id, temperature_site)" +
                "VALUES (0, \'##.##\'), (1, \'##.##\'), (2, \'##.##\'), (3, \'##.##\'), (4, \'##.##\'), " +
                "(5, \'##.##\'), (6, \'##.##\'), (7, \'##.##\'), (8, \'##.##\'), (9, \'##.##\'), (10, \'##.##\'), " +
                "(11, \'##.##\'), (12, \'##.##\'), (13, \'##.##\'), (14, \'##.##\'), (15, \'##.##\'), " +
                "(16, \'##.##\'), (17, \'##.##\'), (18, \'##.##\'), (19, \'##.##\'), (20, \'##.##\'), " +
                "(21, \'##.##\'), (22, \'##.##\'), (23, \'##.##\')";
        PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
        preparedStatement.executeUpdate();

        closeConnection();
    }

    public static boolean isExistNote(int hour) throws SQLException {
        doConnection();

        String selectSql = "SELECT temperature " +
                "FROM today_temperature " +
                "WHERE id = ? AND temperature <> null;";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
        preparedStatement.setInt(1, hour);
        ResultSet result = preparedStatement.executeQuery();
        boolean res = result.next();
        closeConnection();
        return res;
    }

    public static void insertLastWeekData(int year, int month, int day,
                                          int dayOfWeek)
            throws SQLException {
        doConnection();

        String deleteSql = "DELETE FROM last_week_temperature " +
                "WHERE day_of_week = ?;";
        PreparedStatement preparedStatement1 = connection.prepareStatement(deleteSql);
        preparedStatement1.setString(1, String.valueOf(dayOfWeek));
        preparedStatement1.executeUpdate();

        String insertSql = "INSERT INTO last_week_temperature(day_of_week, date_, av_temperature) " +
                "VALUES (?, to_date(?, 'YYYY-MM-DD'), (SELECT round(avg(temperature)::numeric, 2) FROM today_temperature));";
        PreparedStatement preparedStatement2 = connection.prepareStatement(insertSql);
        preparedStatement2.setString(1, String.valueOf(dayOfWeek));
        preparedStatement2.setString(2, year + "-" + month + "-" + day);
        preparedStatement2.executeUpdate();

        closeConnection();
    }

    public static boolean isExistWeekNote(int year, int month, int day) throws SQLException {
        doConnection();

        String selectSql = "SELECT av_temperature " +
                "FROM last_week_temperature " +
                "WHERE date_ = to_timestamp(?, 'DD.MM.YYYY');";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
        preparedStatement.setString(1, day + "." + month + "." + year);
        ResultSet result = preparedStatement.executeQuery();
        boolean res = result.next();

        closeConnection();
        return res;
    }
}
