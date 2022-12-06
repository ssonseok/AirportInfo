package com.airportinfo.controller;

import java.io.IOException;

import com.airportinfo.model.Airport;
import com.airportinfo.model.RawAirport;

public class UserControllerTest {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		UserController uc = new UserController();
		String[] str = new String[7];
		RawAirport ra = new RawAirport();
		ra.englishName = "korea";
		ra.koreanName = "한국";
		ra.IATA = "IATA";
		ra.ICAO = "ICAO";
		ra.koreanRegion = "koreanRegion";
		ra.englishCountryName = "engCountryName";
		ra.koreanCountryName = "korCountryName";
		ra.englishCityName = "engCityName";
//		Airport ap = new Airport(ra);
//		uc.addBookmark(ap);
//		System.out.println(uc.getAllBookmark());
//		uc.saveBookMark();
//		uc.delBookmark(ap);
//		System.out.println(uc.getAllBookmark());
//		uc.loadBookMark();
//		System.out.println(uc.getAllBookmark());
		
		/*
		 airport.englishName = items[0];
        airport.koreanName = items[1];
        airport.IATA = items[2];
        airport.ICAO = items[3];
        airport.koreanRegion = items[4];
        airport.englishCountryName = items[5];
        airport.koreanCountryName = items[6];
        airport.englishCityName = items[7];
		 */
	}

}