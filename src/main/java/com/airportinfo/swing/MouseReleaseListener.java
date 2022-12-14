package com.airportinfo.swing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

/**
 * MouseListener handle mouse released.
 *
 * @author lalaalal
 */
public class MouseReleaseListener extends MouseAdapter {
    private final Consumer<MouseEvent> consumer;

    public MouseReleaseListener(Consumer<MouseEvent> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        consumer.accept(e);
    }
}
