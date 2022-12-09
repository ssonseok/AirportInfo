package com.airportinfo.util.filewriter;

import com.airportinfo.model.Airport;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * An abstract writer class to write files.
 * Use AirportWriter.create(String) to create.
 *
 * @author JumoKookbob
 */
public abstract class AirportWriter implements AutoCloseable {
    protected BufferedWriter writer;
    protected String fPath;

    /**
     * Factory method of AirportWriter.
     *
     * @param path Path to save. Should be csv or json
     * @return AirportWriter
     * @throws IOException              If an I/O error occurs
     * @throws IllegalArgumentException If path extension not supported
     */
    public static AirportWriter create(String path) throws IOException {
        String extension = path.substring(path.lastIndexOf(".") + 1);
        return switch (extension) {
            case "csv" -> new CSVWriter(path);
            case "json" -> new JSONWriter(path);
            default -> throw new IllegalArgumentException("Unexpected value: " + extension);
        };
    }

    /**
     * A constructor method
     *
     * @param path File path
     * @throws IOException If an I/O error occurs
     */
    public AirportWriter(String path) throws IOException {
        fPath = path;
        writer = new BufferedWriter(new FileWriter(fPath));
    }

    /**
     * An abstract method to make file with Airport
     */
    public abstract void write(Airport airport) throws IOException;

    /**
     * An abstract method to make file with Airport array
     */
    public abstract void write(Airport[] airports) throws IOException;

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
