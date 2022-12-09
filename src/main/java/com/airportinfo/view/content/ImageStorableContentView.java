package com.airportinfo.view.content;

import com.airportinfo.util.Screenshot;
import com.airportinfo.util.Translator;
import com.airportinfo.view.MainFrame;
import com.airportinfo.view.Storable;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class ImageStorableContentView extends ContentView implements Storable {
    public ImageStorableContentView(MainFrame mainFrame) {
        super(mainFrame);
    }

    public void takeScreenshot(File file, JPanel screenshotPanel, Rectangle bounds) {
        try {
            Screenshot.createScreenshot(screenshotPanel, bounds, file.getPath());
        } catch (IOException e) {
            String title = Translator.getBundleString("error");
            String message = Translator.getBundleString("cannot_store");
            JOptionPane.showMessageDialog(mainFrame.getPanel(), message, title, JOptionPane.ERROR_MESSAGE);
        }
    }

    public void takeScreenshot(File file, JPanel screenshotPanel, Dimension dimension) {
        takeScreenshot(file, screenshotPanel, new Rectangle(dimension));
    }
}
