package com.airportinfo;

import com.airportinfo.view.AirportFrame;

public class Main {
    public static void setSystemProperties() {
        System.setProperty("awt.useSystemAAFontSettings", "on");
    }

    public static void main(String[] args) {
        setSystemProperties();
        AirportFrame airportFrame = new AirportFrame();
        airportFrame.showFrame();
        airportFrame.load();
    }
}