package com.airportinfo;

import com.airportinfo.utils.FontManager;
import com.airportinfo.view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ChartViewTest {
    public static void main(String[] args) {
        try {
            Main.setSystemProperties();
            MainFrame mainFrame = new MainFrame("Main", 700, 700) {
                @Override
                protected void changeContent(JPanel content) {
                    setContentPane(content);
                }
            };
            FontManager.loadFont();
            mainFrame.addContentView("ChartContentView", new ChartContentView(mainFrame));
            mainFrame.setContentView("ChartContentView");
            
            mainFrame.setVisible(true);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}
