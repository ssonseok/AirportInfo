package com.airportinfo.view;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.airportinfo.util.GoogleMapAPI;

/**
 * print maps from Google Map API to the screen
 * 
 * @author ShinHeeYoun
 *
 */
public class GoogleMapView extends JFrame {
	
	private JTextField textField = new JTextField(30);
	private JPanel panel = new JPanel();
	private JButton button = new JButton("검색");
	
	private GoogleMapAPI googleAPI = new GoogleMapAPI();
	private JLabel googleMap = new JLabel();
	
	// constructor class with nothing
	// --> print ( textbox + search button + map ) in the screen
	public GoogleMapView() {
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Map Viewer");
		setVisible(true);
		
		panel.add(textField);
		panel.add(button);
		
		button.addMouseListener(new Event());
		add(BorderLayout.NORTH, panel);
		pack();
	}
	
	// constructor class with String value
	// --> print ( map ) in the screen
	public GoogleMapView(String location) {
		googleAPI.downloadMap(location);
		googleMap = new JLabel(googleAPI.getMap(location));
		googleAPI.fileDelete(location);
		add(googleMap);
		
		setTitle("Location picture");
		setVisible(true);
		pack();
	}
	
	// if you click button, get text from textField 
	// (Used by constructor class)
	public class Event implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			setMap(textField.getText());
		}
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
	}
	
	// Load map and place on screen
	// (Used by constructor class)
	public void setMap(String location) {
		googleAPI.downloadMap(location);
		googleMap.setIcon(googleAPI.getMap(location));
		googleAPI.fileDelete(location);
		add(BorderLayout.SOUTH, googleMap);
		pack();
	}
	
	// Change size and zoom
	public void setSize(int num) {
		googleAPI.setSize(num);
	}
	public void setSize(int num, int num2) {
		googleAPI.setSize(num, num2);
	}
	public void setZoom(int num) {
		googleAPI.setZoom(num);
	}
}