package com.airportinfo.view.content;

import com.airportinfo.Main;
import com.airportinfo.controller.AirportController;
import com.airportinfo.controller.UserController;
import com.airportinfo.model.Airport;
import com.airportinfo.view.AirportFrame;
import com.airportinfo.view.TestContentView;
import com.airportinfo.view.airport.AirportTableView;

import java.util.List;

public class AirportDetailContentViewTest {
    public static void main(String[] args) {
        Main.setSystemProperties();
        AirportFrame airportFrame = new AirportFrame();
        airportFrame.showFrame();
        airportFrame.load();

        AirportController airportController = airportFrame.getAirportController();
        UserController userController = airportFrame.getUserController();

        AirportDetailContentView airportDetailContentView = new AirportDetailContentView(airportFrame);
        airportFrame.addContentView(AirportFrame.AIRPORT_DETAIL_VIEW, airportDetailContentView);

        TestContentView contentView = new TestContentView(airportFrame);
        AirportTableView airportTableView = new AirportTableView();
        contentView.setComponent(airportTableView);

        airportFrame.addContentView(AirportFrame.AIRPORT_SEARCH_VIEW, contentView);
        airportFrame.setContentView(AirportFrame.AIRPORT_SEARCH_VIEW);

        airportTableView.setAirports(List.of(airportController.getAirports()));
        airportTableView.addMouseClickAction((mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2) {
                Airport selected = airportTableView.getSelectedAirport();
                userController.addRecent(selected);
                airportController.selectAirport(selected);
                airportFrame.setContentView(AirportFrame.AIRPORT_DETAIL_VIEW);
            }
        });
    }
}