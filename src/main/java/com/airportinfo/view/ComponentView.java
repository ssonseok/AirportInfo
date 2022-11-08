package com.airportinfo.view;

import javax.swing.*;
import java.util.ArrayList;

/**
 * ComponentView is able to get JPanel.
 *
 * @author lalaalal
 */
public abstract class ComponentView {
    private final ArrayList<LocaleChangeListener> localeChangeListeners = new ArrayList<>();

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
    public void onLocaleChange() {
        for (LocaleChangeListener localeChangeListener : localeChangeListeners)
            localeChangeListener.onLocaleChange();
    }
}
