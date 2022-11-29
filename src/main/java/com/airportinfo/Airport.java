package com.airportinfo;

import lombok.Getter;

@Getter
public class Airport {
    public String englishName;
    public String koreanName;
    public String IATA;
    public String ICAO;
    public String region;
    public String englishCountryName;
    public String koreanCountryName;
    public String englishCityName;

    public static Airport create(String[] items) {
        Airport airport = new Airport();
        airport.englishName = items[0];
        airport.koreanName = items[1];
        airport.IATA = items[2];
        airport.ICAO = items[3];
        airport.region = items[4];
        airport.englishCountryName = items[5];
        airport.koreanCountryName = items[6];
        airport.englishCityName = items[7];

        return airport;
    }

    public String[] toArray() {
        String[] items = new String[8];
        items[0] = englishName;
        items[1] = koreanName;
        items[2] = IATA;
        items[3] = ICAO;
        items[4] = region;
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
                region + ", " +
                englishCountryName + ", " +
                koreanCountryName + ", " +
                englishCityName;
    }
}
