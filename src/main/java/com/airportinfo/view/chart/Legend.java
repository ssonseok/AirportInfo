package com.airportinfo.view.chart;

import java.util.Objects;

/**
 * Chart Legend
 *
 * @param name  Legend name
 * @param value Legend value
 * @author lalaalal
 */
public record Legend(String name, Number value) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Legend legend = (Legend) o;

        if (!Objects.equals(name, legend.name)) return false;
        return Objects.equals(value, legend.value);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
