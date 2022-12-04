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
    private AbstractChartView chartView;

    public LegendDetailGroupView(AbstractChartView chartView) {
        this.chartView = chartView;
        panel.setLayout(new FlowLayout());
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
        panel.add(legendDetailView.getPanel());
        addComponentView(legendDetailView);
    }

    @Override
    public void clear() {
        super.clear();
        panel.removeAll();
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
