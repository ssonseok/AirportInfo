package com.airportinfo.view;

import javax.swing.*;
import java.awt.*;

public class ChartBar extends JPanel {
    private static final GridBagConstraints labelConstraints;
    private static final GridBagConstraints barConstraints;

    private final JLabel label;
    private final JPanel bar;

    private final GridBagLayout gridBagLayout;

    private double value;
    private double max;

    static {
        labelConstraints = new GridBagConstraints();
        labelConstraints.fill = GridBagConstraints.BOTH;
        labelConstraints.gridx = 0;
        labelConstraints.gridy = 0;
        labelConstraints.weightx = 1;

        barConstraints = new GridBagConstraints();
        barConstraints.fill = GridBagConstraints.BOTH;
        barConstraints.anchor = GridBagConstraints.CENTER;
        barConstraints.gridx = 0;
        barConstraints.gridy = 1;
        barConstraints.weightx = 1;
        barConstraints.insets = new Insets(0, 15, 0, 15);
    }

    /** *
     * Create a ChartBar automatically resized with value, max rate
     * @param value - entry value
     * @param max - entry max value
     * @param barColor - color of bar
     */
    public ChartBar(double value, double max, Color barColor) {
        this.value = value;
        this.max = max;

        label = new JLabel(String.valueOf(value));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.BOTTOM);

        bar = new JPanel();
        bar.setBackground(barColor);

        gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);

        labelConstraints.weighty = 1 - (value / max);
        barConstraints.weighty = value / max;

        gridBagLayout.setConstraints(label, labelConstraints);
        add(label);

        gridBagLayout.setConstraints(bar, barConstraints);
        add(bar);
    }

    /** *
     * Update entry value, max value
     * @param value - entry value
     * @param max - entry max value
     */
    public void updateBar(double value, double max) {
        this.value = value;
        this.max = max;

        updateWeight();
    }

    /** *
     * Update entry max value
     * @param max - entry max value
     */
    public void updateMax(double max) {
        this.max = max;

        updateWeight();
    }

    private void updateWeight() {
        labelConstraints.weighty = 1 - (value / max);
        barConstraints.weighty = value / max;

        gridBagLayout.setConstraints(label, labelConstraints);
        gridBagLayout.setConstraints(bar, barConstraints);
    }
}
