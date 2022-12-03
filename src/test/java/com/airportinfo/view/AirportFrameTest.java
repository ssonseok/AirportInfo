package com.airportinfo.view;

import com.airportinfo.controller.AirportController;
import com.airportinfo.controller.UserController;

public class AirportFrameTest {
    public static void main(String[] args) {
        AirportFrame airportFrame = new AirportFrame();

        airportFrame.setVisible(true);
        airportFrame.load();

        AirportController airportController = airportFrame.getAirportController();
        UserController userController = airportFrame.getUserController();

        for (int i = 0; i < 20; i++) {
            userController.addBookmark(airportController.getAirports()[i]);
        }
        airportFrame.addContentView("test", new TestContentView());
        airportFrame.setContentView("test");
    }
}