package com.airportinfo.util;

import com.airportinfo.model.Airport;
import com.airportinfo.model.RawAirport;

import java.sql.*;
import java.util.ArrayList;

/**
 * DBManager connect with MySQL.
 *
 * @author lalaalal
 */
public class DBManager implements AutoCloseable {
    private static final String URL = "jdbc:mysql://aws.lalaalal.com:3306/airport?characterEncoding=UTF-8&serverTimezone=UTC";
    private static final String USER = "airport";
    private static final String PASSWORD = "rhrorcp0809";
    private final Connection connection;

    /**
     * Initialize connection with server.
     *
     * @throws ClassNotFoundException If mysql driver not found
     * @throws SQLException           If a database access error occurs or the url is null
     */
    public DBManager() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Delete all data in server.
     *
     * @throws SQLException If a database access error occurs
     */
    public void clear() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM airport");
        statement.execute();
    }

    /**
     * Insert airport to server.
     *
     * @param airport Airport to insert into server
     * @return True if insertion succeed
     * @throws SQLException If a database access error occurs
     */
    public boolean insertAirport(RawAirport airport) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO airport(EnglishName, KoreanName, IATA, ICAO, Region, EnglishCountryName, KoreanCountryName, EnglishCityName) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        String[] items = airport.toArray();
        for (int i = 0; i < items.length; i++)
            statement.setString(i + 1, items[i]);

        return statement.execute();
    }

    /**
     * Get all airports from server.
     *
     * @return ArrayList of Airports
     * @throws SQLException If a database access error occurs
     */
    public ArrayList<Airport> selectAirport() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT EnglishName, KoreanName, IATA, ICAO, Region, EnglishCountryName, KoreanCountryName, EnglishCityName From airport");

        ResultSet result = statement.executeQuery();
        ArrayList<Airport> airports = new ArrayList<>();
        while (result.next()) {
            RawAirport airport = new RawAirport();
            airport.englishName = result.getString("EnglishName");
            airport.koreanName = result.getString("KoreanName");
            airport.IATA = result.getString("IATA");
            airport.ICAO = result.getString("ICAO");
            airport.koreanRegion = result.getString("Region");
            airport.englishCountryName = result.getString("EnglishCountryName");
            airport.koreanCountryName = result.getString("KoreanCountryName");
            airport.englishCityName = result.getString("EnglishCityName");

            airports.add(new Airport(airport));
        }

        return airports;
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
