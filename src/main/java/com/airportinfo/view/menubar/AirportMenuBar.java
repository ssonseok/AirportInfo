package com.airportinfo.view.menubar;

import com.airportinfo.model.MouseReleaseListener;
import com.airportinfo.util.ThemeManager;
import com.airportinfo.util.Translator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 * Airport menu bar.
 *
 * @author lalaalal
 */
public class AirportMenuBar extends JMenuBar {
    private final HashMap<String, JMenu> menus = new HashMap<>();
    private final HashMap<String, JMenuItem> items = new HashMap<>();
    private final ArrayList<JSeparator> separators = new ArrayList<>();
    private final ThemeManager themeManager = ThemeManager.getInstance();

    public AirportMenuBar() {
        setBorder(BorderFactory.createEmptyBorder());
    }

    /**
     * Add menu with translation key.
     *
     * @param key Translation key - Key should be defined in string.properties
     */
    public void addMenu(String key) {
        JMenu menu = new AirportMenu(Translator.getBundleString(key));
        menus.put(key, menu);
        add(menu);
        updateTheme();
    }

    /**
     * Add menu item with translation key.
     *
     * @param menuKey Key of menu to insert into
     * @param itemKey Key of menu item to insert
     * @param action  Action for mouse listener of menu item
     * @return Created JMenuItem
     */
    public JMenuItem addMenuItem(String menuKey, String itemKey, Consumer<MouseEvent> action) {
        JMenu menu = menus.get(menuKey);
        if (menu == null)
            throw new IllegalArgumentException("Menu key (" + menuKey + ") doesn't exist.");
        JMenuItem item = new JMenuItem(Translator.getBundleString(itemKey));
        item.setPreferredSize(new Dimension(200, item.getPreferredSize().height));
        if (action != null)
            item.addMouseListener(new MouseReleaseListener(action));
        menu.add(item);
        items.put(itemKey, item);
        updateTheme();

        return item;
    }

    /**
     * Add a separator to popup menu.
     *
     * @param menuKey Key of target menu
     */
    public void addMenuSeparator(String menuKey) {
        JMenu menu = menus.get(menuKey);
        if (menu == null)
            throw new IllegalArgumentException("Menu key (" + menuKey + ") doesn't exist.");
        JSeparator separator = new JSeparator();
        separator.setBackground(themeManager.getColor("MenuBar.background"));
        separator.setForeground(themeManager.getColor("MenuItem.hoverBackground"));
        menu.add(separator);
        separators.add(separator);
    }

    public void updateTheme() {
        UIManager.put("Menu.selectionBackground", themeManager.getColor("MenuItem.hoverBackground"));
        UIManager.put("Menu.selectionForeground", themeManager.getColor("MenuBar.foreground"));
        UIManager.put("MenuItem.selectionBackground", themeManager.getColor("MenuItem.hoverBackground"));
        UIManager.put("MenuItem.selectionForeground", themeManager.getColor("MenuBar.foreground"));
        UIManager.put("MenuItem.shadow", themeManager.getColor("MenuBar.background"));
        UIManager.put("MenuItem.disabledForeground", themeManager.getColor("Custom.disabledForeground"));
        SwingUtilities.updateComponentTreeUI(this);
        setBackground(themeManager.getColor("MenuBar.background"));

        for (JMenu menu : menus.values()) {
            menu.setBackground(themeManager.getColor("MenuBar.background"));
            menu.setForeground(themeManager.getColor("MenuBar.foreground"));
        }
        for (JMenuItem item : items.values()) {
            item.setBackground(themeManager.getColor("MenuBar.background"));
            item.setForeground(themeManager.getColor("MenuBar.foreground"));
        }
        for (JSeparator separator : separators) {
            separator.setBackground(themeManager.getColor("MenuBar.background"));
            separator.setForeground(themeManager.getColor("MenuItem.hoverBackground"));
        }
    }

    public void onLocaleChange() {
        for (String menuKey : menus.keySet()) {
            JMenu menu = menus.get(menuKey);
            menu.setText(Translator.getBundleString(menuKey));
        }

        for (String itemKey : items.keySet()) {
            JMenuItem item = items.get(itemKey);
            item.setText(Translator.getBundleString(itemKey));
        }
    }
}
