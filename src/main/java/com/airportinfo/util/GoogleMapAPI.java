package com.airportinfo.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import javax.swing.ImageIcon;
/**
 * Get the map picture of location(Variable) from the Google API URL.
 * (used by GoogleMapFrame.java)
 * 
 * @author ShinHeeYoun
 *
 */
public class GoogleMapAPI {
	private static int mapSizeX=700;
	private static int mapSizeY=700;
	private static int zoom = 9;
	private static final String MapAPI ="AIzaSyDNconz87XncSFuaSdNts129M43jXohGkc";

	public void downloadMap(String location) {
		try {
			String imageURL = "https://maps.googleapis.com/maps/api/staticmap?center="
					+ URLEncoder.encode(location, "UTF-8")
					+ "&zoom="+zoom+"&size="+mapSizeX+"x"+mapSizeY+"&key="+MapAPI;
			
			URL url = new URL(imageURL);
			InputStream is = url.openStream();
			OutputStream os = new FileOutputStream(location);
			byte[] b = new byte[2048];
			int length;
			while((length = is.read(b))!= -1) {
				os.write(b, 0, length);
			}
			is.close();
			os.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public ImageIcon getMap(String location) {
		return new ImageIcon((new ImageIcon(location)).getImage().getScaledInstance(mapSizeX, mapSizeY, java.awt.Image.SCALE_SMOOTH));
	}
	
	public void fileDelete(String filename) {
		File f = new File(filename);
		f.delete();
	}
	
	public void setSize (int num) {
		mapSizeX=num;
		mapSizeY=num;
	}
	public void setSize (int num, int num2) {
		mapSizeX=num;
		mapSizeY=num2;
	}
	public void setZoom (int num) {
		zoom=num;
	}
}
