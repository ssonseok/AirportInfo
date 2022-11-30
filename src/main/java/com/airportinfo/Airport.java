package com.airportinfo;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Locale;

/**
 * Airport data support translation.
 *
 * @author lalaalal
 */
public class Airport {
    private final HashMap<Locale, TranslatedAirportData> data = new HashMap<>();
    private final RawAirport airport;

    public Airport(RawAirport airport) {
        this.airport = airport;
    }

    public void addTranslatedAirportData(Class<? extends TranslatedAirportData> type) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        TranslatedAirportData translatedAirportData = type.getDeclaredConstructor(RawAirport.class).newInstance(airport);
        Locale locale = translatedAirportData.getLocale();
        data.put(locale, translatedAirportData);
    }

    public String getAirportName() {
        TranslatedAirportData translatedAirportData = data.get(Locale.getDefault());
        if (translatedAirportData == null)
            return airport.englishName;
        return translatedAirportData.getAirportName();
    }

    public String getCountry() {
        TranslatedAirportData translatedAirportData = data.get(Locale.getDefault());
        if (translatedAirportData == null)
            return airport.englishCountryName;
        return translatedAirportData.getCountry();
    }

    public String getCity() {
        TranslatedAirportData translatedAirportData = data.get(Locale.getDefault());
        if (translatedAirportData == null)
            return airport.englishCityName;
        return translatedAirportData.getCity();
    }

    public String getIATA() {
        return airport.IATA;
    }

    public String getICAO() {
        return airport.ICAO;
    }

    public String getRegion() {
        TranslatedAirportData translatedAirportData = data.get(Locale.getDefault());
        if (translatedAirportData == null)
            return airport.koreanRegion;
        return translatedAirportData.getRegion();
    }

    public RawAirport getRawData() {
        return airport;
    }

    public String[] toArray() {
        String[] array = new String[TranslatedAirportData.ATTRIBUTE_NAMES.length];

        array[0] = getAirportName();
        array[1] = getIATA();
        array[2] = getICAO();
        array[3] = getRegion();
        array[4] = getCountry();
        array[5] = getCity();

        return array;
    }

    @Override
    public String toString() {
        return getAirportName() + ","
                + getIATA() + ","
                + getICAO() + ","
                + getRegion() + ","
                + getCountry() + ","
                + getCity();
    }
}
