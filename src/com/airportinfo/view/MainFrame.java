package com.airportinfo.view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public abstract class MainFrame extends JFrame {
    private final HashMap<String, ContentView> contentViewHashMap = new HashMap<>();
    private static final Dimension DEFAULT_SIZE = new Dimension(700, 500);

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

    public void addContentView(String name, ContentView contentView) {
        contentViewHashMap.put(name, contentView);
    }

    public void setContentView(String name) {
        ContentView contentView = contentViewHashMap.get(name);
        if (contentView == null)
            throw new IllegalArgumentException();

        contentView.load();
        changeContent(contentView.getPanel());
    }

    protected abstract void changeContent(JPanel content);
}
