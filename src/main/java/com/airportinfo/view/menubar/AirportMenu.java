package com.airportinfo.view.menubar;

import com.airportinfo.util.ThemeManager;

import javax.swing.*;

/**
 * Change source color when mouse hover.
 *
 * @author lalaalal
 */
public class AirportMenu extends JMenu {
    public AirportMenu(String text) {
        super(text);
    }

    @Override
    public JPopupMenu getPopupMenu() {
        ThemeManager themeManager = ThemeManager.getInstance();
        JPopupMenu popupMenu = super.getPopupMenu();
        popupMenu.setBorder(BorderFactory.createLineBorder(themeManager.getColor("MenuItem.hoverBackground")));
        return popupMenu;
    }
}
