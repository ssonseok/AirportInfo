package com.airportinfo.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Provide a method to take screenshot.
 *
 * @author lalaalal
 */
public class Screenshot {
    /**
     * Take a screenshot with component to png.
     *
     * @param component Screenshot area
     * @param path      Save path
     * @throws IOException If the file exists but is a directory, does not exist but cannot be created or an error occurs during writing
     */
    public static void createScreenshot(Component component, String path) throws IOException {
        createScreenshot(component, component.getBounds(), path);
    }

    /**
     * Take a screenshot with component to png.
     *
     * @param component Screenshot area
     * @param path      Save path
     * @param bounds    Size to paint component
     * @throws IOException If the file exists but is a directory, does not exist but cannot be created or an error occurs during writing
     */
    public static void createScreenshot(Component component, Rectangle bounds, String path) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(bounds.width, bounds.height, BufferedImage.TYPE_INT_ARGB);
        component.paint(bufferedImage.getGraphics());

        if (!path.endsWith(".png"))
            path = path + ".png";
        try (FileOutputStream outputStream = new FileOutputStream(path)) {
            ImageIO.write(bufferedImage, "png", outputStream);
        }
    }
}

