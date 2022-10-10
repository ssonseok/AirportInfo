package com.airportinfo.csv;

import java.io.*;

public class CSVReader implements AutoCloseable {
    private final BufferedReader reader;

    public CSVReader(String path) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(path));
    }

    public String[] read() throws IOException {
        String line = reader.readLine();

        if (line == null)
            return null;
        return line.split(",");
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
