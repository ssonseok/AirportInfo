package com.airportinfo.view.chart;

/**
 * Make number format String.
 * Provide int, double format as default.
 *
 * @author lalaalal
 */
public interface NumberFormat {
    NumberFormat INT_FORMAT = (number) -> String.valueOf(number.intValue());
    NumberFormat DOUBLE_FORMAT = (number) -> String.format("%.2f", number.doubleValue());

    /**
     * Make a String with number.
     *
     * @param number Number to make string
     * @return String made by number.
     */
    String formatNumber(Number number);
}
