package com.airportinfo;

import com.airportinfo.utils.FontManager;
import com.airportinfo.view.MainFrame;
import mdlaf.MaterialLookAndFeel;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class ChartViewTest {
    public static void main(String[] args) {
        try {
            MainFrame mainFrame = new MainFrame() {
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
