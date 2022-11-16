package com.airportinfo.view.chart;

import java.util.ArrayList;

/**
 * Wrapper class of ArrayList Legend.
 *
 * @author lalaalal
 */
public class LegendList extends ArrayList<Legend> {
    public void addLegend(String name, Number value) {
        add(new Legend(name, value));
    }
}
