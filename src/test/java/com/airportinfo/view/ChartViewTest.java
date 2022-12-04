package com.airportinfo.view;

import com.airportinfo.Main;

public class ChartViewTest {
    public static void main(String[] args) {
        Main.setSystemProperties();
        MainFrame mainFrame = new TestFrame();
        mainFrame.addContentView("ChartContentView", new ChartContentView(mainFrame));
        mainFrame.setContentView("ChartContentView");

        mainFrame.showFrame();
    }
}
