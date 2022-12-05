package com.airportinfo.util;

import com.airportinfo.model.Airport;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Locale;

/**
 * Web crawling from wikipedia.
 *
 * @author lalaalal
 */
public class WikiCrawler {
    private static final String EN_WIKI_URL = "https://en.wikipedia.org/wiki/";

    private static String getLocalizedWikiURL() {
        Locale locale = Locale.getDefault();
        return EN_WIKI_URL.replace("en.wikipedia", locale + ".wikipedia");
    }

    /**
     * Search about following airport.
     *
     * @param airport Target
     * @return Information about given airport
     */
    public static String searchLocalizedInfo(Airport airport) {
        try {
            String airportSearchTerm = makeAirportSearchTerm(airport.getAirportName());
            String url = getLocalizedWikiURL() + airportSearchTerm;
            return searchInfo(url, airport);
        } catch (RuntimeException | HttpStatusException e) {
            if (Locale.getDefault().equals(Locale.ENGLISH))
                return Translator.getBundleString("not_found");
            return searchEnglishInfo(airport);
        } catch (IOException e) {
            return Translator.getBundleString("connection_failed");
        }
    }

    public static String searchEnglishInfo(Airport airport) {
        try {
            String airportSearchTerm = makeAirportSearchTerm(airport.getRawData().englishName);
            return searchInfo(EN_WIKI_URL + airportSearchTerm, airport);
        } catch (RuntimeException | HttpStatusException e) {
            return Translator.getBundleString("not_found");
        } catch (IOException e) {
            return Translator.getBundleString("connection_failed");
        }
    }

    private static String makeAirportSearchTerm(String name) {
        return name.trim().replace(' ', '_');
    }

    private static String searchInfo(String url, Airport airport) throws IOException {
        Document document = Jsoup.connect(url).get();
        Elements elements = document.select("#mw-content-text > div.mw-parser-output > p");
        for (Element element : elements) {
            String airportInfo = element.text().replaceAll("\\[[0-9]+]", "");
            if (airportInfo.contains(airport.getIATA()))
                return airportInfo;
        }
        throw new RuntimeException("not_found");
    }
}