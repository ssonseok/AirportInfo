package com.airportinfo.utils;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

/**
 * Provide some fonts and set default font.
 *
 * @author lalaalal
 */
public class FontManager {
    public static final Font HEADER_FONT = UIManager.getDefaults().getFont("Label.font").deriveFont(18f);

    /**
     * Set default font to param.
     *
     * @param fontUIResource font resource to set as default font.
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
