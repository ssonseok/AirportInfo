package com.airportinfo.model;

import java.util.Locale;

/**
 * Korean Airport Data.
 *
 * @author lalaalal
 */
public class KoreanAirportData extends TranslatedAirportData {
    public static final String[] ATTRIBUTE_NAMES_KR = {"공항 이름", "IATA", "ICAO", "지역", "국가", "도시"};

    private static final PreTranslatedData cityTranslation = loadPreTranslatedData("translation/pre-translated-city-en-ko.data");

    static {
        Airport.addLocalizedAttributeNames(Locale.KOREAN, ATTRIBUTE_NAMES_KR);
    }

    public KoreanAirportData(RawAirport airport) {
        super(airport);
    }

    @Override
    public Locale getLocale() {
        return Locale.KOREAN;
    }

    @Override
    public String getAirportName() {
        return airport.koreanName;
    }

    @Override
    public String getCountry() {
        return airport.koreanCountryName;
    }

    @Override
    public String getCity() {
        String key = airport.englishCityName;
        if (cityTranslation.containsKey(key))
            return cityTranslation.get(key);
        return airport.englishCityName;
    }

    @Override
    public String getRegion() {
        return airport.koreanRegion;
    }
}
