package com.airportinfo.controller;

import com.airportinfo.misc.Aspect;
import com.airportinfo.misc.Subject;
import com.airportinfo.model.Airport;
import com.airportinfo.model.EnglishAirportData;
import com.airportinfo.model.KoreanAirportData;
import com.airportinfo.model.RawAirport;
import com.airportinfo.util.CSVReader;
import com.airportinfo.util.DBManager;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controller handling airports.
 *
 * @author lalaalal
 */
public class AirportController extends Subject {
    public static final Aspect SELECTED_AIRPORT_CHANGE = new Aspect("selected_airport_change");
    public static final Aspect DB_INSERT = new Aspect("db_insert");
    private Airport selectedAirport;
    private ArrayList<Airport> airports = new ArrayList<>();

    public AirportController() {
        Airport.addTranslation(KoreanAirportData.class);
        Airport.addTranslation(EnglishAirportData.class);
    }

    /**
     * Load airports from Database.
     *
     * @throws SQLException           If a database access error occurs
     * @throws ClassNotFoundException If mysql driver not found
     */
    public void loadFromDB() throws SQLException, ClassNotFoundException, NoSuchMethodException {
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
                dbManager.insertAirport(airport.getRawData());
                notice(DB_INSERT);
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
            readCSVFile(reader);
        }
    }

    /**
     * Load from a CSV file. All airports will be removed.
     *
     * @param inputStream CSVFile input stream
     * @throws IOException Exception when open and read file
     */
    public void loadFromFile(InputStream inputStream) throws IOException {
        try (CSVReader reader = new CSVReader(inputStream)) {
            readCSVFile(reader);
        }
    }

    private void readCSVFile(CSVReader reader) throws IOException {
        airports.clear();
        String[] items = reader.read();
        while ((items = reader.read()) != null) {
            Airport airport = RawAirport.create(items);
            airports.add(airport);
        }
    }

    public Airport[] getAirports() {
        return airports.toArray(new Airport[0]);
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

    public void selectAirport(Airport airport) {
        selectedAirport = airport;
        notice(SELECTED_AIRPORT_CHANGE);
    }

    public Airport getSelectedAirport() {
        return selectedAirport;
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
