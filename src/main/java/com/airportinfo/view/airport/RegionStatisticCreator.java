package com.airportinfo.view.airport;

import com.airportinfo.Airport;
import com.airportinfo.view.chart.LegendList;

import java.util.HashMap;

public class RegionStatisticCreator implements AirportStatisticCreator {
    @Override
    public LegendList createStatistic(Iterable<Airport> airports) {
        HashMap<String, Integer> statistic = new HashMap<>();

        for (Airport airport : airports) {
            String key = airport.region;
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
