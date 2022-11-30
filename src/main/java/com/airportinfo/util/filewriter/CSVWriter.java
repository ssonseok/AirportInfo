package com.airportinfo.util.filewriter;

import com.airportinfo.RawAirport;

import java.io.IOException;

/**
 * A writer class to write csv files.
 *
 * @author JumoKookbob
 */
public class CSVWriter extends AirportWriter {

    public CSVWriter(String fPath) throws IOException {
        super(fPath);
    }

    @Override
    public void write(RawAirport airport) throws IOException {
        try {
            int count = 0;
            for (String content : airport.toArray()) {
                if (count < airport.toArray().length - 1)
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
     * A method to only use to write header to file.
     *
     * @throws IOException If an I/O error occurs
     */
    protected void writeHeader() throws IOException {
        int count = 0;
        for (String head : getHeader()) {
            if (count < getHeader().length - 1)
                writer.write(head + ",");
            else
                writer.write(head);
            count++;
        }
        writer.newLine();
    }

    @Override
    public void write(RawAirport[] airports) throws IOException {
        writeHeader();
        for (RawAirport airport : airports) {
            write(airport);
        }
    }
}
