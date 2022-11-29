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
    /**
     * A method to only use to write header to file
     * @throws IOException
     */
    protected void writeHeader() throws IOException {
        int count = 0;
        for(String head : getHeader()) {
            if(count < getHeader().length - 1)
                writer.write(head + ",");
            else
                writer.write(head);
            count++;
        }
        writer.newLine();
    }

    @Override
    public void write(Airport[] airports) throws IOException {
        writeHeader();
        for(Airport airport : airports) {
            write(airport);
        }
    }
}
