package com.airportinfo.view;

import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialLiteTheme;
import mdlaf.themes.MaterialOceanicTheme;

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
    private static final LookAndFeel LITE_THEME = new MaterialLookAndFeel(new MaterialLiteTheme());
    private static final LookAndFeel DARK_THEME = new MaterialLookAndFeel(new MaterialOceanicTheme());
    private AppTheme theme = AppTheme.Lite;
    private static final Dimension DEFAULT_SIZE = new Dimension(700, 500);
    private final HashMap<String, ContentView> contentViewHashMap = new HashMap<>();

    public MainFrame() {
        setTitle("Main");
        setSize(DEFAULT_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTheme(AppTheme.Lite);
    }

    public MainFrame(String title) {
        setTitle(title);
        setSize(DEFAULT_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTheme(AppTheme.Lite);
    }

    public MainFrame(String title, int width, int height) {
        setTitle(title);
        setSize(width, height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTheme(AppTheme.Lite);
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

    /**
     * Change Application theme.
     *
     * @param theme Theme to change
     */
    public void setTheme(AppTheme theme) {
        try {
            this.theme = theme;
            if (theme == AppTheme.Lite)
                UIManager.setLookAndFeel(LITE_THEME);
            else
                UIManager.setLookAndFeel(DARK_THEME);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Toggle theme between lite and dark.
     */
    public void toggleTheme() {
        if (theme == AppTheme.Lite)
            setTheme(AppTheme.Dark);
        else
            setTheme(AppTheme.Lite);
    }
}
