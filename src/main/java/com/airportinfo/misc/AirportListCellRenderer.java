package com.airportinfo.misc;

import com.airportinfo.model.Airport;
import com.airportinfo.util.FontManager;
import com.airportinfo.util.ThemeManager;
import com.airportinfo.util.Translator;

import javax.swing.*;
import java.awt.*;

/**
 * List cell renderer.
 *
 * @author lalaalal
 */
public class AirportListCellRenderer extends JLabel implements ListCellRenderer<Airport> {
    public AirportListCellRenderer() {
        setOpaque(true);
        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Airport> list, Airport value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value == null) setText(Translator.getBundleString("empty"));
        else setText(shrinkString(value.getAirportName(), list.getWidth()));

        Color plainBackground = ThemeManager.getDefaultColor("List.background");
        Color plainForeground = ThemeManager.getDefaultColor("List.foreground");
        Color selectionBackground = ThemeManager.getDefaultColor("List.selectionBackground");
        Color selectionForeground = ThemeManager.getDefaultColor("List.selectionForeground");

        Color background = isSelected ? selectionBackground : plainBackground;
        Color foreground = isSelected ? selectionForeground : plainForeground;

        setBackground(background);
        setForeground(foreground);

        return this;
    }

    private String shrinkString(String text, int listWidth) {
        FontMetrics fontMetrics = getFontMetrics(FontManager.getFont());
        String shrinkText = text;
        String dot = "...";
        int requiredWidth = listWidth - fontMetrics.stringWidth(dot) - 35;

        while (fontMetrics.stringWidth(shrinkText) > requiredWidth) {
            int endIndex = shrinkText.length() - 6;
            if (endIndex < 0)
                return shrinkText;
            shrinkText = text.substring(0, endIndex) + dot;
        }

        return shrinkText;
    }
}
