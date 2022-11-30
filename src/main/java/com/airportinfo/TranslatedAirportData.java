package com.airportinfo;

import java.util.Locale;

/**
 * TranslatedAirportData.
 *
 * @author lalaalal
 */
public abstract class TranslatedAirportData {
    protected final RawAirport airport;

    public TranslatedAirportData(RawAirport airport) {
        this.airport = airport;
    }

    public abstract Locale getLocale();

    public abstract String getAirportName();

    public abstract String getCountry();

    public abstract String getCity();

    public abstract String getRegion();
}
