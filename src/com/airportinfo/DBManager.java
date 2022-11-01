package com.airportinfo;

import java.sql.*;
import java.util.ArrayList;

public class DBManager implements AutoCloseable {
    private final Connection connection;
    private static final String URL = "jdbc:mysql://aws.lalaalal.com:3306/airport?characterEncoding=UTF-8&serverTimezone=UTC";
    private static final String USER = "airport";
    private static final String PASSWORD = "rhrorcp0809";

    public DBManager() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void clear() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM airport");
        statement.execute();
    }

    public boolean insertAirport(Airport airport) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO airport(EnglishName, KoreanName, IATA, ICAO, Region, EnglishCountryName, KoreanCountryName, EnglishCityName) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        String[] items = airport.toArray();
        for (int i = 0; i < items.length; i++)
            statement.setString(i + 1, items[i]);

        return statement.execute();
    }

    public ArrayList<Airport> selectAirport() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT EnglishName, KoreanName, IATA, ICAO, Region, EnglishCountryName, KoreanCountryName, EnglishCityName From airport");

        ResultSet result = statement.executeQuery();
        ArrayList<Airport> airports = new ArrayList<>();
        while (result.next()) {
            Airport airport = new Airport();
            airport.englishName = result.getString("EnglishName");
            airport.koreanName = result.getString("KoreanName");
            airport.IATA = result.getString("IATA");
            airport.ICAO = result.getString("ICAO");
            airport.region = result.getString("Region");
            airport.englishCountryName = result.getString("EnglishCountryName");
            airport.koreanCountryName = result.getString("KoreanCountryName");
            airport.englishCityName = result.getString("EnglishCityName");

            airports.add(airport);
        }

        return airports;
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
