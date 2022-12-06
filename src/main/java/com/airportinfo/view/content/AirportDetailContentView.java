package com.airportinfo.view.content;

import com.airportinfo.Setting;
import com.airportinfo.controller.AirportController;
import com.airportinfo.controller.UserController;
import com.airportinfo.model.Airport;
import com.airportinfo.util.*;
import com.airportinfo.view.AirportFrame;
import com.airportinfo.view.AppTheme;
import com.airportinfo.view.airport.AirportDetailView;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.jsoup.HttpStatusException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

public class AirportDetailContentView extends ContentView {
    private JPanel panel;
    private JLabel mapLabel;
    private JLabel airportNameLabel;
    private JLabel imagesHeaderLabel;
    private JPanel imagesPanel;
    private JPanel mapPanel;
    private JTextPane airportInfoTextPane;
    private JPanel airportDetailPanel;
    private JSeparator separator;
    private JScrollPane scrollPane;
    private JButton addBookMarkButton;
    private JButton removeBookMarkButton;
    private final AirportDetailView airportDetailView = new AirportDetailView();
    private final AirportController airportController;
    private Airport selected;

    public AirportDetailContentView(AirportFrame airportFrame) {
        super(airportFrame);
        this.airportController = airportFrame.getAirportController();
        UserController userController = airportFrame.getUserController();
        ThemeManager themeManager = ThemeManager.getInstance();

        $$$setupUI$$$();
        airportNameLabel.setFont(FontManager.getFont(FontManager.HEADER_FONT_SIZE).deriveFont(Font.BOLD));
        airportNameLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        imagesHeaderLabel.setFont(FontManager.getFont(FontManager.HEADER_FONT_SIZE).deriveFont(Font.BOLD));
        imagesHeaderLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        addThemeChangeListener((theme) -> {
            airportInfoTextPane.setBackground(ThemeManager.getDefaultColor("Label.background"));
            separator.setBackground(themeManager.getColor("Separator.background"));
            separator.setForeground(themeManager.getColor("Separator.foreground"));
            if (theme == AppTheme.Lite)
                airportDetailPanel.setBorder(BorderFactory.createLineBorder(themeManager.getColor("Separator.foreground")));
            else
                airportDetailPanel.setBorder(BorderFactory.createEmptyBorder());
        });
        addLocaleChangeListener((locale) -> load());
        Setting.getInstance().attach(this::load, Setting.LOCALIZATION_CHANGE);
        addBookMarkButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                userController.addBookmark(selected);
            }
        });
        removeBookMarkButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                userController.delBookmark(selected);
            }
        });
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void load() {
        if (!isDisplaying())
            return;
        selected = airportController.getSelectedAirport();
        if (selected == null)
            selected = airportController.getAirports()[0];

        airportDetailView.setAirport(selected);
        setLabelsLoading();
        airportNameLabel.setText(selected.getAirportName());
        imagesHeaderLabel.setText(Translator.getBundleString("images"));
        addBookMarkButton.setText(Translator.getBundleString("add_bookmark"));
        imagesPanel.removeAll();
        Thread loadingThread = new Thread(this::loadAirportInformation);
        loadingThread.start();
    }

    public void loadAirportInformation() {
        try {
            AirportWikiCrawler crawler = new AirportWikiCrawler(selected);
            String airportInfo = " " + crawler.getInformation();
            airportInfoTextPane.setText(airportInfo);

            BufferedImage bufferedImage = GoogleMapAPI.getMapImage(selected.getRawData().englishName, mapPanel.getWidth(), mapPanel.getWidth(), 12);
            ImageIcon mapImageIcon = new ImageIcon(bufferedImage);
            mapLabel.setText("");
            mapLabel.setIcon(mapImageIcon);

            for (BufferedImage image : crawler.getBufferedImages(3)) {
                ImageIcon airportImageIcon = new ImageIcon(image);
                JLabel airportImageLabel = new JLabel(airportImageIcon);
                imagesPanel.add(airportImageLabel);
            }
        } catch (HttpStatusException e) {
            String notFound = Translator.getBundleString("not_found");
            airportInfoTextPane.setText(notFound);
            mapLabel.setText(notFound);

        } catch (IOException e) {
            String title = Translator.getBundleString("error");
            String message = Translator.getBundleString("cannot_load");
            JOptionPane.showMessageDialog(panel, message, title, JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setLabelsLoading() {
        String loadingText = Translator.getBundleString("loading");
        airportInfoTextPane.setText(loadingText);
        mapLabel.setText(loadingText);
        mapLabel.setIcon(null);
    }

    private void createUIComponents() {
        airportDetailPanel = airportDetailView.getPanel();
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
        panel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(31);
        scrollPane.setVerticalScrollBarPolicy(20);
        panel.add(scrollPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(4, 1, new Insets(30, 30, 30, 30), -1, 20));
        scrollPane.setViewportView(panel1);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), 30, -1, true, false));
        panel1.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, 10));
        panel2.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        airportInfoTextPane = new JTextPane();
        airportInfoTextPane.setEditable(false);
        airportInfoTextPane.setEnabled(true);
        panel3.add(airportInfoTextPane, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        panel3.add(airportDetailPanel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel3.add(spacer1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        mapPanel = new JPanel();
        mapPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(mapPanel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        mapLabel = new JLabel();
        this.$$$loadLabelText$$$(mapLabel, this.$$$getMessageFromBundle$$$("string", "loading"));
        mapPanel.add(mapLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        imagesHeaderLabel = new JLabel();
        this.$$$loadLabelText$$$(imagesHeaderLabel, this.$$$getMessageFromBundle$$$("string", "images"));
        panel4.add(imagesHeaderLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        imagesPanel = new JPanel();
        imagesPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel4.add(imagesPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        separator = new JSeparator();
        panel1.add(separator, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), 10, -1));
        panel1.add(panel5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        airportNameLabel = new JLabel();
        airportNameLabel.setText("Label");
        panel5.add(airportNameLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addBookMarkButton = new JButton();
        this.$$$loadButtonText$$$(addBookMarkButton, this.$$$getMessageFromBundle$$$("string", "add_bookmark"));
        panel5.add(addBookMarkButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        removeBookMarkButton = new JButton();
        this.$$$loadButtonText$$$(removeBookMarkButton, this.$$$getMessageFromBundle$$$("string", "remove_bookmark"));
        panel5.add(removeBookMarkButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel5.add(spacer2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
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
    private void $$$loadButtonText$$$(AbstractButton component, String text) {
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
            component.setMnemonic(mnemonic);
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
