package com.airportinfo;

import com.airportinfo.csv.CSVReader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/** *
 * Controller handling airports
 */
public class AirportController {
    private ArrayList<Airport> airports = new ArrayList<>();

    /** *
     * Initialize airports with loadFromDB()
     * @throws SQLException - when sql incorrect
     * @throws ClassNotFoundException - when mysql not found
     */
    public AirportController() throws SQLException, ClassNotFoundException {
        loadFromDB();
    }

    public void loadFromDB() throws SQLException, ClassNotFoundException {
        try (DBManager dbManager = new DBManager()) {
            airports = dbManager.selectAirport();
        }
    }

    /** *
     * Insert all airports that AirportController has
     * @throws SQLException - when sql incorrect
     * @throws ClassNotFoundException - when mysql not found
     */
    public void updateDB() throws SQLException, ClassNotFoundException {
        try (DBManager dbManager = new DBManager()) {
            for (Airport airport : airports) {
                dbManager.insertAirport(airport);
            }
        }
    }

    /** *
     * Load from a CSV file. All airports will be removed.
     * @param path - CSVFile path
     * @throws IOException -
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

    public ArrayList<Airport> search(Filter filter) {
        ArrayList<Airport> result = new ArrayList<>();
        for (Airport airport : airports) {
            if (filter.filter(airport))
                result.add(airport);
        }

        return result;
    }

    @FunctionalInterface
    public interface Filter {
        /** *
         * Filtering airport with param airport
         * @param airport : airport to filter
         * @return true if you want to contain airport
         */
        boolean filter(Airport airport);
    }
}
