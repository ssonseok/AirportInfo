package com.airportinfo;

import com.airportinfo.view.GoogleMapView;
/**
 * Controller handling viewing map function 
 * (src/main/java/com.airportinfo/GoogleMapAPI.java, src/main/java/com.airport.view/GoogleMapView.java)
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
			
		/**
		 * if you want to see only map 
		 * (To view airport maps directly without searching)
		 * 
		 * String location="--------";
		 * GoogleMapView frame = new GoogleMapView(location);
		 */
		//String location="--------";
		//GoogleMapView frame = new GoogleMapView(location);
		
		/**
		 * change size, zoom
		 */
		// frame.setSize(1000);			//xSize=1000, ySize=1000
		// frame.setSize(1000, 400);	//xSize=1000, ySize=400
		// frame.setZoom(20);			//zoom=20
	}
}