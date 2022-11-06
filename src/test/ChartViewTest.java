package test;

import com.airportinfo.view.MainFrame;

import javax.swing.*;

public class ChartViewTest {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame() {
            @Override
            protected void changeContent(JPanel content) {
                setContentPane(content);
            }
        };
        mainFrame.addContentView("ChartContentView", new ChartContentView());
        mainFrame.setContentView("ChartContentView");

        mainFrame.setVisible(true);
    }
}
