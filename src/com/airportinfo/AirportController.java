package com.airportinfo;

import com.airportinfo.csv.CSVReader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class AirportController {
    private ArrayList<Airport> airports;

    public void loadFromDB() throws SQLException, ClassNotFoundException {
        try (DBManager dbManager = new DBManager()) {
            airports = dbManager.selectAirport();
        }
    }

    public void updateDB() throws SQLException, ClassNotFoundException {
        try (DBManager dbManager = new DBManager()) {
            for (Airport airport : airports) {
                dbManager.insertAirport(airport);
            }
        }
    }

    public void loadFromFile(String path) throws IOException {
        try (CSVReader reader = new CSVReader(path)) {
            airports = new ArrayList<>();
            String[] items = reader.read();
            while ((items = reader.read()) != null) {
                Airport airport = Airport.create(items);
                airports.add(airport);
            }
        }
    }

    public ArrayList<Airport> getAirports() {
        return airports;
    }
}
