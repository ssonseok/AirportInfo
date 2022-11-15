package com.airportinfo.view.chart;

import com.airportinfo.view.ComponentView;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Abstract class of ChartView.
 * Provide legend interface and support color scheme, number format.
 * The method updateChartView(Graphics) will draw in chartPanel.
 *
 * @author lalaalal
 */
public abstract class AbstractChartView extends ComponentView {
    public static final Color[] DEFAULT_COLOR_SCHEME = {Color.decode("#FF8787"), Color.decode("#F8C4B4"), Color.decode("#E5EBB2"), Color.decode("#BCE29E"), Color.decode("#B8E8FC"), Color.decode("#B1AFFF"), Color.decode("#C8FFD4"), Color.decode("#DFD3C3"), Color.decode("#F8EDE3"), Color.decode("#AEBDCA")};
    protected final ChartData legends = new ChartData();
    protected final ChartPanel chartPanel = new ChartPanel();
    protected NumberFormat numberFormat = NumberFormat.INT_FORMAT;
    private Color[] colorScheme = DEFAULT_COLOR_SCHEME;

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
     * Add new Legend with name, value. Need to call updateView() after add legends.
     *
     * @param name  Legend name
     * @param value Legend value
     */
    public void addLegend(String name, Number value) {
        legends.addLegend(name, value);
    }

    /**
     * Set legends if different with this chart.
     *
     * @param chartData New legends
     */
    public void setLegends(ChartData chartData) {
        if (Objects.equals(legends, chartData))
            return;

        clear();
        for (Legend legend : chartData)
            addLegend(legend.name(), legend.value());
    }

    /**
     * Remove all chart data.
     */
    public void clear() {
        legends.clear();
    }

    /**
     * Draw chart using chartPanel's Graphics when ChartPanel.paintComponent(Graphics).
     *
     * @param graphics Graphics from chartPanel
     */
    public abstract void updateChartView(Graphics graphics);

    protected class ChartPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            updateChartView(graphics);
        }
    }
}
