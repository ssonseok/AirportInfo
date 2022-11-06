package com.airportinfo.view;

/** *
 * ContentView is able to setContentPane of MainFrame
 */
public interface ContentView extends ComponentView {
    /** *
     * Call load when change contentPane of Frame to ContentView
     */
    void load();
}
