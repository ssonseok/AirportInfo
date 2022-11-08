package com.airportinfo.view;

import javax.swing.*;
import java.awt.*;

/**
 * ChartBar includes auto resized label and bar.
 *
 * @author lalaalal
 */
public class ChartBar extends JPanel {
    private static final GridBagConstraints labelConstraints;
    private static final GridBagConstraints barConstraints;

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

    private final JLabel label;
    private final JPanel bar;
    private final GridBagLayout gridBagLayout;
    private Number value;
    private double max;

    /**
     * Create a ChartBar automatically resized with value, max rate.
     *
     * @param value        Entry value
     * @param max          Entry max value
     * @param numberFormat Number format for label
     * @param barColor     Color of bar
     */
    public ChartBar(Number value, double max, NumberFormat numberFormat, Color barColor) {
        this.value = value;
        this.max = max;
        gridBagLayout = new GridBagLayout();
        label = new JLabel(numberFormat.formatNumber(value));
        bar = new JPanel();

        setLayout(gridBagLayout);
        addLabel(numberFormat);
        addBar(barColor);
    }

    private void addLabel(NumberFormat numberFormat) {
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.BOTTOM);

        labelConstraints.weighty = 1 - (value.doubleValue() / max);
        gridBagLayout.setConstraints(label, labelConstraints);
        add(label);
    }

    private void addBar(Color barColor) {
        bar.setBackground(barColor);

        barConstraints.weighty = value.doubleValue() / max;

        gridBagLayout.setConstraints(bar, barConstraints);
        add(bar);
    }

    /**
     * Update label number format.
     *
     * @param numberFormat Format for label
     */
    public void updateLabel(NumberFormat numberFormat) {
        label.setText(numberFormat.formatNumber(value));
    }

    /**
     * Update entry value, max value.
     *
     * @param value Entry value
     * @param max   Entry max value
     */
    public void updateBar(double value, double max) {
        this.value = value;
        this.max = max;

        updateWeight();
    }

    /**
     * Update entry max value.
     *
     * @param max Entry max value
     */
    public void updateMax(double max) {
        this.max = max;

        updateWeight();
    }

    private void updateWeight() {
        labelConstraints.weighty = 1 - (value.doubleValue() / max);
        barConstraints.weighty = value.doubleValue() / max;

        gridBagLayout.setConstraints(label, labelConstraints);
        gridBagLayout.setConstraints(bar, barConstraints);
    }
}
