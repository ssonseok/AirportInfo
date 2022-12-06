package com.airportinfo.view;

import com.airportinfo.controller.AirportController;
import com.airportinfo.view.airport.AirportDetailView;

import java.sql.SQLException;

public class AirportDetailViewTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, NoSuchMethodException {
        AirportController airportController = new AirportController();
        airportController.loadFromDB();

        MainFrame mainFrame = new TestFrame();

        TestContentView contentView = new TestContentView(mainFrame);
        AirportDetailView airportDetailView = new AirportDetailView();
        airportDetailView.setAirport(airportController.getAirports()[0]);
        contentView.setComponent(airportDetailView);

        mainFrame.addContentView("AirportDetailView", contentView);
        mainFrame.setContentView("AirportDetailView");
        mainFrame.showFrame();
    }
}
