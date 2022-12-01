package com.airportinfo.model;

import com.airportinfo.util.SerializeManager;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;

/**
 * TranslatedAirportData.
 *
 * @author lalaalal
 */
public abstract class TranslatedAirportData {
    protected final RawAirport airport;

    protected static PreTranslatedData loadPreTranslatedData(String path) {
        InputStream inputStream = TranslatedAirportData.class.getClassLoader().getResourceAsStream(path);
        if (inputStream == null)
            return new PreTranslatedData();
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
            byte[] bytes = bufferedInputStream.readAllBytes();
            return SerializeManager.deserialize(bytes, PreTranslatedData.class);
        } catch (IOException e) {
            return new PreTranslatedData();
        }
    }

    public TranslatedAirportData(RawAirport airport) {
        this.airport = airport;
    }

    public abstract Locale getLocale();

    public abstract String getAirportName();

    public abstract String getCountry();

    public abstract String getCity();

    public abstract String getRegion();

    protected static class PreTranslatedData extends HashMap<String, String> {

    }
}
