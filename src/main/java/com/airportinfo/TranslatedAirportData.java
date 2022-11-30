package com.airportinfo;

import java.util.Locale;

/**
 * TranslatedAirportData.
 *
 * @author lalaalal
 */
public abstract class TranslatedAirportData {
    public static final String[] ATTRIBUTE_NAMES = {"Airport Name", "IATA", "ICAO", "Region", "Country", "City"};
    protected final RawAirport airport;

    public TranslatedAirportData(RawAirport airport) {
        this.airport = airport;
    }

    public String[] getAttributeNames() {
        return ATTRIBUTE_NAMES;
    }

    public abstract Locale getLocale();

    public abstract String getAirportName();

    public abstract String getCountry();

    public abstract String getCity();

    public abstract String getRegion();
}
