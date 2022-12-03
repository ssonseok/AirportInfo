package com.airportinfo.view;

import com.airportinfo.controller.AirportController;
import com.airportinfo.controller.UserController;
import com.airportinfo.misc.AirportListCellRenderer;
import com.airportinfo.model.Airport;
import com.airportinfo.util.ThemeManager;
import com.airportinfo.util.Translator;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

/**
 * Sidebar of AirportInfo main frame.
 *
 * @author lalaalal
 */
public class AirportSidebar extends ComponentView {
    private JPanel panel;
    private JLabel recentLabel;
    private JLabel bookmarkLabel;
    private JList<Airport> airportList;
    private JPanel recentPanel;
    private JPanel bookmarkPanel;
    private final DefaultListModel<Airport> airportListModel = new DefaultListModel<>();
    private final UserController userController;
    private final ThemeManager themeManager = ThemeManager.getInstance();
    private Tab currentTab = Tab.Recent;

    public AirportSidebar(AirportFrame airportFrame) {
        this.userController = airportFrame.getUserController();
        AirportController airportController = airportFrame.getAirportController();

        $$$setupUI$$$();
        recentPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectTab(Tab.Recent);
            }
        });
        recentPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bookmarkPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectTab(Tab.Bookmark);
            }
        });
        bookmarkPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        airportList.addListSelectionListener((event) -> {
            Airport selectedAirport = airportList.getSelectedValue();
            if (event.getValueIsAdjusting() && selectedAirport != null) {
                airportController.selectAirport(selectedAirport);
                airportFrame.setContentView(AirportFrame.AIRPORT_DETAIL_VIEW);
            }
        });

        userController.attach(() -> {
            if (currentTab == Tab.Bookmark)
                loadBookmark();
        }, UserController.BOOKMARK_CHANGE);

        themeManager.addColor(AppTheme.Lite, "Tab.background", Color.decode("#494949"));
        themeManager.addColor(AppTheme.Lite, "Tab.foreground", ThemeManager.getDefaultColor(ThemeManager.LITE_THEME, "Label.background"));
        themeManager.addColor(AppTheme.Lite, "Tab.selectionBackground", Color.decode("#393939"));
        themeManager.addColor(AppTheme.Dark, "Tab.background", Color.decode("#32424A"));
        themeManager.addColor(AppTheme.Dark, "Tab.foreground", ThemeManager.getDefaultColor(ThemeManager.DARK_THEME, "Label.foreground"));
        themeManager.addColor(AppTheme.Dark, "Tab.selectionBackground", Color.decode("#263238"));

        recentLabel.setForeground(themeManager.getColor("Tab.foreground"));
        bookmarkLabel.setForeground(themeManager.getColor("Tab.foreground"));

        addListeners();

        selectTab(currentTab);
    }

    private void addListeners() {
        addThemeChangeListener(theme -> {
            recentLabel.setForeground(themeManager.getColor("Tab.foreground"));
            bookmarkLabel.setForeground(themeManager.getColor("Tab.foreground"));
            selectTab(currentTab);
        });

        addLocaleChangeListener(locale -> {
            recentLabel.setText(Translator.getBundleString("recent_search"));
            bookmarkLabel.setText(Translator.getBundleString("bookmark"));
            selectTab(currentTab);
        });
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    private void loadRecent() {
        airportListModel.removeAllElements();
        // TODO : Implement after UserController support history.
        if (airportListModel.getSize() == 0)
            airportListModel.addElement(null);
    }

    private void loadBookmark() {
        airportListModel.removeAllElements();
        airportListModel.addAll(userController.getAllBookmark());
        if (airportListModel.getSize() == 0)
            airportListModel.addElement(null);
    }

    public void selectTab(Tab tab) {
        currentTab = tab;
        switch (tab) {
            case Recent -> {
                recentPanel.setBackground(themeManager.getColor("Tab.selectionBackground"));
                bookmarkPanel.setBackground(themeManager.getColor("Tab.background"));
                loadRecent();
            }
            case Bookmark -> {
                bookmarkPanel.setBackground(themeManager.getColor("Tab.selectionBackground"));
                recentPanel.setBackground(themeManager.getColor("Tab.background"));
                loadBookmark();
            }
        }
    }

    private void createUIComponents() {
        airportList = new JList<>(airportListModel);
        airportList.setFixedCellHeight(50);
        airportList.setCellRenderer(new AirportListCellRenderer());
    }

    public enum Tab {
        Recent, Bookmark
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panel = new JPanel();
        panel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), 0, -1));
        panel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(250, -1), null, null, 0, false));
        recentPanel = new JPanel();
        recentPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(recentPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        recentPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        recentLabel = new JLabel();
        this.$$$loadLabelText$$$(recentLabel, this.$$$getMessageFromBundle$$$("string", "recent_search"));
        recentPanel.add(recentLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bookmarkPanel = new JPanel();
        bookmarkPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(bookmarkPanel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        bookmarkPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        bookmarkLabel = new JLabel();
        this.$$$loadLabelText$$$(bookmarkLabel, this.$$$getMessageFromBundle$$$("string", "bookmark"));
        bookmarkPanel.add(bookmarkLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel.add(scrollPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setViewportView(airportList);
    }

    private static Method $$$cachedGetBundleMethod$$$ = null;

    private String $$$getMessageFromBundle$$$(String path, String key) {
        ResourceBundle bundle;
        try {
            Class<?> thisClass = this.getClass();
            if ($$$cachedGetBundleMethod$$$ == null) {
                Class<?> dynamicBundleClass = thisClass.getClassLoader().loadClass("com.intellij.DynamicBundle");
                $$$cachedGetBundleMethod$$$ = dynamicBundleClass.getMethod("getBundle", String.class, Class.class);
            }
            bundle = (ResourceBundle) $$$cachedGetBundleMethod$$$.invoke(null, path, thisClass);
        } catch (Exception e) {
            bundle = ResourceBundle.getBundle(path);
        }
        return bundle.getString(key);
    }

    /**
     * @noinspection ALL
     */
    private void $$$loadLabelText$$$(JLabel component, String text) {
        StringBuffer result = new StringBuffer();
        boolean haveMnemonic = false;
        char mnemonic = '\0';
        int mnemonicIndex = -1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '&') {
                i++;
                if (i == text.length()) break;
                if (!haveMnemonic && text.charAt(i) != '&') {
                    haveMnemonic = true;
                    mnemonic = text.charAt(i);
                    mnemonicIndex = result.length();
                }
            }
            result.append(text.charAt(i));
        }
        component.setText(result.toString());
        if (haveMnemonic) {
            component.setDisplayedMnemonic(mnemonic);
            component.setDisplayedMnemonicIndex(mnemonicIndex);
        }
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }

}
