package com.airportinfo;

import com.airportinfo.misc.Aspect;
import com.airportinfo.misc.Subject;
import com.airportinfo.view.chart.HistogramView;

/**
 * Application setting.
 *
 * @author lalaalal
 */
public class Setting extends Subject {
    public static final Aspect CHART_CHANGE = new Aspect("chart_change");
    public static final Aspect HISTOGRAM_CHANGE = new Aspect(CHART_CHANGE, "histogram_change");

    private static Setting instance;

    private boolean showChartLabel = true;
    private int histogramLegendInterval = HistogramView.DEFAULT_LEGEND_INTERVAL;
    private boolean showHistogramGuideLine = true;

    public static Setting getInstance() {
        if (instance == null)
            return instance = new Setting();
        return instance;
    }

    private Setting() { }

    public boolean getShowChartLabel() {
        return showChartLabel;
    }

    /**
     * Set show chart label and notice observers.
     *
     * @param showChartLabel Show chart label
     */
    public void setShowChartLabel(boolean showChartLabel) {
        this.showChartLabel = showChartLabel;
        notice(CHART_CHANGE);
    }

    /**
     * Set show chart label without notice.
     *
     * @param showChartLabel Show chart label
     */
    public void setSilentShowChartLabel(boolean showChartLabel) {
        this.showChartLabel = showChartLabel;
    }

    public int getHistogramLegendInterval() {
        return histogramLegendInterval;
    }

    /**
     * Set histogram legend interval and notice observers.
     *
     * @param histogramLegendInterval Histogram legend interval
     */
    public void setHistogramLegendInterval(int histogramLegendInterval) {
        this.histogramLegendInterval = histogramLegendInterval;
        notice(HISTOGRAM_CHANGE);
    }

    /**
     * Set histogram legend interval without notice.
     *
     * @param histogramLegendInterval Histogram legend interval
     */
    public void setSilentHistogramLegendInterval(int histogramLegendInterval) {
        this.histogramLegendInterval = histogramLegendInterval;
    }

    public boolean getShowHistogramGuideLine() {
        return showHistogramGuideLine;
    }

    /**
     * Set show histogram guideline and notice observers.
     *
     * @param showHistogramGuideLine Show histogram guideline
     */
    public void setShowHistogramGuideLine(boolean showHistogramGuideLine) {
        this.showHistogramGuideLine = showHistogramGuideLine;
        notice(HISTOGRAM_CHANGE);
    }

    /**
     * Set show histogram guideline without notice.
     *
     * @param showHistogramGuideLine Show histogram guideline
     */
    public void setSilentShowHistogramGuideLine(boolean showHistogramGuideLine) {
        this.showHistogramGuideLine = showHistogramGuideLine;
    }
}
