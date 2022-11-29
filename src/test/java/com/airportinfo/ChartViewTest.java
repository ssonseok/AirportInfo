package com.airportinfo;

import com.airportinfo.util.FontManager;
import com.airportinfo.view.MainFrame;
import com.airportinfo.view.TestFrame;

import java.awt.*;
import java.io.IOException;

public class ChartViewTest {
    public static void main(String[] args) {
        try {
            Main.setSystemProperties();
            MainFrame mainFrame = new TestFrame();
            FontManager.loadFont();
            mainFrame.addContentView("ChartContentView", new ChartContentView(mainFrame));
            mainFrame.setContentView("ChartContentView");
            
            mainFrame.setVisible(true);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}
