package com.airportinfo.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/** *
 * ChartPanel show chart with bar (histogram)
 */
public class ChartPanel extends JPanel {
    private static final Color[] COLORS = {Color.decode("#FF8787"), Color.decode("#F8C4B4"), Color.decode("#E5EBB2"), Color.decode("#BCE29E"), Color.decode("#B8E8FC"), Color.decode("#B1AFFF"), Color.decode("#C8FFD4"), Color.decode("#DFD3C3"), Color.decode("#F8EDE3"), Color.decode("#AEBDCA")};

    private JPanel panel;
    private JLabel titleLabel;
    private JPanel chartPanel;
    private JPanel entryPanel;
    private JPanel axisPanel;

    private double max = 0.0;
    private int numEntry = 0;

    private final ArrayList<ChartBar> chartBars = new ArrayList<>();

    public ChartPanel() {

    }

    public ChartPanel(String title) {
        titleLabel.setText(title);
    }

    private double calcMax(double value) {
        int digit = (int) Math.log10(value);
        int firstDigit = (int) (value / Math.pow(10, digit));
        return (firstDigit + 1) * Math.pow(10, digit);
    }

    /** *
     * Add new Entry with name, value
     * @param name - entry name
     * @param value - entry value
     */
    public void addEntry(String name, double value) {
        updateAxis(value);

        ChartBar bar = new ChartBar(value, max, COLORS[numEntry % COLORS.length]);
        chartPanel.add(bar);
        chartBars.add(bar);

        JLabel label = new JLabel(name);
        label.setHorizontalAlignment(JLabel.CENTER);
        entryPanel.add(label);

        numEntry += 1;
    }

    private void updateAxis(double value) {
        double prevMax = max;
        max = Math.max(max, calcMax(value));

        if (prevMax == max)
            return;

        for (ChartBar chartBar : chartBars)
            chartBar.updateMax(max);

        axisPanel.removeAll();
        int digit = (int) Math.log10(value);
        int firstDigit = (int) (value / Math.pow(10, digit));

        for (int i = firstDigit; i >= 0; i--) {
            double unit = i * Math.pow(10, digit);
            JLabel unitLabel = new JLabel(String.valueOf(unit));
            unitLabel.setVerticalAlignment(JLabel.BOTTOM);
            axisPanel.add(unitLabel);
        }
    }

    private void createUIComponents() {
        panel = this;
        GridLayout gridLayout = new GridLayout(1, 0);
        GridLayout axisGridLayout = new GridLayout(0, 1);
        chartPanel = new JPanel();
        chartPanel.setLayout(gridLayout);
        entryPanel = new JPanel();
        entryPanel.setLayout(gridLayout);
        axisPanel = new JPanel();
        axisPanel.setLayout(axisGridLayout);
    }
}
