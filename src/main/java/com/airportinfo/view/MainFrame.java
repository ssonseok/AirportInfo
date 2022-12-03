package com.airportinfo.view;

import com.airportinfo.util.FontManager;
import com.airportinfo.util.ThemeManager;
import com.airportinfo.util.Translator;
import com.airportinfo.view.content.ContentView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

/**
 * An abstract JFrame which is able to change ContentView set before.
 * Implement changeContent(JPanel content) to set content area.
 *
 * @author lalaalal
 */
public abstract class MainFrame extends ComponentGroup {
    private final ThemeManager themeManager = ThemeManager.getInstance();
    private static final Dimension DEFAULT_SIZE = new Dimension(700, 500);
    private final HashMap<String, ContentView> contentViewHashMap = new HashMap<>();
    protected final JFrame frame = new JFrame();

    public MainFrame() {
        frame.setTitle("Main");
        frame.setSize(DEFAULT_SIZE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTheme(AppTheme.Lite);
        Locale.setDefault(Locale.KOREAN);
    }

    public MainFrame(String title) {
        frame.setTitle(title);
        frame.setSize(DEFAULT_SIZE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTheme(AppTheme.Lite);
        Locale.setDefault(Locale.KOREAN);
    }

    public MainFrame(String title, int width, int height) {
        frame.setTitle(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTheme(AppTheme.Lite);
        Locale.setDefault(Locale.KOREAN);
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

        frame.revalidate();
        frame.repaint();
    }

    /**
     * Change content with JPanel.
     * This function is called by setContentView(String name) after load ContentView.
     *
     * @param content JPanel replacing content
     */
    protected abstract void changeContent(JPanel content);

    /**
     * Change Application theme. If you have another component in MainFrame, override and call ohThemeChange.
     *
     * @param theme Theme to change
     */
    public void setTheme(AppTheme theme) {
        try {
            themeManager.currentTheme = theme;
            if (theme == AppTheme.Lite)
                UIManager.setLookAndFeel(ThemeManager.LITE_THEME);
            else
                UIManager.setLookAndFeel(ThemeManager.DARK_THEME);
            FontManager.loadFont();
            for (ContentView contentView : contentViewHashMap.values())
                contentView.onThemeChange(theme);
        } catch (UnsupportedLookAndFeelException e) {
            String title = Translator.getBundleString("error");
            JOptionPane.showMessageDialog(frame, e.getMessage(), title, JOptionPane.ERROR_MESSAGE);
        } catch (IOException | FontFormatException e) {
            String title = Translator.getBundleString("error");
            String message = Translator.getBundleString("font_load_fail");
            JOptionPane.showMessageDialog(frame, message, title, JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Wrapper of JFrame.setVisible(boolean).
     *
     * @param value Boolean value to set frame visible.
     */
    public void setVisible(boolean value) {
        frame.setVisible(value);
    }

    /**
     * Toggle theme between lite and dark.
     */
    public void toggleTheme() {
        if (themeManager.currentTheme == AppTheme.Lite)
            setTheme(AppTheme.Dark);
        else
            setTheme(AppTheme.Lite);
    }

    /**
     * Change Locale and call onLocaleChange from ContentViews.
     *
     * @param locale Locale to change
     */
    public void changeLocale(Locale locale) {
        Locale.setDefault(locale);
        for (ContentView contentView : contentViewHashMap.values())
            contentView.onLocaleChange(locale);
    }
}
