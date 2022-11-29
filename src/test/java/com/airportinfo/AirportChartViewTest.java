package com.airportinfo;

import com.airportinfo.controller.AirportController;
import com.airportinfo.util.FontManager;
import com.airportinfo.view.MainFrame;
import com.airportinfo.view.TestContentView;
import com.airportinfo.view.TestFrame;
import com.airportinfo.view.airport.AirportChartView;
import com.airportinfo.view.airport.AttributeStatisticCreator;
import com.airportinfo.view.chart.HistogramView;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class AirportChartViewTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, FontFormatException {
        AirportController airportController = new AirportController();
        airportController.loadFromDB();

        MainFrame mainFrame = new TestFrame();
        FontManager.loadFont();
        TestContentView contentView = new TestContentView();
        AirportChartView airportChartView = new AirportChartView(new HistogramView(), new AttributeStatisticCreator(Airport::getRegion));
        contentView.setPanel(airportChartView.getPanel());
        airportChartView.setAirports(airportController.getAirports());

        mainFrame.addContentView("AirportChartView", contentView);
        mainFrame.setContentView("AirportChartView");

        mainFrame.setVisible(true);
    }
}
