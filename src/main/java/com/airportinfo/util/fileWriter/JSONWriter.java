package com.airportinfo.util.fileWriter;

import com.airportinfo.Airport;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * A writer class to write json files.
 *
 * @author JumoKookbob
 */
public class JSONWriter extends AirportWriter{

    /**
     * constructor method
     * @param fPath file path
     * @throws IOException
     */
    public JSONWriter(String fPath) throws IOException {
        super(fPath);
    }

    @Override
    public void write(Airport airport) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void write(Airport[] airports) throws IOException {
        throw new UnsupportedOperationException();
    }

}
