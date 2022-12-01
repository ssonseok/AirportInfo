package com.airportinfo.view.airport;

import com.airportinfo.model.Airport;
import com.airportinfo.view.chart.LegendList;

import java.util.HashMap;
import java.util.function.Function;

/**
 * AirportStatisticCreator using airport attribute.
 *
 * @author lalaalal
 */
public class AttributeStatisticCreator implements AirportStatisticCreator {
    private final Function<Airport, String> attributeSelector;

    public AttributeStatisticCreator(Function<Airport, String> attributeSelector) {
        this.attributeSelector = attributeSelector;
    }

    @Override
    public LegendList createStatistic(Iterable<Airport> airports) {
        HashMap<String, Integer> statistic = new HashMap<>();

        for (Airport airport : airports) {
            String key = attributeSelector.apply(airport);
            if (statistic.containsKey(key)) {
                int value = statistic.get(key);
                statistic.put(key, value + 1);
            } else {
                statistic.put(key, 1);
            }
        }

        LegendList legends = new LegendList();
        for (String key : statistic.keySet()) {
            int value = statistic.get(key);
            legends.add(key, value);
        }

        return legends;
    }
}
