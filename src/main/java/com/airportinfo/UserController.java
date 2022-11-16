package com.airportinfo;

import java.util.HashMap;
import java.util.HashSet;

/**
 * User control function.
 *
 * @author lalaalal
 */
public class UserController {
	private HashSet<Airport> bookmark = new HashSet<Airport>();
	private HashMap<Airport, Integer> rating = new HashMap<Airport, Integer>();
	
	//즐겨찾기 추가
	/**
	 * Add bookmark.
	 * 
	 * @param airport
	 */
	public void addBookmark(Airport airport){
		bookmark.add(airport);
		System.out.println(bookmark.contains(airport));
	}
	//즐겨찾기 삭제
	/**
	 * Delete bookmark.
	 * 
	 * @param airport
	 */
	public void delBookmark(Airport airport) {
		if (bookmark.contains(airport))
			bookmark.remove(airport);
		System.out.println(bookmark.contains(airport));
	}
	
	//즐겨찾기 목록
	/**
	 * BookmarkList
	 * 
	 * @return bookmark
	 */
	public HashSet<Airport> getAllBookmark(){
		return bookmark;
	}
	
	//별점기능
	/**
	 * Rating
	 * 
	 * @param airport
	 * @param rate
	 */
	public void addRating(Airport airport, int rate) {
		//별점여부 확인
		if(rating.containsKey(airport)) {
			rating.put(airport, (rate+rating.get(airport))/2);
		}
		else {
			rating.put(airport, rate);
		}
		
	}
	/**
	 * All rating list.
	 * 
	 * @return all airport
	 */
	//모든 공항
	public HashMap<Airport, Integer> getAllRating() {
		return rating;
	}
	//내가 원하는 공항
	/**
	 * Select airport rating.
	 * 
	 * @param airport
	 * @return exist - rate
	 * @return nonexistent - -1
	 */
	public int getRating(Airport airport) {
		
		if(rating.containsKey(airport)) {
			int rate = (Integer) rating.get(airport);	
			return rate;
		}
		
		else {
			return -1;
		}
	
		
	}
	
//	public static void main(String[] args) {
//		final Airport a = new Airport();
//		final Airport b = new Airport();
//		
//		UserController uc = new UserController();
//		
//		uc.addBookmark(a);
//		uc.addBookmark(b);
//		uc.delBookmark(a);
//		
//		System.out.println(uc.bookmark.size());
//		System.out.println(a.toString());
//		
//		
//	}
}
