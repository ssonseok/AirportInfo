package com.airportinfo;

import java.util.HashMap;
import java.util.Locale;

/**
 * Korean Airport Data.
 *
 * @author lalaalal
 */
public class KoreanAirportData extends TranslatedAirportData {
    public static final String[] ATTRIBUTE_NAMES_KR = {"공항 이름", "IATA", "ICAO", "지역", "국가", "도시"};
    private static final HashMap<String, String> cache = new HashMap<>();

    public KoreanAirportData(RawAirport airport) {
        super(airport);
    }

    @Override
    public String[] getAttributeNames() {
        return ATTRIBUTE_NAMES_KR;
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
        return airport.englishCityName;
    }

    @Override
    public String getRegion() {
        return airport.koreanRegion;
    }
}
