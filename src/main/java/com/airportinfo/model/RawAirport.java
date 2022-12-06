package com.airportinfo.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Having all airport data.
 *
 * @author lalaalal
 */
public class RawAirport implements Serializable {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RawAirport that = (RawAirport) o;

        if (!Objects.equals(englishName, that.englishName)) return false;
        if (!Objects.equals(koreanName, that.koreanName)) return false;
        if (!Objects.equals(IATA, that.IATA)) return false;
        if (!Objects.equals(ICAO, that.ICAO)) return false;
        if (!Objects.equals(koreanRegion, that.koreanRegion)) return false;
        if (!Objects.equals(englishCountryName, that.englishCountryName))
            return false;
        if (!Objects.equals(koreanCountryName, that.koreanCountryName))
            return false;
        return Objects.equals(englishCityName, that.englishCityName);
    }

    @Override
    public int hashCode() {
        int result = englishName != null ? englishName.hashCode() : 0;
        result = 31 * result + (koreanName != null ? koreanName.hashCode() : 0);
        result = 31 * result + (IATA != null ? IATA.hashCode() : 0);
        result = 31 * result + (ICAO != null ? ICAO.hashCode() : 0);
        result = 31 * result + (koreanRegion != null ? koreanRegion.hashCode() : 0);
        result = 31 * result + (englishCountryName != null ? englishCountryName.hashCode() : 0);
        result = 31 * result + (koreanCountryName != null ? koreanCountryName.hashCode() : 0);
        result = 31 * result + (englishCityName != null ? englishCityName.hashCode() : 0);
        return result;
    }
}
