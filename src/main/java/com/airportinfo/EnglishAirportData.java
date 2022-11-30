package com.airportinfo;

import java.util.Locale;

/**
 * EnglishAirportData
 *
 * @author lalaalal
 */
public class EnglishAirportData extends TranslatedAirportData {
    public EnglishAirportData(RawAirport airport) {
        super(airport);
    }

    @Override
    public Locale getLocale() {
        return Locale.ENGLISH;
    }

    @Override
    public String getAirportName() {
        return airport.englishName;
    }

    @Override
    public String getCountry() {
        return airport.englishCountryName;
    }

    @Override
    public String getCity() {
        return airport.englishCityName;
    }

    @Override
    public String getRegion() {
        return airport.koreanRegion;
    }
}
