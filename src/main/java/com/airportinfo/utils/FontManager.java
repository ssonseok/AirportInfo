package com.airportinfo.utils;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

/**
 * Provide some fonts and set default font.
 *
 * @author lalaalal
 */
public class FontManager {
    public static final Font HEADER_FONT = UIManager.getDefaults().getFont("Label.font").deriveFont(18f);

    /**
     * Set default font to SCDream in resources.
     *
     * @throws IOException         If font file not found
     * @throws FontFormatException If the fontStream data does not contain the required font tables for the specified format
     */
    public static void loadFont() throws IOException, FontFormatException {
        try (InputStream is = FontManager.class.getClassLoader().getResourceAsStream("font/SCDream4.otf")) {
            if (is == null)
                throw new IOException("Font not found");
            Font font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(13f);
            setUIFont(new FontUIResource(font));
        }
    }

    /**
     * Set default font to param.
     *
     * @param fontUIResource Font resource to set as default font
     */
    public static void setUIFont(FontUIResource fontUIResource) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource)
                UIManager.put(key, fontUIResource);
        }
    }
}
