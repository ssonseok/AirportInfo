package com.airportinfo.view.chart;

import com.airportinfo.view.ComponentView;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;

/**
 * Show legend detail using color.
 *
 * @author lalaalal
 */
class LegendDetailView extends ComponentView {
    public static final int LAYOUT_GAP = 5;
    public static final int INDICATOR_THICKNESS = 5;
    private final JPanel panel = new JPanel();
    private final JPanel indicator = new JPanel();
    private final String name;

    public LegendDetailView(String name, Color color) {
        this.name = name;
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, LAYOUT_GAP, LAYOUT_GAP));
        indicator.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel.add(indicator);
        JLabel label = new JLabel();
        label.setText(name);
        panel.add(label);

        indicator.setBorder(BorderFactory.createLineBorder(color, INDICATOR_THICKNESS));
        label.setText(name);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Get legend name.
     *
     * @return Legend name
     */
    public String getLegendName() {
        return name;
    }

    /**
     * Change indicator color.
     *
     * @param color New color to change
     */
    public void setIndicatorColor(Color color) {
        indicator.setBorder(BorderFactory.createLineBorder(color, INDICATOR_THICKNESS));
    }
}
