package com.airportinfo.util.filewriter;

import com.airportinfo.Airport;
import com.airportinfo.TranslatedAirportData;

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

    @Override
    public void write(Airport airport) throws IOException {
        try {
            int count = 0;
            for (String content : airport.toArray()) {
                if (content.contains(","))
                    content = "\"" + content + "\"";
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
        for (String head : TranslatedAirportData.ATTRIBUTE_NAMES) {
            if (count < TranslatedAirportData.ATTRIBUTE_NAMES.length - 1)
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
        for (Airport airport : airports) {
            write(airport);
        }
    }
}
