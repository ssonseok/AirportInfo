package com.airportinfo.view.chart;

import com.airportinfo.view.ComponentGroup;
import com.airportinfo.view.ComponentView;

import javax.swing.*;
import java.awt.*;

/**
 * Group view of legend details.
 *
 * @author lalaalal
 */
class LegendDetailGroupView extends ComponentGroup {
    private final JPanel panel = new JPanel();
    private final GridLayout layout = new GridLayout(0, 1);
    private AbstractChartView chartView;
    private int legendColumnLimit = Integer.MAX_VALUE;

    public LegendDetailGroupView(AbstractChartView chartView) {
        this.chartView = chartView;
        panel.setLayout(layout);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Add legend.
     *
     * @param name Name of legend to add.
     */
    public void addLegend(String name) {
        Color color = chartView.getColor(name);
        LegendDetailView legendDetailView = new LegendDetailView(name, color);
        if (chartView.getNumLegends() <= legendColumnLimit)
            layout.setColumns(chartView.getNumLegends());
        panel.add(legendDetailView.getPanel());
        addComponentView(legendDetailView);
    }

    @Override
    public void clear() {
        super.clear();
        panel.removeAll();
        layout.setColumns(1);
    }

    /**
     * Set limit of legend detail columns.
     *
     * @param limit Value of limit.
     */
    public void setLegendColumnLimit(int limit) {
        legendColumnLimit = limit;
    }

    /**
     * Change chart view. Skip if chart view is same.
     *
     * @param chartView Chart view to change.
     */
    public void setChartView(AbstractChartView chartView) {
        if (this.chartView.equals(chartView))
            return;

        this.chartView = chartView;
        for (Legend legend : chartView.getLegends())
            addLegend(legend.name());
    }

    /**
     * Update colors of legend detail views.
     * Call when color scheme of chart view is changed.
     */
    public void updateColor() {
        for (ComponentView componentView : componentViews) {
            if (componentView instanceof LegendDetailView legendDetailView) {
                String legendName = legendDetailView.getLegendName();
                legendDetailView.setIndicatorColor(chartView.getColor(legendName));
            }
        }
    }
}
