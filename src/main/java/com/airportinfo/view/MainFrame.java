package com.airportinfo.view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * An abstract JFrame which is able to change ContentView set before.
 * Implement changeContent(JPanel content) to set content area.
 *
 * @author lalaalal
 */
public abstract class MainFrame extends JFrame {
    private static final Dimension DEFAULT_SIZE = new Dimension(700, 500);
    private final HashMap<String, ContentView> contentViewHashMap = new HashMap<>();

    public MainFrame() {
        setTitle("Main");
        setSize(DEFAULT_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public MainFrame(String title) {
        setTitle(title);
        setSize(DEFAULT_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public MainFrame(String title, int width, int height) {
        setTitle(title);
        setSize(width, height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Add a new contentView with name.
     *
     * @param name        ContentView name
     * @param contentView ContentView
     */
    public void addContentView(String name, ContentView contentView) {
        contentViewHashMap.put(name, contentView);
    }

    /**
     * Set ContentView with name.
     *
     * @param name ContentView name
     */
    public void setContentView(String name) {
        ContentView contentView = contentViewHashMap.get(name);
        if (contentView == null)
            throw new IllegalArgumentException();

        contentView.load();
        changeContent(contentView.getPanel());

        revalidate();
        repaint();
    }

    /**
     * Change content with JPanel.
     * This function is called by setContentView(String name) after load ContentView.
     *
     * @param content JPanel replacing content
     */
    protected abstract void changeContent(JPanel content);
}
