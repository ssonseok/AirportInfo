package com.airportinfo.util.fileWriter;

import com.airportinfo.Airport;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * A writer class to write csv files.
 *
 * @author JumoKookbob
 */
public class CSVWriter extends AirportWriter{

    public CSVWriter(String fPath) throws IOException {
        super(fPath);
    }

    @Override
    public void write(Airport airport) throws IOException {
        try{
            int count = 0;
            for(String content : airport.toArray()) {
                if(count < airport.toArray().length - 1)
                    writer.write(content + ",");
                else
                    writer.write(content);
                count++;
            }
        } finally {
            writer.flush();
            writer.newLine();
        }
    }

    @Override
    public void write(Airport[] airports) throws IOException {
        writeHeader();
        for(Airport airport : airports) {
            write(airport);
        }
    }
    /**
     * A method to make csv file
     */
    @Override
    public void download(Airport[] airports, String fName) throws IOException {
        File dataFile = new File(fPath + "/" + fName + ".csv");
        dataFile.createNewFile();

        write(airports);
    }
}
