package com.airportinfo;

import java.io.IOException;
import java.sql.SQLException;

public class AirportControllerTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        AirportController airportController = new AirportController();
        airportController.loadFromFile("/Users/lalaalal/Downloads/airports.csv");
        for (Airport airport : airportController.getAirports()) {
            System.out.println(airport);
        }
        airportController.updateDB();
    }
}
