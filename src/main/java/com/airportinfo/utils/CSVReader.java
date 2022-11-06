package com.airportinfo.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CSVReader implements AutoCloseable {
    private final BufferedReader reader;

    public CSVReader(String path) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(path));
    }

    public String[] read() throws IOException {
        String line = reader.readLine();
        if (line == null)
            return new String[0];

        String[] tuple = line.split("\"");
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < tuple.length; i++) {
            if (i % 2 == 0) {
                if (tuple[i].charAt(0) == ',')
                    tuple[i] = tuple[i].substring(1);
                String[] parts = tuple[i].split(",");
                result.addAll(Arrays.stream(parts).toList());
            } else {
                result.add(tuple[i]);
            }
        }
        return result.toArray(new String[0]);
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
