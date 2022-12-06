package com.airportinfo.view.content;

import com.airportinfo.view.ComponentGroup;
import com.airportinfo.view.MainFrame;

/**
 * ContentView is able to be a ContentPane of MainFrame.
 * Also contains ComponentViews.
 *
 * @author lalaalal
 */
public abstract class ContentView extends ComponentGroup {
    protected final MainFrame mainFrame;

    public ContentView(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    /**
     * Call load when change contentPane of Frame to ContentView.
     */
    public abstract void load();

    /**
     * Check this content view is displaying.
     *
     * @return True if main frame is displaying this view
     */
    public boolean isDisplaying() {
        return mainFrame.isDisplaying(this);
    }
}
