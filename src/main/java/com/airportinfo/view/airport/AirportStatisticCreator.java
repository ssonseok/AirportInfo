package com.airportinfo.view.airport;

import com.airportinfo.Airport;
import com.airportinfo.view.chart.LegendList;

public interface AirportStatisticCreator {
    LegendList createStatistic(Iterable<Airport> airports);
}
