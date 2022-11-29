package com.airportinfo;

import com.airportinfo.util.GoogleMapFrame;
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
		GoogleMapFrame frame = new GoogleMapFrame();
		
		/**
		 * if you want to see only map 
		 * (To view airport maps directly without searching)
		 * 
		 * String location="--------";
		 * GoogleMapFrame frame = new GoogleMapFrame(location);
		 */
		//String location="--------";
		//GoogleMapFrame frame = new GoogleMapFrame(location);
	}
}