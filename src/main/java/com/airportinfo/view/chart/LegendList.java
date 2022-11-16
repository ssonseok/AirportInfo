package com.airportinfo.view.chart;

import java.util.ArrayList;

/**
 * Wrapper class of ArrayList Legend.
 *
 * @author lalaalal
 */
public class LegendList extends ArrayList<Legend> {

    /**
     * Add legend. Value should be bigger equal than 0.
     *
     * @param legend Legend to add
     * @return True if this collection changed as a result of the call
     */
    @Override
    public boolean add(Legend legend) {
        if (legend.value().doubleValue() < 0)
            return false;
        return super.add(legend);
    }

    /**
     * Add legend. Value should be bigger equal than 0.
     *
     * @param name  Name of legend
     * @param value Value of legend
     * @return True if this collection changed as a result of the call
     */
    public boolean add(String name, Number value) {
        return add(new Legend(name, value));
    }
}
