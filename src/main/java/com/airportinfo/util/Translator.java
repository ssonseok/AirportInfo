package com.airportinfo.util;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Translate using Papago.
 *
 * @author lalaalal
 */
public class Translator {
    private static final String CLIENT_ID = "A9727Muyd4P3dLyLHABn";
    private static final String CLIENT_SECRET = "eHbXOXutnl";
    private static final String API_URL = "https://openapi.naver.com/v1/papago/n2mt";

    public static String translate(String sourceLang, String targetLang, String text) {

        String encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8);

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", CLIENT_ID);
        requestHeaders.put("X-Naver-Client-Secret", CLIENT_SECRET);

        String jsonBody = post(requestHeaders, sourceLang, targetLang, encodedText);
        return parseTranslatedText(jsonBody);
    }

    private static String parseTranslatedText(String body) {
        JSONObject jsonObject = new JSONObject(body);
        return jsonObject.getJSONObject("message").getJSONObject("result").getString("translatedText");
    }

    private static String post(Map<String, String> requestHeaders, String sourceLang, String targetLang, String text) {
        HttpURLConnection con = connect();
        String postParams = "source=" + sourceLang + "&target=" + targetLang + "&text=" + text;
        try {
            con.setRequestMethod("POST");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postParams.getBytes());
                wr.flush();
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
                return readBody(con.getInputStream());
            } else {  // 에러 응답
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect() {
        try {
            URL url = new URL(Translator.API_URL);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + Translator.API_URL, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + Translator.API_URL, e);
        }
    }

    private static String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}
