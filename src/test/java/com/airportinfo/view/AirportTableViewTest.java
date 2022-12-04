package com.airportinfo.view;

import com.airportinfo.controller.AirportController;
import com.airportinfo.model.Airport;
import com.airportinfo.view.airport.AirportTableView;

import java.sql.SQLException;
import java.util.List;

public class AirportTableViewTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, NoSuchMethodException {
        AirportController airportController = new AirportController();
        airportController.loadFromDB();
        MainFrame mainFrame = new AirportFrame();
        mainFrame.showFrame();
        TestContentView contentView = new TestContentView();
        AirportTableView airportTableView = new AirportTableView();
        contentView.setPanel(airportTableView);

        mainFrame.addContentView("AirportTableView", contentView);
        mainFrame.setContentView("AirportTableView");

        airportTableView.setAirports(List.of(airportController.getAirports()));
        airportTableView.addMouseClickAction((mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2) {
                Airport selected = airportTableView.getSelectedAirport();
                System.out.println(selected);
            }
        });
    }
}
