package com.airportinfo.util.filewriter;

import com.airportinfo.model.Airport;
import com.airportinfo.model.RawAirport;

import java.io.IOException;

/**
 * A writer class to write csv files.
 *
 * @author JumoKookbob
 */
class CSVWriter extends AirportWriter {

    public CSVWriter(String fPath) throws IOException {
        super(fPath);
    }

    private void write(String[] data) throws IOException {
        for (int i = 0; i < data.length; i++) {
            String content = data[i];
            if (content.contains(","))
                content = "\"" + content + "\"";

            writer.write(content);
            if (i < data.length - 1)
                writer.write(",");
        }
        writer.flush();
        writer.newLine();
    }

    @Override
    public void write(Airport airport) throws IOException {
        write(airport.toArray());
    }

    @Override
    public void writeRawAirport(Airport airport) throws IOException {
        write(airport.getRawData().toArray());
    }

    /**
     * A method to only use to write header to file.
     *
     * @throws IOException If an I/O error occurs
     */
    public void writeHeader(String[] header) throws IOException {
        int count = 0;
        for (String head : header) {
            writer.write(head);
            if (count < header.length - 1)
                writer.write(",");

            count++;
        }
        writer.newLine();
    }

    @Override
    public void write(Airport[] airports) throws IOException {
        writeHeader(Airport.ATTRIBUTE_NAMES);
        for (Airport airport : airports) {
            write(airport);
        }
    }

    @Override
    public void writeRawAirports(Airport[] airports) throws IOException {
        writeHeader(RawAirport.ATTRIBUTE_NAMES);
        for (Airport airport : airports) {
            write(airport.getRawData().toArray());
        }
    }
}
