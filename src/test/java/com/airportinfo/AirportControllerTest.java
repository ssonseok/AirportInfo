package com.airportinfo;

import com.airportinfo.controller.AirportController;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

class AirportControllerTest {
    public static final InputStream AIRPORTS_CSV_INPUT_STREAM = AirportControllerTest.class.getResourceAsStream("airports.csv");

    @Test
    public void testLoadFromFile() throws IOException {
        AirportController airportController = new AirportController();
        InputStream is = getClass().getClassLoader().getResourceAsStream("airports.csv");
        airportController.loadFromFile(is);
        for (Airport airport : airportController.getAirports()) {
            System.out.println(airport);
        }
    }

    @Test
    @Disabled
    public void testUpdateDB() throws SQLException, ClassNotFoundException, IOException {
        AirportController airportController = new AirportController();
        airportController.loadFromFile(AIRPORTS_CSV_INPUT_STREAM);
        for (Airport airport : airportController.getAirports()) {
            System.out.println(airport);
        }
        airportController.updateDB();
    }

    @Test
    public void testLoadFromDB() throws SQLException, ClassNotFoundException {
        AirportController airportController = new AirportController();
        airportController.loadFromDB();
        for (Airport airport : airportController.getAirports()) {
            System.out.println(airport);
        }
    }
}
