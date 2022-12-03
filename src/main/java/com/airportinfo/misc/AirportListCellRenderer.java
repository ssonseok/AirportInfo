package com.airportinfo.misc;

import com.airportinfo.model.Airport;

import javax.swing.*;
import java.awt.*;

public class AirportListCellRenderer extends JLabel implements ListCellRenderer<Airport> {
    public AirportListCellRenderer() {
        setOpaque(true);
        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Airport> list, Airport value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.getAirportName());

        Color plainBackground = UIManager.getDefaults().getColor("List.background");
        Color plainForeground = UIManager.getDefaults().getColor("List.foreground");
        Color selectionBackground = UIManager.getDefaults().getColor("List.selectionBackground");
        Color selectionForeground = UIManager.getDefaults().getColor("List.selectionForeground");

        Color background = isSelected ? selectionBackground : plainBackground;
        Color foreground = isSelected ? selectionForeground : plainForeground;

        setBackground(background);
        setForeground(foreground);

        return this;
    }
}
