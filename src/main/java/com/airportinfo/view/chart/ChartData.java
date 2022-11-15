package com.airportinfo.view.chart;

import java.util.ArrayList;

/**
 * Wrapper class of ArrayList Legend.
 *
 * @author lalaalal
 */
@Deprecated
public class ChartData extends ArrayList<Legend> {
    public void addLegend(String name, Number value) {
        add(new Legend(name, value));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ChartData chartData) {
            if (size() != chartData.size())
                return false;

            for (int i = 0; i < size(); i++) {
                if (!get(i).equals(chartData.get(i)))
                    return false;
            }
            return true;
        }
        return false;
    }
}
