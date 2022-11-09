package com.airportinfo;

import javax.swing.JPanel;

import com.airportinfo.view.MainFrame;

public class EmailTest {
	public static void main(String[] args) {
		 Main.setSystemProperties();
         MainFrame mainFrame = new MainFrame("Main", 700, 700) {
             @Override
             protected void changeContent(JPanel content) {
                 setContentPane(content);
             }
         };
         
         mainFrame.setVisible(true);
	}
}
