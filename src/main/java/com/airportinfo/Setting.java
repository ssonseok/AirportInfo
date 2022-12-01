package com.airportinfo;

import com.airportinfo.misc.Aspect;
import com.airportinfo.misc.Subject;

public class Setting extends Subject {
    public static final Aspect CHART_CHANGE = new Aspect("chart_change");
    public static final Aspect HISTOGRAM_CHANGE = new Aspect(CHART_CHANGE, "histogram_change");

    private static Setting instance;

    private boolean showChartLabel = true;
    private int histogramLegendInterval = 4;
    private boolean showHistogramGuideLine = true;

    public static Setting getInstance() {
        if (instance == null)
            return instance = new Setting();
        return instance;
    }

    private Setting() { }

    public boolean isShowChartLabel() {
        return showChartLabel;
    }

    public void setShowChartLabel(boolean showChartLabel) {
        this.showChartLabel = showChartLabel;
        notice(CHART_CHANGE);
    }

    public int getHistogramLegendInterval() {
        return histogramLegendInterval;
    }

    public void setHistogramLegendInterval(int histogramLegendInterval) {
        this.histogramLegendInterval = histogramLegendInterval;
        notice(HISTOGRAM_CHANGE);
    }

    public boolean isShowHistogramGuideLine() {
        return showHistogramGuideLine;
    }

    public void setShowHistogramGuideLine(boolean showHistogramGuideLine) {
        this.showHistogramGuideLine = showHistogramGuideLine;
        notice(HISTOGRAM_CHANGE);
    }
}
