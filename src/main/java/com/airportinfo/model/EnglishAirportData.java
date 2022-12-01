package com.airportinfo.model;

import com.airportinfo.util.Translator;

import java.util.Locale;

/**
 * EnglishAirportData
 *
 * @author lalaalal
 */
public class EnglishAirportData extends TranslatedAirportData {
    private static final PreTranslatedData regionTranslation = loadPreTranslatedData("translation/pre-translated-region-ko-en.data");

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
        String key = airport.koreanRegion;
        if (regionTranslation.containsKey(key))
            return regionTranslation.get(key);

        String value = Translator.translate("ko", "en", airport.koreanRegion);
        regionTranslation.put(key, value);
        return value;
    }
}
