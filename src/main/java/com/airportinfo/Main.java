package com.airportinfo;

import com.airportinfo.view.AirportFrame;
import com.airportinfo.view.content.AirportDetailContentView;
import com.airportinfo.view.content.AirportSearchContentView;

public class Main {
    public static void setSystemProperties() {
        System.setProperty("awt.useSystemAAFontSettings", "on");
    }

    public static void main(String[] args) {
        setSystemProperties();
        AirportFrame airportFrame = new AirportFrame();
        airportFrame.showFrame();
        airportFrame.load();

        AirportSearchContentView airportSearchContentView = new AirportSearchContentView(airportFrame);
        AirportDetailContentView airportDetailContentView = new AirportDetailContentView(airportFrame);

        airportFrame.addContentView(AirportFrame.AIRPORT_SEARCH_VIEW, airportSearchContentView);
        airportFrame.addContentView(AirportFrame.AIRPORT_DETAIL_VIEW, airportDetailContentView);

        airportFrame.setContentView(AirportFrame.AIRPORT_SEARCH_VIEW);
    }
}