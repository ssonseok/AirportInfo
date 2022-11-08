package com.airportinfo.view;

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
