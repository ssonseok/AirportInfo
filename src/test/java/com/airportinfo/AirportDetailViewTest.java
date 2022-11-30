package com.airportinfo;

import com.airportinfo.controller.AirportController;
import com.airportinfo.view.MainFrame;
import com.airportinfo.view.TestContentView;
import com.airportinfo.view.TestFrame;
import com.airportinfo.view.airport.AirportDetailView;

import java.sql.SQLException;

public class AirportDetailViewTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, NoSuchMethodException {
        AirportController airportController = new AirportController();
        airportController.loadFromDB();

        MainFrame mainFrame = new TestFrame();

        TestContentView contentView = new TestContentView();
        AirportDetailView airportDetailView = new AirportDetailView();
        airportDetailView.setAirport(airportController.getAirports().get(0));
        contentView.setPanel(airportDetailView.getPanel());

        mainFrame.addContentView("AirportDetailView", contentView);
        mainFrame.setContentView("AirportDetailView");
        mainFrame.setVisible(true);
    }
}
