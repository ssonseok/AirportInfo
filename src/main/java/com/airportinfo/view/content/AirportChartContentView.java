package com.airportinfo.view.content;

import com.airportinfo.Setting;
import com.airportinfo.controller.AirportController;
import com.airportinfo.misc.AirportAttributeSelector;
import com.airportinfo.misc.AttributeStatisticCreator;
import com.airportinfo.misc.CautiousFileChooser;
import com.airportinfo.model.Airport;
import com.airportinfo.util.Translator;
import com.airportinfo.view.AirportFrame;
import com.airportinfo.view.airport.AirportChartView;
import com.airportinfo.view.airport.AirportTableView;
import com.airportinfo.view.chart.HistogramView;
import com.airportinfo.view.chart.PieChartView;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.util.List;
import java.util.*;

/**
 * Airport chart content view.
 *
 * @author lalaalal
 */
public class AirportChartContentView extends ImageStorableContentView {
    private static final AirportAttributeSelector[] ATTRIBUTE_SELECTORS = {
            new AirportAttributeSelector("Region"),
            new AirportAttributeSelector("Country")
    };
    private JPanel panel;
    private JPanel airportChartPanel;
    private JComboBox<AirportAttributeSelector> selectorComboBox;
    private JComboBox<String> regionComboBox;
    private JButton showHistogramButton;
    private JButton showPieChartButton;
    private JPanel selectedAirportsPanel;
    private JScrollPane scrollPane;
    private final PieChartView pieChartView = new PieChartView();
    private final HistogramView histogramView = new HistogramView();
    private final AirportTableView airportTableView = new AirportTableView();
    private final AirportChartView airportChartView;
    private final AirportController airportController;

    public AirportChartContentView(AirportFrame mainFrame) {
        super(mainFrame);
        Setting setting = Setting.getInstance();
        airportController = mainFrame.getAirportController();
        airportChartView = new AirportChartView(histogramView, new AttributeStatisticCreator(Airport::getRegion));
        for (int i = 1; i < Airport.ATTRIBUTE_NAMES.length; i++)
            airportTableView.removeColumn(Airport.getLocalizedAttributeNames()[i]);

        $$$setupUI$$$();

        scrollPane.getHorizontalScrollBar().setUnitIncrement(10);

        addComponentView(histogramView);
        addComponentView(pieChartView);
        addComponentView(airportChartView);
        addComponentView(airportTableView);

        addLocaleChangeListener(locale -> {
            updateComboBox();
            updateButtonText();
        });

        showHistogramButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                airportChartView.setChartView(histogramView);
                statisticTargetAirports();
            }
        });
        showPieChartButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                airportChartView.setChartView(pieChartView);
                statisticTargetAirports();
            }
        });

        setting.attach(() -> {
            histogramView.showGuideline = setting.isShowHistogramGuideLine();
            histogramView.setLegendInterval(setting.getHistogramLegendInterval());
            airportChartView.getPanel().repaint();
        }, Setting.HISTOGRAM_CHANGE);
        setting.attach(() -> {
            histogramView.showLegendLabel = setting.isShowChartLabel();
            pieChartView.showLegendLabel = setting.isShowChartLabel();
            airportChartView.getPanel().repaint();
        }, Setting.CHART_CHANGE);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void load() {
        statisticTargetAirports();
    }

    @Override
    public void store() {
        CautiousFileChooser fileChooser = new CautiousFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("png", "png"));
        fileChooser.showFileChooser(mainFrame.getPanel(), file -> takeScreenshot(file, airportChartPanel, airportChartPanel.getBounds()));
    }

    private void statisticTargetAirports() {
        AirportAttributeSelector selector
                = (AirportAttributeSelector) Objects.requireNonNullElse(selectorComboBox.getSelectedItem(), ATTRIBUTE_SELECTORS[0]);
        String region = (String) Objects.requireNonNullElse(regionComboBox.getSelectedItem(), Translator.getBundleString("all"));
        List<Airport> airports = getRegionAirports(region);
        airportChartView.setAirports(airports);
        airportChartView.setStatisticCreator(new AttributeStatisticCreator(selector.getSelector()));
        airportTableView.setAirports(airports);
    }

    private List<Airport> getRegionAirports(String region) {
        if (region.equals(Translator.getBundleString("all")))
            return List.of(airportController.getAirports());
        return Arrays.stream(airportController.getAirports()).filter(airport -> airport.getRegion().equals(region)).toList();
    }

    private void updateComboBox() {
        HashSet<String> selectableRegions = new HashSet<>();
        Arrays.stream(airportController.getAirports()).forEach(airport -> selectableRegions.add(airport.getRegion()));
        regionComboBox.removeAllItems();
        regionComboBox.addItem(Translator.getBundleString("all"));
        for (String selectableRegion : selectableRegions)
            regionComboBox.addItem(selectableRegion);

        selectorComboBox.removeAllItems();
        for (AirportAttributeSelector attributeSelector : ATTRIBUTE_SELECTORS)
            selectorComboBox.addItem(attributeSelector);
    }

    private void updateButtonText() {
        showHistogramButton.setText(Translator.getBundleString("show_histogram"));
        showPieChartButton.setText(Translator.getBundleString("show_pie"));
    }

    private void createUIComponents() {
        airportChartPanel = airportChartView.getPanel();
        selectedAirportsPanel = airportTableView.getPanel();
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
        panel.add(scrollPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(15, 15, 15, 15), 10, -1));
        scrollPane.setViewportView(panel1);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, 10));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, 1, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), 10, -1));
        panel2.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, 1, 1, null, null, null, 0, false));
        selectorComboBox = new JComboBox();
        panel3.add(selectorComboBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), 10, -1, true, false));
        panel2.add(panel4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, 1, 1, null, null, null, 0, false));
        showHistogramButton = new JButton();
        this.$$$loadButtonText$$$(showHistogramButton, this.$$$getMessageFromBundle$$$("string", "show_histogram"));
        panel4.add(showHistogramButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        showPieChartButton = new JButton();
        this.$$$loadButtonText$$$(showPieChartButton, this.$$$getMessageFromBundle$$$("string", "show_pie"));
        panel4.add(showPieChartButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel2.add(selectedAirportsPanel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, 1, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        regionComboBox = new JComboBox();
        panel2.add(regionComboBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel1.add(airportChartPanel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
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
