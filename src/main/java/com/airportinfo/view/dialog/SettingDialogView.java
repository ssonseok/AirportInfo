package com.airportinfo.view.dialog;

import com.airportinfo.Setting;
import com.airportinfo.controller.UserController;
import com.airportinfo.misc.SpinnerPositiveNumberModel;
import com.airportinfo.util.ThemeManager;
import com.airportinfo.util.Translator;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Setting dialog view.
 *
 * @author lalaalal
 */
public class SettingDialogView extends DialogView {
    private static final String DEFAULT_EXTENSION = Setting.SUPPORTED_AIRPORT_TABLE_SAVE_EXTENSIONS[0];
    private JPanel panel;
    private JTabbedPane tabbedPane;
    private JButton cancelButton;
    private JButton saveButton;
    private JCheckBox showChartLabelCheckBox;
    private JCheckBox showGuidelineCheckBox;
    private JSpinner intervalSpinner;
    private JButton resetHistoryButton;
    private JButton resetBookmarkButton;
    private JPanel chartGeneralPanel;
    private JPanel histogramPanel;
    private JLabel intervalLabel;
    private JCheckBox localizeEnglishCheckBox;
    private JComboBox<String> extensionComboBox;
    private final Setting setting = Setting.getInstance();

    public SettingDialogView(UserController userController) {
        $$$setupUI$$$();
        dialog.setContentPane(getPanel());
        dialog.setTitle(Translator.getBundleString("setting"));
        dialog.setSize(700, 500);

        updateComponentColor();
        intervalSpinner.setModel(new SpinnerPositiveNumberModel());
        load();

        resetBookmarkButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                userController.delAllBookmark();
            }
        });
        resetHistoryButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                userController.delAllRecent();
            }
        });

        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                saveSetting();
                dialog.setVisible(false);
            }
        });
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dialog.setVisible(false);
            }
        });

        addThemeChangeListener(theme -> updateComponentColor());
        addLocaleChangeListener(locale -> updateLanguage());
    }

    private void updateComponentColor() {
        Color borderColor = ThemeManager.getDefaultColor("Spinner.background");
        intervalSpinner.setBorder(BorderFactory.createLineBorder(borderColor, 5));
        updateTitledBorder();
    }

    private void updateLanguage() {
        dialog.setTitle(Translator.getBundleString("setting"));
        tabbedPane.setTitleAt(0, Translator.getBundleString("general"));
        tabbedPane.setTitleAt(1, Translator.getBundleString("chart_setting"));
        updateTitledBorder();
        showChartLabelCheckBox.setText(Translator.getBundleString("show_chart_label"));
        showGuidelineCheckBox.setText(Translator.getBundleString("show_guideline"));
        intervalLabel.setText(Translator.getBundleString("histogram_interval"));
        saveButton.setText(Translator.getBundleString("save"));
        cancelButton.setText(Translator.getBundleString("cancel"));
        resetBookmarkButton.setText(Translator.getBundleString("reset_bookmark"));
        resetHistoryButton.setText(Translator.getBundleString("reset_history"));
        resetBookmarkButton.setToolTipText(Translator.getBundleString("reset_tooltip"));
        resetHistoryButton.setToolTipText(Translator.getBundleString("reset_tooltip"));
        localizeEnglishCheckBox.setText(Translator.getBundleString("localize_english_only"));
    }

    private void updateTitledBorder() {
        String histogramTitle = Translator.getBundleString("histogram_setting");
        String chartGeneralTitle = Translator.getBundleString("general");
        Color lineColor = ThemeManager.getDefaultColor("Label.foreground");
        histogramPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(lineColor), histogramTitle));
        chartGeneralPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(lineColor), chartGeneralTitle));
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void load() {
        showChartLabelCheckBox.setSelected(setting.isShowChartLabel());
        intervalSpinner.setValue(setting.getHistogramLegendInterval());
        showGuidelineCheckBox.setSelected(setting.isShowHistogramGuideLine());
        localizeEnglishCheckBox.setSelected(setting.isLocalizeEnglish());
        for (String supportedAirportTableSaveExtension : Setting.SUPPORTED_AIRPORT_TABLE_SAVE_EXTENSIONS)
            extensionComboBox.addItem(supportedAirportTableSaveExtension);
        extensionComboBox.setSelectedItem(setting.getAirportTableExtension());
    }

    private void saveSetting() {
        setting.setSilentShowChartLabel(showChartLabelCheckBox.isSelected());
        int interval = (int) intervalSpinner.getValue();
        setting.setSilentHistogramLegendInterval(interval);
        setting.setSilentShowHistogramGuideLine(showGuidelineCheckBox.isSelected());
        setting.setSilentLocalizeEnglishOnly(localizeEnglishCheckBox.isSelected());
        String extension = Objects.requireNonNullElse(extensionComboBox.getSelectedItem(), DEFAULT_EXTENSION).toString();
        setting.setAirportTableExtension(extension);
        setting.notice();
    }

    private void createUIComponents() {
        extensionComboBox = new JComboBox<>();
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
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        tabbedPane = new JTabbedPane();
        panel.add(tabbedPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane.addTab(this.$$$getMessageFromBundle$$$("string", "general"), panel1);
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), 10, -1));
        panel1.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        resetHistoryButton = new JButton();
        this.$$$loadButtonText$$$(resetHistoryButton, this.$$$getMessageFromBundle$$$("string", "reset_history"));
        resetHistoryButton.setToolTipText(this.$$$getMessageFromBundle$$$("string", "reset_tooltip"));
        panel2.add(resetHistoryButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        resetBookmarkButton = new JButton();
        this.$$$loadButtonText$$$(resetBookmarkButton, this.$$$getMessageFromBundle$$$("string", "reset_bookmark"));
        resetBookmarkButton.setToolTipText(this.$$$getMessageFromBundle$$$("string", "reset_tooltip"));
        panel2.add(resetBookmarkButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel2.add(spacer1, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel1.add(spacer2, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        localizeEnglishCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(localizeEnglishCheckBox, this.$$$getMessageFromBundle$$$("string", "localize_english_only"));
        panel1.add(localizeEnglishCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 2, new Insets(0, 7, 0, 0), 10, -1));
        panel1.add(panel3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        this.$$$loadLabelText$$$(label1, this.$$$getMessageFromBundle$$$("string", "default_airport_save_extension"));
        panel3.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel3.add(extensionComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane.addTab(this.$$$getMessageFromBundle$$$("string", "chart_setting"), panel4);
        panel4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        chartGeneralPanel = new JPanel();
        chartGeneralPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel4.add(chartGeneralPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        chartGeneralPanel.setBorder(BorderFactory.createTitledBorder(null, this.$$$getMessageFromBundle$$$("string", "general"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        showChartLabelCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(showChartLabelCheckBox, this.$$$getMessageFromBundle$$$("string", "show_chart_label"));
        chartGeneralPanel.add(showChartLabelCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        chartGeneralPanel.add(spacer3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        histogramPanel = new JPanel();
        histogramPanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel4.add(histogramPanel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        histogramPanel.setBorder(BorderFactory.createTitledBorder(null, this.$$$getMessageFromBundle$$$("string", "histogram_setting"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        showGuidelineCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(showGuidelineCheckBox, this.$$$getMessageFromBundle$$$("string", "show_guideline"));
        histogramPanel.add(showGuidelineCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        histogramPanel.add(spacer4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 2, new Insets(0, 7, 0, 10), -1, -1));
        histogramPanel.add(panel5, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        intervalLabel = new JLabel();
        this.$$$loadLabelText$$$(intervalLabel, this.$$$getMessageFromBundle$$$("string", "histogram_interval"));
        panel5.add(intervalLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        intervalSpinner = new JSpinner();
        panel5.add(intervalSpinner, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, -1), null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel.add(panel6, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        cancelButton = new JButton();
        this.$$$loadButtonText$$$(cancelButton, this.$$$getMessageFromBundle$$$("string", "cancel"));
        panel6.add(cancelButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel6.add(spacer5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        saveButton = new JButton();
        this.$$$loadButtonText$$$(saveButton, this.$$$getMessageFromBundle$$$("string", "save"));
        panel6.add(saveButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        intervalLabel.setLabelFor(intervalSpinner);
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
