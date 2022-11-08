package com.airportinfo.view;

import com.airportinfo.Airport;

import java.util.ArrayList;
import java.util.Collection;

/**
 * An abstract view class handling airports.
 *
 * @author lalaalal
 */
public abstract class AirportView extends ComponentView {
    protected final ArrayList<Airport> airports = new ArrayList<>();

    /**
     * Need to call updateView() after addAirport(Airport).
     *
     * @param airport Airport to add
     */
    public void addAirport(Airport airport) {
        airports.add(airport);
    }

    /**
     * Need to call updateView() after addAirports(Collection<? extends Airport>).
     *
     * @param airports Collection of airport to add
     */
    public void addAirports(Collection<? extends Airport> airports) {
        this.airports.addAll(airports);
    }

    /**
     * Replace all airports with param.
     * This function automatically calls updateView().
     *
     * @param airports Collection of airport to set airports
     */
    public void setAirports(Collection<? extends Airport> airports) {
        this.airports.clear();
        this.airports.addAll(airports);
        updateView();
    }

    /**
     * Remove all airports.
     * This function automatically calls updateView().
     */
    public void clearAirports() {
        airports.clear();
        updateView();
    }

    /**
     * Remove airport from param.
     * Need to call updateView() after remove.
     *
     * @param airport Airport to remove from view
     */
    public void removeAirport(Airport airport) {
        airports.remove(airport);
    }

    /**
     * Update view according to airports.
     */
    public abstract void updateView();
}
