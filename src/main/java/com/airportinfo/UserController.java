package com.airportinfo;

import java.util.HashMap;
import java.util.HashSet;

public class UserController {
	private HashSet<Airport> bookMark = new HashSet<Airport>();
	private HashMap<Airport, Integer> rating = new HashMap<Airport, Integer>();
	
	//즐겨찾기 추가
	public void addBookMark(Airport airport){
		bookMark.add(airport);
		System.out.println(bookMark.contains(airport));
	}
	//즐겨찾기 삭제
	public void delBookMark(Airport airport) {
		if (bookMark.contains(airport))
			bookMark.remove(airport);
		System.out.println(bookMark.contains(airport));
	}
	
	//별점기능
	public void addRating(Airport airport, int rate) {
		//별점여부 확인
		if(rating.containsKey(airport)) {
			rating.put(airport, (rate+rating.get(airport))/2);
		}
		else {
			rating.put(airport, rate);
		}
	}
	
//	public static void main(String[] args) {
//		final Airport a = new Airport();
//		final Airport b = new Airport();
//		
//		UserController uc = new UserController();
//		
//		uc.addBookMark(a);
//		uc.addBookMark(b);
//		uc.delBookMark(a);
//		
//		System.out.println(uc.bookMark.size());
//		System.out.println(a.toString());
//		
//		
//	}
}

//airport x -> e.N