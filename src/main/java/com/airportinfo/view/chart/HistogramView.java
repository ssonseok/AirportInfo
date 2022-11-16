package com.airportinfo.view.chart;

import com.airportinfo.utils.FontManager;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

/**
 * Histogram View.
 *
 * @author lalaalal
 */
public class HistogramView extends AbstractChartView {
    public static final int AXIS_INTERVAL = 15;
    public static final int AXIS_THICKNESS = 2;
    public static final int GUID_THICKNESS = 1;
    public static final int DEFAULT_LEGEND_INTERVAL = 20;
    private JPanel panel;
    private JLabel titleLabel;
    private JPanel histogramPanel;
    private JPanel legendDetailPanel;
    private double max = 0;
    private int legendInterval = DEFAULT_LEGEND_INTERVAL;

    public HistogramView() {

        $$$setupUI$$$();
    }

    public HistogramView(String title) {
        $$$setupUI$$$();
        titleLabel.setText(title);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Set interval between legends.
     *
     * @param legendInterval Interval
     */
    public void setLegendInterval(int legendInterval) {
        this.legendInterval = legendInterval;
    }

    private double calcMax(Number value) {
        int digit = (int) Math.log10(value.doubleValue());
        int firstDigit = (int) (value.doubleValue() / Math.pow(10, digit));
        return (firstDigit + 1) * Math.pow(10, digit);
    }

    private double getUnit() {
        int digit = (int) Math.log10(max);
        return Math.pow(10, digit);
    }

    @Override
    public void addLegend(Legend legend) {
        super.addLegend(legend);
        max = Math.max(max, calcMax(legend.value()));
    }

    @Override
    public void updateChartView(Graphics graphics) {
        if (graphics instanceof Graphics2D graphics2D)
            graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int yAxisWidth = graphics.getFontMetrics().stringWidth(numberFormat.formatNumber(max)) + AXIS_INTERVAL;
        int width = (int) (histogramPanel.getWidth() * 0.8);
        int height = (int) (histogramPanel.getHeight() * 0.8);
        int xOffset = (histogramPanel.getWidth() - width) / 2;
        int yOffset = (histogramPanel.getHeight() - height) / 2;
        int actualXOffset = xOffset + yAxisWidth;
        int actualWidth = width - yAxisWidth;

        drawLegends(graphics, actualXOffset, yOffset, actualWidth, height);
        drawGuidLine(graphics, actualXOffset, yOffset, actualWidth, height);
        drawAxis(graphics, actualXOffset, yOffset, actualWidth, height);
        drawYAxisLabel(graphics, xOffset, yOffset, height);
    }

    /**
     * Draw x, y axis.
     *
     * @param graphics Graphic instance
     * @param xOffset  X offset of actual chart
     * @param yOffset  Y offset of actual chart
     * @param width    Width of actual chart
     * @param height   Height of actual chart
     */
    private void drawAxis(Graphics graphics, int xOffset, int yOffset, int width, int height) {
        Color lineColor = UIManager.getDefaults().getColor("Label.foreground");
        graphics.setColor(lineColor);
        if (graphics instanceof Graphics2D graphics2D)
            graphics2D.setStroke(new BasicStroke(AXIS_THICKNESS));
        graphics.drawLine(xOffset, yOffset, xOffset, yOffset + height);
        graphics.drawLine(xOffset, yOffset + height, xOffset + width, yOffset + height);
    }

    /**
     * Draw y axis unit label.
     *
     * @param graphics Graphic instance
     * @param xOffset  X offset of entire chart
     * @param yOffset  Y offset of entire chart
     * @param height   Height of entire chart
     */
    private void drawYAxisLabel(Graphics graphics, int xOffset, int yOffset, int height) {
        Color lineColor = UIManager.getDefaults().getColor("Label.foreground");
        graphics.setColor(lineColor);

        double unit = getUnit();
        int numUnitLabels = (int) (max / unit);
        int unitLabelInterval = height / numUnitLabels - 1;

        double unitLine = 0;
        int labelHeight = (int) (graphics.getFontMetrics().getHeight() * 0.25);
        int unitLabelY = yOffset + height + labelHeight;
        for (int i = 0; i < numUnitLabels; i++) {
            graphics.drawString(numberFormat.formatNumber(unitLine), xOffset, unitLabelY);
            unitLine += unit;
            unitLabelY -= unitLabelInterval;
        }
        graphics.drawString(numberFormat.formatNumber(unitLine), xOffset, unitLabelY);
    }

    /**
     * Draw guid line.
     *
     * @param graphics Graphic instance
     * @param xOffset  X offset of actual chart
     * @param yOffset  Y offset of actual chart
     * @param width    Width of actual chart
     * @param height   Height of actual chart
     */
    private void drawGuidLine(Graphics graphics, int xOffset, int yOffset, int width, int height) {
        Color lineColor = UIManager.getDefaults().getColor("Label.foreground");
        graphics.setColor(lineColor);
        if (graphics instanceof Graphics2D graphics2D)
            graphics2D.setStroke(new BasicStroke(GUID_THICKNESS));

        double unit = getUnit();
        int numUnitLabels = (int) (max / unit);
        int guidLineInterval = height / numUnitLabels;
        int guidLineY = yOffset + height;
        for (int i = 0; i < numUnitLabels; i++) {
            graphics.drawLine(xOffset, guidLineY, xOffset + width, guidLineY);
            guidLineY -= guidLineInterval;
        }
    }

    /**
     * Draw legends.
     *
     * @param graphics Graphic instance
     * @param xOffset  X offset of actual chart
     * @param yOffset  Y offset of actual chart
     * @param width    Width of actual chart
     * @param height   Height of actual chart
     */
    private void drawLegends(Graphics graphics, int xOffset, int yOffset, int width, int height) {
        int totalInterval = legendInterval * (legends.size() + 1);
        int legendWidth = (width - totalInterval) / legends.size();
        int legendX = legendInterval;
        for (Legend legend : legends) {
            double legendRate = legend.value().doubleValue() / max;
            int legendHeight = (int) (height * legendRate + 0.5) - AXIS_THICKNESS;
            int spaceHeight = height - legendHeight;

            Color color = getColor(legend.name());
            graphics.setColor(color);
            graphics.fillRect(xOffset + legendX, yOffset + spaceHeight, legendWidth, legendHeight);
            legendX += legendWidth + legendInterval;
        }
    }

    @Override
    public void clear() {
        super.clear();
        max = 0;
    }

    private void createUIComponents() {
        titleLabel = new JLabel();
        titleLabel.setFont(FontManager.getFont(FontManager.HEADER_FONT_SIZE));
        histogramPanel = createChartPanel();
        legendDetailPanel = getLegendDetailPanel();
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
        panel.setLayout(new GridLayoutManager(3, 1, new Insets(10, 10, 10, 10), -1, -1));
        panel.add(histogramPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        this.$$$loadLabelText$$$(titleLabel, this.$$$getMessageFromBundle$$$("string", "default_chart_title"));
        panel.add(titleLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel.add(legendDetailPanel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, 1, 1, null, null, null, 0, false));
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
