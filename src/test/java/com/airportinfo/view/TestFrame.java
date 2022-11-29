package com.airportinfo.view;

import javax.swing.*;

public class TestFrame extends MainFrame {
    public TestFrame() {
        super("Test", 700, 700);
    }

    @Override
    protected void changeContent(JPanel content) {
        setContentPane(content);
    }
}
