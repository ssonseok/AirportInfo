package com.airportinfo.view.chart;

import com.airportinfo.view.ComponentView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Abstract class of ChartView.
 * Provide entry interface and support color scheme, number format.
 * The method updateChartView(Graphics) will draw in chartPanel.
 *
 * @author lalaalal
 */
public abstract class AbstractChartView extends ComponentView {
    private class ChartPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            updateChartView(graphics);
        }
    }

    public static final Color[] DEFAULT_COLOR_SCHEME = {Color.decode("#FF8787"), Color.decode("#F8C4B4"), Color.decode("#E5EBB2"), Color.decode("#BCE29E"), Color.decode("#B8E8FC"), Color.decode("#B1AFFF"), Color.decode("#C8FFD4"), Color.decode("#DFD3C3"), Color.decode("#F8EDE3"), Color.decode("#AEBDCA")};
    protected final ArrayList<Entry> entries = new ArrayList<>();
    private Color[] colorScheme = DEFAULT_COLOR_SCHEME;
    protected NumberFormat numberFormat = NumberFormat.intFormat;
    protected final ChartPanel chartPanel = new ChartPanel();

    /**
     * Change color scheme.
     *
     * @param colorScheme Color scheme to change.
     */
    public void setColorScheme(Color[] colorScheme) {
        if (colorScheme != null)
            this.colorScheme = colorScheme;
    }

    /**
     * Get nth color. Cycle if index is bigger than length of color scheme.
     *
     * @param index Index of color scheme
     * @return Nth color
     */
    public Color getColor(int index) {
        return colorScheme[index % colorScheme.length];
    }

    /**
     * Change number format.
     *
     * @param numberFormat Number format to change
     */
    public void setNumberFormat(NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
    }

    /**
     * Create a JLabel value of number using NumberFormatter set by setNumberFormat(NumberFormatter).
     *
     * @param number Number to show
     * @return JLabel showing number
     */
    public JLabel createLabel(Number number) {
        String label = numberFormat.formatNumber(number);
        return new JLabel(label);
    }

    /**
     * Add new Entry with name, value. Need to call updateView() after add entries.
     *
     * @param name  Entry name
     * @param value Entry value
     */
    public void addEntry(String name, Number value) {
        entries.add(new Entry(name, value));
    }

    /**
     * Remove all chart data.
     */
    public void clear() {
        entries.clear();
    }

    /**
     * Draw chart using chartPanel's Graphics when ChartPanel.paintComponent(Graphics).
     *
     * @param graphics Graphics from chartPanel
     */
    public abstract void updateChartView(Graphics graphics);
}
