package com.airportinfo.view.content;

import com.airportinfo.Setting;
import com.airportinfo.controller.AirportController;
import com.airportinfo.controller.UserController;
import com.airportinfo.misc.FontCompatibleTextPane;
import com.airportinfo.model.Airport;
import com.airportinfo.model.MouseReleaseListener;
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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

/**
 * ContentView showing detail (information, images, map) of airport.
 *
 * @author lalaalal
 */
public class AirportDetailContentView extends ImageStorableContentView {
    private JPanel panel;
    private JLabel mapLabel;
    private JLabel airportNameLabel;
    private JLabel imagesHeaderLabel;
    private JPanel imagesPanel;
    private JPanel mapPanel;
    private JTextPane airportInfoText;
    private JPanel airportDetailPanel;
    private JSeparator separator;
    private JScrollPane scrollPane;
    private JButton addBookMarkButton;
    private JButton removeBookMarkButton;
    private JPanel screenshotPanel;
    private final JLabel loadingLabel = new JLabel();
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
        loadingLabel.setText(Translator.getBundleString("loading"));


        addThemeChangeListener((theme) -> {
            airportInfoText.setBackground(ThemeManager.getDefaultColor("Label.background"));
            separator.setBackground(themeManager.getColor("Separator.background"));
            separator.setForeground(themeManager.getColor("Separator.foreground"));
            loadingLabel.setForeground(themeManager.getColor("Label.foreground"));
            if (theme == AppTheme.Lite)
                airportDetailPanel.setBorder(BorderFactory.createLineBorder(themeManager.getColor("Separator.foreground")));
            else
                airportDetailPanel.setBorder(BorderFactory.createEmptyBorder());
        });
        addLocaleChangeListener((locale) -> load());
        Setting.getInstance().attach(this::load, Setting.LOCALIZATION_CHANGE);
        addBookMarkButton.addMouseListener(new MouseReleaseListener(mouseEvent -> userController.addBookmark(selected)));
        removeBookMarkButton.addMouseListener(new MouseReleaseListener(mouseEvent -> userController.delBookmark(selected)));
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

        airportDetailView.setAirport(selected);
        setLabelsLoading();
        airportNameLabel.setText(selected.getAirportName());
        imagesHeaderLabel.setText(Translator.getBundleString("images"));
        addBookMarkButton.setText(Translator.getBundleString("add_bookmark"));
        removeBookMarkButton.setText(Translator.getBundleString("remove_bookmark"));
        Thread loadingThread = new Thread(this::loadAirportInformation);
        loadingThread.start();
    }

    @Override
    public void actionBeforeUIUpdate(AppTheme theme) {
        UIManager.put("TextPane.selectionBackground", ThemeManager.getDefaultColor(ThemeManager.LITE_THEME, "List.selectionBackground"));
    }

    @Override
    public void store(File file) {
        takeScreenshot(file, screenshotPanel, screenshotPanel.getPreferredSize());
    }

    private void loadMap() {
        try {
            String location = selected.getRawData().englishName;
            int width = mapPanel.getWidth(), height = mapPanel.getHeight();
            BufferedImage bufferedImage = GoogleMapAPI.getMapImage(location, width, height, 12);
            ImageIcon mapImageIcon = new ImageIcon(bufferedImage);
            mapLabel.setText("");
            mapLabel.setIcon(mapImageIcon);
        } catch (IOException e) {
            String message = Translator.getBundleString("cannot_load_map");
            mapLabel.setText(message);
        }
    }

    private void loadAirportInformation() {
        try {
            AirportWikiCrawler crawler = new AirportWikiCrawler(selected);
            String airportInfo = " " + crawler.getInformation();
            airportInfoText.setText(airportInfo);

            imagesPanel.removeAll();
            BufferedImage[] images = crawler.getBufferedImages(3);
            for (BufferedImage image : images) {
                ImageIcon airportImageIcon = new ImageIcon(image);
                JLabel airportImageLabel = new JLabel(airportImageIcon);
                imagesPanel.add(airportImageLabel);
            }
        } catch (HttpStatusException e) {
            String notFound = Translator.getBundleString("not_found");
            airportInfoText.setText(notFound);
            loadingLabel.setText(notFound);
            mapLabel.setText(notFound);
        } catch (IOException e) {
            String message = Translator.getBundleString("cannot_load_wiki");
            airportInfoText.setText(message);
        }
        loadMap();
        panel.revalidate();
    }

    private void setLabelsLoading() {
        String loadingText = Translator.getBundleString("loading");
        airportInfoText.setText(loadingText);
        mapLabel.setText(loadingText);
        mapLabel.setIcon(null);
        loadingLabel.setText(loadingText);
        imagesPanel.removeAll();
        imagesPanel.add(loadingLabel);
    }

    private void createUIComponents() {
        airportDetailPanel = airportDetailView.getPanel();
        airportInfoText = new FontCompatibleTextPane();
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
        panel.add(scrollPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        screenshotPanel = new JPanel();
        screenshotPanel.setLayout(new GridLayoutManager(4, 1, new Insets(30, 30, 30, 30), -1, 20));
        scrollPane.setViewportView(screenshotPanel);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), 30, -1, true, false));
        screenshotPanel.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, 10));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel2.add(airportDetailPanel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel2.add(spacer1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panel2.add(airportInfoText, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        mapPanel = new JPanel();
        mapPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(mapPanel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(-1, 250), null, null, 0, false));
        mapLabel = new JLabel();
        this.$$$loadLabelText$$$(mapLabel, this.$$$getMessageFromBundle$$$("string", "loading"));
        mapPanel.add(mapLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        screenshotPanel.add(panel3, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        imagesHeaderLabel = new JLabel();
        this.$$$loadLabelText$$$(imagesHeaderLabel, this.$$$getMessageFromBundle$$$("string", "images"));
        panel3.add(imagesHeaderLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        imagesPanel = new JPanel();
        imagesPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel3.add(imagesPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        separator = new JSeparator();
        screenshotPanel.add(separator, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), 10, -1));
        screenshotPanel.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        airportNameLabel = new JLabel();
        airportNameLabel.setText("Label");
        panel4.add(airportNameLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addBookMarkButton = new JButton();
        this.$$$loadButtonText$$$(addBookMarkButton, this.$$$getMessageFromBundle$$$("string", "add_bookmark"));
        panel4.add(addBookMarkButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        removeBookMarkButton = new JButton();
        this.$$$loadButtonText$$$(removeBookMarkButton, this.$$$getMessageFromBundle$$$("string", "remove_bookmark"));
        panel4.add(removeBookMarkButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel4.add(spacer2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
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
