package com.airportinfo.view;

import java.util.Locale;

/**
 * Do some action when locale changes.
 *
 * @author lalaalal
 */
public interface LocaleChangeListener {
    /**
     * Update text when locale changes.
     */
    void onLocaleChange(Locale locale);
}
