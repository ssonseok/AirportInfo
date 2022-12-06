package com.airportinfo.model;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

/**
 * Airport data support translation.
 *
 * @author lalaalal
 */
public class Airport implements Serializable {
    public static final String[] ATTRIBUTE_NAMES = {"Airport Name", "IATA", "ICAO", "Region", "Country", "City"};
    private static final HashMap<Locale, String[]> localizedAttributeNames = new HashMap<>();
    private static final HashSet<Class<? extends TranslatedAirportData>> supportedTranslation = new HashSet<>();
    private final HashMap<Locale, TranslatedAirportData> translatedData = new HashMap<>();
    private final RawAirport rawAirport;

    /**
     * Add translation support. Added translations will be generated during initialize Airport.
     * Only valid TranslatedAirportData will be added.
     *
     * @param type Class of TranslatedAirportData to support
     */
    public static void addTranslation(Class<? extends TranslatedAirportData> type) {
        try {
            type.getDeclaredConstructor(RawAirport.class).newInstance((RawAirport) null);
            supportedTranslation.add(type);
        } catch (InvocationTargetException | InstantiationException
                 | IllegalAccessException | NoSuchMethodException ignored) {

        }
    }

    public static void addLocalizedAttributeNames(Locale locale, String[] attributeNames) {
        if (attributeNames.length == ATTRIBUTE_NAMES.length)
            localizedAttributeNames.put(locale, attributeNames);
    }

    public static String[] getLocalizedAttributeNames() {
        Locale locale = Locale.getDefault();
        String[] attributeNames = localizedAttributeNames.get(locale);
        if (attributeNames == null)
            return ATTRIBUTE_NAMES;
        return attributeNames;
    }

    public Airport(RawAirport rawAirport) {
        this.rawAirport = rawAirport;
        for (Class<? extends TranslatedAirportData> type : supportedTranslation)
            addTranslatedAirportData(type);
    }

    private void addTranslatedAirportData(Class<? extends TranslatedAirportData> type) {
        try {
            TranslatedAirportData translatedAirportData = type.getDeclaredConstructor(RawAirport.class).newInstance(rawAirport);
            Locale locale = translatedAirportData.getLocale();
            translatedData.put(locale, translatedAirportData);
        } catch (Exception ignored) {

        }
    }

    public String getAirportName() {
        TranslatedAirportData translatedAirportData = translatedData.get(Locale.getDefault());
        if (translatedAirportData == null)
            return rawAirport.englishName;
        return translatedAirportData.getAirportName();
    }

    public String getCountry() {
        TranslatedAirportData translatedAirportData = translatedData.get(Locale.getDefault());
        if (translatedAirportData == null)
            return rawAirport.englishCountryName;
        return translatedAirportData.getCountry();
    }

    public String getCity() {
        TranslatedAirportData translatedAirportData = translatedData.get(Locale.getDefault());
        if (translatedAirportData == null)
            return rawAirport.englishCityName;
        return translatedAirportData.getCity();
    }

    public String getIATA() {
        return rawAirport.IATA;
    }

    public String getICAO() {
        return rawAirport.ICAO;
    }

    public String getRegion() {
        TranslatedAirportData translatedAirportData = translatedData.get(Locale.getDefault());
        if (translatedAirportData == null)
            return rawAirport.koreanRegion;
        return translatedAirportData.getRegion();
    }

    public RawAirport getRawData() {
        return rawAirport;
    }

    public String[] toArray() {
        String[] array = new String[ATTRIBUTE_NAMES.length];

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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Airport airport)
            return getRawData().equals(airport.rawAirport);
        return false;
    }
}
