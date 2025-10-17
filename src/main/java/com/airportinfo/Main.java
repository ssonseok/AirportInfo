package com.airportinfo;

import com.airportinfo.view.AirportFrame;
import com.airportinfo.view.content.AirportChartContentView;
import com.airportinfo.view.content.AirportDetailContentView;
import com.airportinfo.view.content.AirportSearchContentView;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;

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
        AirportChartContentView airportChartContentView = new AirportChartContentView(airportFrame);

        airportFrame.addContentView(AirportFrame.AIRPORT_SEARCH_VIEW, airportSearchContentView);
        airportFrame.addContentView(AirportFrame.AIRPORT_DETAIL_VIEW, airportDetailContentView);
        airportFrame.addContentView(AirportFrame.AIRPORT_CHART_VIEW, airportChartContentView);

        airportFrame.setContentView(AirportFrame.AIRPORT_SEARCH_VIEW);
    }
}