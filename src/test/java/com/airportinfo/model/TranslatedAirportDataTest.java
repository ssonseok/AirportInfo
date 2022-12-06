package com.airportinfo.model;

import com.airportinfo.controller.AirportController;
import com.airportinfo.util.SerializeManager;
import com.airportinfo.util.Translator;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.sql.SQLException;
import java.util.Locale;
import java.util.function.Function;

@Disabled
public class TranslatedAirportDataTest {
    @Test
    public void testKorean() throws SQLException, ClassNotFoundException, NoSuchMethodException, IOException {
        String dataFileName = "pre-translated-city-en-ko.data";

        Locale.setDefault(Locale.ENGLISH);
        AirportController airportController = new AirportController();
        airportController.loadFromDB();

        TranslatedAirportData.PreTranslatedData preTranslated = load(dataFileName);

        translate(preTranslated, airportController.getAirports(), Airport::getCity, "en", "ko");

        write(dataFileName, preTranslated);
    }

    @Test
    public void testEnglish() throws SQLException, ClassNotFoundException, NoSuchMethodException, IOException {
        String dataFileName = "pre-translated-region-ko-en.data";

        Locale.setDefault(Locale.KOREAN);
        AirportController airportController = new AirportController();
        airportController.loadFromDB();

        TranslatedAirportData.PreTranslatedData preTranslated = load(dataFileName);

        translate(preTranslated, airportController.getAirports(), Airport::getRegion, "ko", "en");

        write(dataFileName, preTranslated);
    }

    private void translate(TranslatedAirportData.PreTranslatedData preTranslatedData, Airport[] airports, Function<Airport, String> selector, String sourceLang, String targetLang) {
        int count = 0;
        for (Airport airport : airports) {
            if (count >= 200)
                break;

            String original = selector.apply(airport);

            if (preTranslatedData.containsKey(original))
                continue;
            String translated = Translator.translate(sourceLang, targetLang, selector.apply(airport));
            preTranslatedData.put(original, translated);
            System.out.printf("%s: %s\n", original, translated);

            count += 1;
        }
    }

    private TranslatedAirportData.PreTranslatedData load(String path) {
        File file = new File(path);
        if (!file.exists())
            return new TranslatedAirportData.PreTranslatedData();

        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(path))) {
            return SerializeManager.deserialize(inputStream.readAllBytes(), TranslatedAirportData.PreTranslatedData.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new TranslatedAirportData.PreTranslatedData();
        }
    }

    private void write(String path, Object data) throws IOException {
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path))) {
            outputStream.write(SerializeManager.serialize(data));
            outputStream.flush();
        }
    }
}