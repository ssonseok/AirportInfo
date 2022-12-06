package com.airportinfo.util;

import com.airportinfo.Setting;
import com.airportinfo.model.Airport;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

/**
 * Web crawling from wikipedia.
 *
 * @author lalaalal
 */
public class AirportWikiCrawler {
    private static final String EN_WIKI_URL = "https://en.wikipedia.org/wiki/";
    private static final HashMap<String, DocumentCacheData> documentCache = new HashMap<>();
    private static final HashMap<String, String> translationCache = new HashMap<>();
    private final Document document;
    private final Airport airport;
    private final boolean englishOnly;

    private static String getLocalizedWikiURL() {
        Locale locale = Locale.getDefault();
        return EN_WIKI_URL.replace("en.wikipedia", locale + ".wikipedia");
    }

    private static DocumentCacheData createDocument(Airport airport) throws IOException {
        try {
            String airportSearchTerm = makeAirportSearchTerm(airport.getAirportName());
            String url = getLocalizedWikiURL() + airportSearchTerm;
            return new DocumentCacheData(Locale.getDefault(), Jsoup.connect(url).get());
        } catch (HttpStatusException e) {
            String airportSearchTerm = makeAirportSearchTerm(airport.getRawData().englishName);
            String url = EN_WIKI_URL + airportSearchTerm;
            return new DocumentCacheData(Locale.ENGLISH, Jsoup.connect(url).get());
        }
    }

    private static boolean isEnglishOnly(DocumentCacheData documentCacheData) {
        return !Locale.getDefault().equals(Locale.ENGLISH)
                && documentCacheData.locale.equals(Locale.ENGLISH);
    }

    /**
     * Load document from wikipedia.
     *
     * @param airport Airport to search
     * @throws HttpStatusException If page not found
     * @throws IOException         If an I/O error occurs
     */
    public AirportWikiCrawler(Airport airport) throws IOException {
        this.airport = airport;
        DocumentCacheData cachedDocumentData = documentCache.get(airport.getAirportName());
        if (cachedDocumentData != null) {
            document = cachedDocumentData.document;
            englishOnly = isEnglishOnly(cachedDocumentData);
        } else {
            DocumentCacheData documentCacheData = createDocument(airport);
            document = documentCacheData.document;
            englishOnly = isEnglishOnly(documentCacheData);
            documentCache.put(airport.getAirportName(), documentCacheData);
        }
    }

    /**
     * Get information about airport.
     *
     * @return Information about airport
     */
    public String getInformation() {
        try {
            String information = findAirportInformation();
            if (englishOnly && Setting.getInstance().isLocalizeEnglish())
                return translateInformation(information);
            return information;
        } catch (RuntimeException e) {
            return Translator.getBundleString("not_found");
        }
    }

    private String translateInformation(String information) {
        String cachedTranslation = translationCache.get(information);
        if (cachedTranslation != null)
            return cachedTranslation;
        String translatedInformation = Translator.translate(Locale.ENGLISH, Locale.getDefault(), information);
        translationCache.put(information, translatedInformation);
        return translatedInformation;
    }

    private String findAirportInformation() {
        Elements elements = document.select("#mw-content-text > div.mw-parser-output > p");
        for (Element element : elements) {
            String airportInfo = element.text().replaceAll("\\[[0-9]+]", "");
            if (airportInfo.contains(airport.getIATA())
                    || airportInfo.contains(airport.getAirportName())
                    || airportInfo.contains(airport.getRawData().englishName))
                return airportInfo;
        }
        throw new RuntimeException("not_found");
    }

    public String[] getImages(int numImages) {
        String[] result = new String[numImages];
        Elements elements = document.select("img");
        int count = 0;
        for (Element element : elements) {
            if (count >= numImages)
                break;

            String sourceURL = element.attr("src").replaceFirst("^/+", "https://");
            if (sourceURL.contains("static"))
                continue;
            result[count] = sourceURL;
            count += 1;
        }

        return result;
    }

    /**
     * Search about following airport.
     *
     * @param airport Target
     * @return Information about given airport
     */
    @Deprecated
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

    private static String searchEnglishInfo(Airport airport) {
        try {
            Locale currentLocale = Locale.getDefault();
            String airportSearchTerm = makeAirportSearchTerm(airport.getRawData().englishName);
            String englishInformation = searchInfo(EN_WIKI_URL + airportSearchTerm, airport);
            if (!currentLocale.equals(Locale.ENGLISH) && Setting.getInstance().isLocalizeEnglish())
                return Translator.translate(Locale.ENGLISH, currentLocale, englishInformation);
            return englishInformation;
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

    private record DocumentCacheData(Locale locale, Document document) {
    }
}