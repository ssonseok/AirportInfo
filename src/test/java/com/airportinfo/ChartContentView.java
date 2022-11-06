package com.airportinfo;

import com.airportinfo.utils.Screenshot;
import com.airportinfo.view.ChartView;
import com.airportinfo.view.ContentView;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class ChartContentView implements ContentView {
    private JPanel panel;
    private JButton saveButton;
    private JPanel chartViewPanel;

    private ChartView chartView;

    public ChartContentView() {
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Screenshot.createScreenshot(chartViewPanel, "test.png");
                    JOptionPane.showMessageDialog(panel, "Succeed", "Info", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(panel, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void load() {
        chartView.clear();
        chartView.addEntry("a", 10);
        chartView.addEntry("b", 20);
        chartView.addEntry("c", 30);
        chartView.addEntry("d", 40);
        chartView.addEntry("e", 50);
        chartView.addEntry("f", 60);
        chartView.addEntry("g", 70);
        chartView.addEntry("h", 80);
        chartView.addEntry("i", 90);
    }

    private void createUIComponents() {
        chartView = new ChartView();
        chartViewPanel = chartView.getPanel();
    }
}
