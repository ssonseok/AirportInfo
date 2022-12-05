package com.airportinfo.view;

import com.airportinfo.controller.AirportController;
import com.airportinfo.controller.UserController;
import com.airportinfo.model.Airport;
import com.airportinfo.view.airport.AirportTableView;

import java.util.List;

public class AirportTableViewTest {
    public static void main(String[] args) {
        AirportFrame mainFrame = new AirportFrame();
        mainFrame.showFrame();
        mainFrame.load();
        AirportController airportController = mainFrame.getAirportController();
        UserController userController = mainFrame.getUserController();
        TestContentView contentView = new TestContentView();
        AirportTableView airportTableView = new AirportTableView();
        contentView.setComponent(airportTableView);

        mainFrame.addContentView("AirportTableView", contentView);
        mainFrame.setContentView("AirportTableView");

        airportTableView.setAirports(List.of(airportController.getAirports()));
        airportTableView.addMouseClickAction((mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2) {
                Airport selected = airportTableView.getSelectedAirport();
                userController.addBookmark(selected);
                System.out.println(selected);
            }
        });
    }
}
