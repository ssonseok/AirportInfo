package com.airportinfo.misc;

import java.util.Objects;

public final class Aspect {
    public static final Aspect GENERAL_ASPECT = new Aspect(null, "general");

    private final Aspect inheritance;
    private final String interest;

    public Aspect(String interest) {
        this.inheritance = GENERAL_ASPECT;
        this.interest = interest;
    }

    public Aspect(Aspect inheritance, String interest) {
        this.inheritance = inheritance;
        this.interest = interest;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Aspect aspect)
            return Objects.equals(inheritance, aspect)
                    || Objects.equals(interest, aspect.interest);

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(interest, inheritance);
    }

    @Override
    public String toString() {
        return "Aspect["
                + "inheritance=" + Objects.requireNonNullElse(inheritance.interest, "null") + ","
                + "interest=" + interest + ']';
    }
}
