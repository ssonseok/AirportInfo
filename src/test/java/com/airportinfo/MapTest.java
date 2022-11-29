package com.airportinfo;

import com.airportinfo.view.GoogleMapView;
/**
 * Controller handling viewing map function 
 * (src/main/java/com.airportinfo/GoogleMapAPI.java, src/main/java/com.airportinfo/GoogleMapFrame.java)
 * 
 * @author ShinHeeYoun
 *
 */
public class MapTest {
	public static void main(String[] args) {
		/**
		 * if you want to search place inside program 
		 * 
		 * GoogleMapFrame frame = new GoogleMapFrame();
		 */
		GoogleMapView frame = new GoogleMapView();
		frame.setSize(1000);	
		/**
		 * if you want to see only map 
		 * (To view airport maps directly without searching)
		 * 
		 * String location="--------";
		 * GoogleMapFrame frame = new GoogleMapFrame(location);
		 */
		//String location="--------";
		//GoogleMapFrame frame = new GoogleMapFrame(location);
		
		/**
		 * change size, zoom
		 */
		// frame.setSize(1000);			//xSize=1000, ySize=1000
		// frame.setSize(1000, 800);	//xSize=1000, ySize=800
		// frame.setZoom(20);			//zoom=20
	}
}