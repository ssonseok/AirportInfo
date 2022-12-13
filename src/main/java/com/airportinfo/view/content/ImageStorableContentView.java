package com.airportinfo.view.content;

import com.airportinfo.swing.CautiousFileChooser;
import com.airportinfo.util.Screenshot;
import com.airportinfo.util.Translator;
import com.airportinfo.view.MainFrame;
import com.airportinfo.view.Storable;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * A ContentView can store image.
 *
 * @author lalaalal
 */
public abstract class ImageStorableContentView extends ContentView implements Storable {
    @Override
    public void store() {
        CautiousFileChooser fileChooser = new CautiousFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("png", "png"));
        fileChooser.showFileChooser(mainFrame.getPanel(), this::store);
    }

    public ImageStorableContentView(MainFrame mainFrame) {
        super(mainFrame);
    }

    /**
     * Take a screenshot with panel and its bounds.
     *
     * @param file            File to save
     * @param screenshotPanel Panel to take screenshot
     * @param bounds          Bounds of panel
     */
    public void takeScreenshot(File file, JPanel screenshotPanel, Rectangle bounds) {
        try {
            Screenshot.createScreenshot(screenshotPanel, bounds, file.getPath());
        } catch (IOException e) {
            String title = Translator.getBundleString("error");
            String message = Translator.getBundleString("cannot_store");
            JOptionPane.showMessageDialog(mainFrame.getPanel(), message, title, JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Take a screenshot with panel and its dimension.
     *
     * @param file            File to save
     * @param screenshotPanel Panel to take screenshot
     * @param dimension       Dimension of panel
     */
    public void takeScreenshot(File file, JPanel screenshotPanel, Dimension dimension) {
        takeScreenshot(file, screenshotPanel, new Rectangle(dimension));
    }
}
