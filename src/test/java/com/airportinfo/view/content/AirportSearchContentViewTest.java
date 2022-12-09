package com.airportinfo.view.content;

import com.airportinfo.view.AirportFrame;

public class AirportSearchContentViewTest {
    public static void main(String[] args) {
        AirportFrame airportFrame = new AirportFrame();
        airportFrame.showFrame();
        airportFrame.load();

        AirportSearchContentView airportSearchContentView = new AirportSearchContentView(airportFrame);
        airportFrame.addContentView(AirportFrame.AIRPORT_SEARCH_VIEW, airportSearchContentView);
        airportFrame.setContentView(AirportFrame.AIRPORT_SEARCH_VIEW);
    }
}