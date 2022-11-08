package com.airportinfo.utils;

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
     * @param component screenshot area
     * @param path      save path
     * @throws IOException If the file exists but is a directory, does not exist but cannot be created or an error occurs during writing
     */
    public static void createScreenshot(Component component, String path) throws IOException {
        Rectangle rectangle = component.getBounds();
        BufferedImage bufferedImage = new BufferedImage(rectangle.width, rectangle.height, BufferedImage.TYPE_INT_ARGB);
        component.paint(bufferedImage.getGraphics());

        try (FileOutputStream outputStream = new FileOutputStream(path)) {
            ImageIO.write(bufferedImage, "png", outputStream);
        }
    }
}
