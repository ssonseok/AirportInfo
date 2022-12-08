package com.airportinfo.misc;

import com.airportinfo.model.Airport;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * A set of attribute name and selector.
 *
 * @author lalaalal
 */
public final class AirportAttributeSelector {
    private final int attributeIndex;
    private final Function<Airport, String> selector;

    /**
     * Initialize AirportAttributeSelector via attribute name.
     *
     * @param attributeName Attribute name should be an element of Airport.ATTRIBUTE_NAMES
     * @throws IllegalArgumentException If given attributeName is invalid
     */
    public AirportAttributeSelector(String attributeName) {
        ArrayList<String> attributes = new ArrayList<>(List.of(Airport.ATTRIBUTE_NAMES));
        attributeIndex = attributes.indexOf(attributeName);
        selector = searchAttributeSelector(attributeIndex);
    }

    private static Function<Airport, String> searchAttributeSelector(int attributeIndex) {
        return switch (attributeIndex) {
            case 0 -> Airport::getAirportName;
            case 1 -> Airport::getIATA;
            case 2 -> Airport::getICAO;
            case 3 -> Airport::getRegion;
            case 4 -> Airport::getCountry;
            case 5 -> Airport::getCity;
            default -> throw new IllegalArgumentException("Unsupported attribute index : " + attributeIndex);
        };
    }

    /**
     * Get attribute value of airport.
     *
     * @param airport Airport to get value
     * @return Attribute value of airport
     */
    public String getAttributeValue(Airport airport) {
        return selector.apply(airport);
    }

    public Function<Airport, String> getSelector() {
        return selector;
    }

    @Override
    public String toString() {
        return Airport.getLocalizedAttributeNames()[attributeIndex];
    }
}
