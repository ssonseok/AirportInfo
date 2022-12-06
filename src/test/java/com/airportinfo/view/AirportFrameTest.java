package com.airportinfo.view;

import com.airportinfo.Main;
import com.airportinfo.controller.AirportController;
import com.airportinfo.controller.UserController;

public class AirportFrameTest {
    public static void main(String[] args) {
        Main.setSystemProperties();
        AirportFrame airportFrame = new AirportFrame();

        airportFrame.showFrame();
        airportFrame.load();

        AirportController airportController = airportFrame.getAirportController();
        UserController userController = airportFrame.getUserController();

        for (int i = 0; i < 20; i++) {
            userController.addBookmark(airportController.getAirports()[i]);
        }
        airportFrame.addContentView("test", new ChartContentView(airportFrame));
        airportFrame.setContentView("test");
    }
}