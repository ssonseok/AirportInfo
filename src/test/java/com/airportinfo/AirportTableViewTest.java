package com.airportinfo;

import com.airportinfo.controller.AirportController;
import com.airportinfo.util.FontManager;
import com.airportinfo.view.MainFrame;
import com.airportinfo.view.TestContentView;
import com.airportinfo.view.TestFrame;
import com.airportinfo.view.airport.AirportTableView;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class AirportTableViewTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, FontFormatException {
        AirportController airportController = new AirportController();
        airportController.loadFromDB();

        MainFrame mainFrame = new TestFrame();
        FontManager.loadFont();
        TestContentView contentView = new TestContentView();
        AirportTableView airportTableView = new AirportTableView();
        airportTableView.setAirports(airportController.getAirports());

        contentView.setPanel(airportTableView.getPanel());

        mainFrame.addContentView("AirportTableView", contentView);
        mainFrame.setContentView("AirportTableView");

        mainFrame.setVisible(true);
    }
}
