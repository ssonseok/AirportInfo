package com.airportinfo.view;

import javax.swing.*;
import java.awt.*;

/**
 * Abstract class of ChartView.
 * Provide entry interface and support color scheme, number format.
 *
 * @author lalaalal
 */
public abstract class AbstractChartView extends ComponentView {
    public static final Color[] DEFAULT_COLOR_SCHEME = {Color.decode("#FF8787"), Color.decode("#F8C4B4"), Color.decode("#E5EBB2"), Color.decode("#BCE29E"), Color.decode("#B8E8FC"), Color.decode("#B1AFFF"), Color.decode("#C8FFD4"), Color.decode("#DFD3C3"), Color.decode("#F8EDE3"), Color.decode("#AEBDCA")};
    private Color[] colorScheme = DEFAULT_COLOR_SCHEME;
    protected NumberFormat numberFormat = NumberFormat.intFormat;

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
     * Add new Entry with name, value.
     *
     * @param name  Entry name
     * @param value Entry value
     */
    public abstract void addEntry(String name, Number value);

    /**
     * Remove all chart data.
     */
    public abstract void clear();
}
