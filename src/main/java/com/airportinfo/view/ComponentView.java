package com.airportinfo.view;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Locale;

/**
 * ComponentView is able to get JPanel.
 *
 * @author lalaalal
 */
public abstract class ComponentView {
    private final ArrayList<LocaleChangeListener> localeChangeListeners = new ArrayList<>();
    private final ArrayList<ThemeChangeListener> themeChangeListeners = new ArrayList<>();

    /**
     * Get root panel of component.
     *
     * @return Root panel
     */
    public abstract JPanel getPanel();

    /**
     * Add a new LocaleChangeListener.
     *
     * @param localeChangeListener New LocaleChangeListener
     */
    public void addLocaleChangeListener(LocaleChangeListener localeChangeListener) {
        localeChangeListeners.add(localeChangeListener);
    }

    /**
     * Do all LocaleChangeListeners.
     */
    public void onLocaleChange(Locale locale) {
        for (LocaleChangeListener localeChangeListener : localeChangeListeners)
            localeChangeListener.onLocaleChange(locale);
    }

    /**
     * Add a new ThemeChangeListener.
     *
     * @param themeChangeListener New ThemeChangeListener
     */
    public void addThemeChangeListener(ThemeChangeListener themeChangeListener) {
        themeChangeListeners.add(themeChangeListener);
    }

    protected void actionBeforeUIUpdate(AppTheme theme) {

    }

    /**
     * Do all ThemeChangeListeners.
     */
    public void onThemeChange(AppTheme theme) {
        actionBeforeUIUpdate(theme);
        JPanel panel = getPanel();
        if (panel != null)
            SwingUtilities.updateComponentTreeUI(panel);
        for (ThemeChangeListener themeChangeListener : themeChangeListeners)
            themeChangeListener.onThemeChange(theme);
    }
}
