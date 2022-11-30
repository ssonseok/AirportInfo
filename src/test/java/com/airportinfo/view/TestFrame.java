package com.airportinfo.view;

import com.airportinfo.util.FontManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class TestFrame extends MainFrame {
    public TestFrame() {
        super("Test", 700, 700);
        try {
            FontManager.loadFont();
        } catch (FontFormatException | IOException e) {
            JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    @Override
    protected void changeContent(JPanel content) {
        setContentPane(content);
    }
}
