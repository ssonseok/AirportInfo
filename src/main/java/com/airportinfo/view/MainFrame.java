package com.airportinfo.view;

import com.airportinfo.swing.LocalizedOptionPane;
import com.airportinfo.util.FontManager;
import com.airportinfo.util.ThemeManager;
import com.airportinfo.util.Translator;
import com.airportinfo.view.content.ContentView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

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
    private final ArrayList<ContentChangeListener> contentChangeListeners = new ArrayList<>();
    protected final JFrame frame = new JFrame();
    protected ContentView currentContentView = null;

    public MainFrame() {
        Locale.setDefault(Locale.KOREAN);
        setTheme(AppTheme.Lite);
        frame.setTitle("Main");
        frame.setSize(DEFAULT_SIZE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowCloseListener());
    }

    public MainFrame(String title) {
        Locale.setDefault(Locale.KOREAN);
        setTheme(AppTheme.Lite);
        frame.setTitle(title);
        frame.setSize(DEFAULT_SIZE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowCloseListener());
    }

    public MainFrame(String title, int width, int height) {
        Locale.setDefault(Locale.KOREAN);
        setTheme(AppTheme.Lite);
        frame.setTitle(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowCloseListener());
    }

    public abstract void load();

    protected abstract void destroy();

    /**
     * Add a new contentView with name.
     *
     * @param name        ContentView name
     * @param contentView ContentView
     */
    public void addContentView(String name, ContentView contentView) {
        contentViewHashMap.put(name, contentView);
        contentView.onThemeChange(themeManager.currentTheme);
        contentView.onLocaleChange(Locale.getDefault());
    }

    /**
     * Set ContentView with name.
     *
     * @param name ContentView name
     */
    public void setContentView(String name) {
        currentContentView = contentViewHashMap.get(name);
        if (currentContentView == null)
            throw new IllegalArgumentException();

        changeContent(currentContentView.getPanel());
        currentContentView.load();
        for (ContentChangeListener contentChangeListener : contentChangeListeners)
            contentChangeListener.onContentChange(currentContentView);

        frame.revalidate();
        frame.repaint();
    }

    public void addContentChangeListener(ContentChangeListener listener) {
        contentChangeListeners.add(listener);
    }

    public void removeContentChangeListener(ContentChangeListener listener) {
        contentChangeListeners.remove(listener);
    }

    /**
     * Check following content view is current displaying.
     *
     * @param contentView Content view to check
     * @return True if following content view is equal to current content view
     */
    public boolean isDisplaying(ContentView contentView) {
        return Objects.equals(currentContentView, contentView);
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
            onThemeChange(theme);
            for (ContentView contentView : contentViewHashMap.values())
                contentView.onThemeChange(theme);
        } catch (UnsupportedLookAndFeelException e) {
            String title = Translator.getBundleString("error");
            JOptionPane.showMessageDialog(frame, e.getMessage(), title, JOptionPane.ERROR_MESSAGE);
        } catch (IOException | FontFormatException e) {
            LocalizedOptionPane.showErrorMessageDialog(frame, "font_load_fail");
        }
    }

    /**
     * Show frame center of screen.
     */
    public void showFrame() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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
        if (Locale.getDefault().equals(locale))
            return;
        Locale.setDefault(locale);
        for (ContentView contentView : contentViewHashMap.values())
            contentView.onLocaleChange(locale);
        onLocaleChange(locale);
    }

    private class WindowCloseListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            destroy();
        }
    }
}
