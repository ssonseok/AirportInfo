package com.airportinfo.view;

import com.airportinfo.view.content.ContentView;

/**
 * When MainFrame changes ContentView.
 *
 * @author lalaalal
 */
public interface ContentChangeListener {
    void onContentChange(ContentView selected);
}
