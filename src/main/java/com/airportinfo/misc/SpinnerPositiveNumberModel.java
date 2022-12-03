package com.airportinfo.misc;

import javax.swing.*;

public class SpinnerPositiveNumberModel extends SpinnerNumberModel {
    @Override
    public void setValue(Object value) {
        if (value instanceof Integer integer && integer >= 0)
            super.setValue(integer);
        else
            super.setValue(0);
    }
}
