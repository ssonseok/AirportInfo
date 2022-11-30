package com.airportinfo;

import com.airportinfo.controller.AirportController;
import com.airportinfo.view.MainFrame;
import com.airportinfo.view.TestContentView;
import com.airportinfo.view.TestFrame;
import com.airportinfo.view.airport.AirportTableView;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

public class AirportTableViewTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, NoSuchMethodException {
        Locale.setDefault(Locale.KOREAN);
        AirportController airportController = new AirportController();
        airportController.loadFromDB();

        MainFrame mainFrame = new TestFrame();
        TestContentView contentView = new TestContentView();
        AirportTableView airportTableView = new AirportTableView();
        airportTableView.setAirports(List.of(airportController.getAirports()));

        contentView.setPanel(airportTableView.getPanel());

        mainFrame.addContentView("AirportTableView", contentView);
        mainFrame.setContentView("AirportTableView");

        mainFrame.setVisible(true);
    }
}
