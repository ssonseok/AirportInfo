package com.airportinfo.util.filewriter;

import com.airportinfo.controller.AirportController;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

public class AirportWriterTest {
    @Test
    public void testJSONWriter() throws IOException, SQLException, ClassNotFoundException, NoSuchMethodException {
        testWrite("test.json");
    }

    @Test
    public void testCSVWriter() throws SQLException, IOException, ClassNotFoundException, NoSuchMethodException {
        testWrite("test.csv");
    }

    public void testWrite(String path) throws SQLException, ClassNotFoundException, NoSuchMethodException, IOException {
        AirportController airportController = new AirportController();
        airportController.loadFromDB();
        try (AirportWriter writer = AirportWriter.create(path)) {
            writer.write(airportController.getAirports());
        }
    }
}