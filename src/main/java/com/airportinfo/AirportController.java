package com.airportinfo;

import com.airportinfo.utils.CSVReader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controller handling airports.
 *
 * @author lalaalal
 */
public class AirportController {
    private ArrayList<Airport> airports = new ArrayList<>();

    /**
     * Initialize airports with loadFromDB().
     *
     * @throws SQLException           If a database access error occurs
     * @throws ClassNotFoundException If mysql driver not found
     */
    public AirportController() throws SQLException, ClassNotFoundException {
        loadFromDB();
    }

    /**
     * Load airports from Database.
     *
     * @throws SQLException           If a database access error occurs
     * @throws ClassNotFoundException If mysql driver not found
     */
    public void loadFromDB() throws SQLException, ClassNotFoundException {
        try (DBManager dbManager = new DBManager()) {
            airports = dbManager.selectAirport();
        }
    }

    /**
     * Clear and insert all airports that AirportController has.
     *
     * @throws SQLException           If a database access error occurs
     * @throws ClassNotFoundException If mysql driver not found
     */
    public void updateDB() throws SQLException, ClassNotFoundException {
        try (DBManager dbManager = new DBManager()) {
            dbManager.clear();
            for (Airport airport : airports) {
                dbManager.insertAirport(airport);
            }
        }
    }

    /**
     * Load from a CSV file. All airports will be removed.
     *
     * @param path CSVFile path
     * @throws IOException Exception when open and read file
     */
    public void loadFromFile(String path) throws IOException {
        try (CSVReader reader = new CSVReader(path)) {
            airports.clear();
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

    /**
     * Search airports with filter.
     *
     * @param filter To compare airport
     * @return Matching airports with filter
     */
    public ArrayList<Airport> search(Filter filter) {
        ArrayList<Airport> result = new ArrayList<>();
        for (Airport airport : airports) {
            if (filter.filter(airport))
                result.add(airport);
        }

        return result;
    }

    /**
     * Filtering while search airports.
     */
    @FunctionalInterface
    public interface Filter {
        /**
         * Filtering airport with param airport.
         *
         * @param airport Airport to filter
         * @return True if you want to contain airport
         */
        boolean filter(Airport airport);
    }
}
