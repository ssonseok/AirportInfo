package com.airportinfo.view.content;

import com.airportinfo.view.ComponentGroup;

/**
 * ContentView is able to be a ContentPane of MainFrame.
 * Also contains ComponentViews.
 *
 * @author lalaalal
 */
public abstract class ContentView extends ComponentGroup {
    /**
     * Call load when change contentPane of Frame to ContentView.
     */
    public abstract void load();
}
