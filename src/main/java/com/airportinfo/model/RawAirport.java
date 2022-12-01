package com.airportinfo.model;

/**
 * Having all airport data.
 *
 * @author lalaalal
 */
public class RawAirport {
    public static final String[] ATTRIBUTE_NAMES = {"EnglishName", "KoreanName", "IATA", "ICAO", "KoreanRegion", "EnglishCountryName", "KoreanCountryName", "EnglishCityName"};
    public String englishName;
    public String koreanName;
    public String IATA;
    public String ICAO;
    public String koreanRegion;
    public String englishCountryName;
    public String koreanCountryName;
    public String englishCityName;

    public static Airport create(String[] items) {
        RawAirport airport = new RawAirport();
        airport.englishName = items[0];
        airport.koreanName = items[1];
        airport.IATA = items[2];
        airport.ICAO = items[3];
        airport.koreanRegion = items[4];
        airport.englishCountryName = items[5];
        airport.koreanCountryName = items[6];
        airport.englishCityName = items[7];

        return new Airport(airport);
    }

    public String[] toArray() {
        String[] items = new String[8];
        items[0] = englishName;
        items[1] = koreanName;
        items[2] = IATA;
        items[3] = ICAO;
        items[4] = koreanRegion;
        items[5] = englishCountryName;
        items[6] = koreanCountryName;
        items[7] = englishCityName;

        return items;
    }

    @Override
    public String toString() {
        return englishName + ", " +
                koreanName + ", " +
                IATA + ", " +
                ICAO + ", " +
                koreanRegion + ", " +
                englishCountryName + ", " +
                koreanCountryName + ", " +
                englishCityName;
    }
}
