package com.airportinfo.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Get the map picture of location(Variable) from the Google API URL.
 * (used by GoogleMapFrame.java)
 *
 * @author ShinHeeYoun
 */
public class GoogleMapAPI {
    private static final String MapAPI = "AIzaSyDNconz87XncSFuaSdNts129M43jXohGkc";

    public static BufferedImage getMapImage(String location, int width, int height, int zoom) throws IOException {
        String imageURL = "https://maps.googleapis.com/maps/api/staticmap?center="
                + URLEncoder.encode(location, StandardCharsets.UTF_8)
                + "&zoom=" + zoom + "&size=" + width + "x" + height + "&key=" + MapAPI;
        URL url = new URL(imageURL);
        return ImageIO.read(url);
    }
}
