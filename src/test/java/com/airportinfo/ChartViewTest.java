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
        try (InputStream is = ChartViewTest.class.getClassLoader().getResourceAsStream("font/SCDream4.otf")) {
            UIManager.setLookAndFeel(new MaterialLookAndFeel());
            if (is == null)
                throw new IOException("Font not found");
            Font font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(12f);
            FontManager.setUIFont(new FontUIResource(font));

            MainFrame mainFrame = new MainFrame() {
                @Override
                protected void changeContent(JPanel content) {
                    setContentPane(content);
                }
            };
            mainFrame.addContentView("ChartContentView", new ChartContentView());
            mainFrame.setContentView("ChartContentView");

            mainFrame.setVisible(true);
        } catch (IOException | UnsupportedLookAndFeelException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}
