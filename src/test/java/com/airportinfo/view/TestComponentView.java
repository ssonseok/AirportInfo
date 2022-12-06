package com.airportinfo.view;

import javax.swing.*;

public class TestComponentView extends ComponentView {
    private final JPanel panel = new JPanel();

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
