package com.airportinfo.util;

import com.airportinfo.view.TestComponentView;
import com.airportinfo.view.TestContentView;
import com.airportinfo.view.TestFrame;

import javax.swing.*;
import java.io.IOException;

public class GoogleMapAPITest {
    public static void main(String[] args) throws IOException {
        TestFrame testFrame = new TestFrame();
        testFrame.showFrame();
        testFrame.load();
        TestContentView testContentView = new TestContentView(testFrame);
        TestComponentView testComponentView = new TestComponentView();
        JLabel label = new JLabel("Loading");
        testComponentView.getPanel().add(label);
        testContentView.setComponent(testComponentView);
        testFrame.addContentView("test", testContentView);
        testFrame.setContentView("test");

        ImageIcon imageIcon = new ImageIcon(GoogleMapAPI.getMapImage("인천 국제공항", 500, 500, 12));
        label.setIcon(imageIcon);
        label.setText("");
    }
}