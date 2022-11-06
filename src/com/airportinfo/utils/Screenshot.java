package com.airportinfo.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

public class Screenshot {
    /** *
     * Take a screenshot with component to png
     * @param component - screenshot area
     * @param path - save path
     * @throws IOException - exception while opening output stream
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
