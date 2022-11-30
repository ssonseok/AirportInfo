package com.airportinfo.controller;

import com.airportinfo.Airport;
import com.airportinfo.KoreanAirportData;
import com.airportinfo.RawAirport;
import com.airportinfo.TranslatedAirportData;
import com.airportinfo.util.CSVReader;
import com.airportinfo.util.DBManager;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controller handling airports.
 *
 * @author lalaalal
 */
public class AirportController {
    private ArrayList<Airport> airports = new ArrayList<>();
    private final ArrayList<Class<? extends TranslatedAirportData>> supportedTranslations = new ArrayList<>();

    public AirportController() {
        supportedTranslations.add(KoreanAirportData.class);
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
            setTranslatedAirportData();
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
            }
        }
    }

    /**
     * Load from a CSV file. All airports will be removed.
     *
     * @param path CSVFile path
     * @throws IOException Exception when open and read file
     */
    public void loadFromFile(String path) throws IOException, NoSuchMethodException {
        try (CSVReader reader = new CSVReader(path)) {
            readCSVFile(reader);
            setTranslatedAirportData();
        }
    }

    /**
     * Load from a CSV file. All airports will be removed.
     *
     * @param inputStream CSVFile input stream
     * @throws IOException Exception when open and read file
     */
    public void loadFromFile(InputStream inputStream) throws IOException, NoSuchMethodException {
        try (CSVReader reader = new CSVReader(inputStream)) {
            readCSVFile(reader);
            setTranslatedAirportData();
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

    private void setTranslatedAirportData() throws NoSuchMethodException {
        try {
            for (Airport airport : airports) {
                for (Class<? extends TranslatedAirportData> supportedTranslation : supportedTranslations) {
                    airport.addTranslatedAirportData(supportedTranslation);
                }
            }
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            System.out.println("Something went wrong while creating TranslatedAirportData");
        }
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
