package com.airportinfo.controller;

import com.airportinfo.misc.Aspect;
import com.airportinfo.misc.Subject;
import com.airportinfo.model.Airport;
import com.airportinfo.util.SerializeManager;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;

/**
 * User control function.
 *
 * @author ssonseok
 */
public class UserController extends Subject {
    public static final Aspect BOOKMARK_CHANGE = new Aspect("bookmark_change");
    public static final Aspect HISTORY_CHANGE = new Aspect("history_change");
    private final HashSet<Airport> bookmark = new HashSet<>();
    private final HashMap<Airport, Integer> rating = new HashMap<>();

    /**
     * 즐겨찾기 추가. Add bookmark.
     *
     * @param airport Airport to add.
     */
    public void addBookmark(Airport airport) {
        bookmark.add(airport);
        notice(BOOKMARK_CHANGE);
    }

    /**
     * 즐겨찾기 삭제. Delete bookmark.
     *
     * @param airport Airport to remove.
     */
    public void delBookmark(Airport airport) {
        bookmark.remove(airport);
        notice(BOOKMARK_CHANGE);
    }

    /**
     * Delete all bookmarks.
     */
    public void delAllBookmark() {
        bookmark.clear();
        notice(BOOKMARK_CHANGE);
    }

    /**
     * 즐겨찾기 목록 BookmarkList
     *
     * @return bookmark
     */
    public HashSet<Airport> getAllBookmark() {
        return bookmark;
    }

    /**
     * 별점기능 Rating
     *
     * @param airport Airport to rate
     * @param rate    Rate
     */
    public void addRating(Airport airport, int rate) {
        //별점여부 확인
        if (rating.containsKey(airport)) {
            rating.put(airport, (rate + rating.get(airport)) / 2);
        } else {
            rating.put(airport, rate);
        }

    }

    /**
     * 모든 공항 All rating list.
     *
     * @return all airport
     */
    public HashMap<Airport, Integer> getAllRating() {
        return rating;
    }

    /**
     * 내가 원하는 공항 Select airport rating.
     *
     * @param airport Airport to get rating.
     * @return If exists return rate or return -1
     */
    public int getRating(Airport airport) {
        return rating.getOrDefault(airport, -1);
    }
    
    public void saveBookMark() throws IOException {
    	FileOutputStream fos = new FileOutputStream("bookmark.ser");
    	ObjectOutputStream oos = new ObjectOutputStream(fos);
    	oos.writeObject(bookmark);
    	oos.close();
    }
    
    public void loadBookMark() throws IOException, ClassNotFoundException {
    	FileInputStream fis = new FileInputStream("bookmark.ser");
    	ObjectInputStream ois = new ObjectInputStream(fis);
    	bookmark = (HashSet<Airport>) ois.readObject();
    	ois.close();
    }
    
    public void saveRecent() throws IOException {
    	FileOutputStream fos = new FileOutputStream("recent.ser");
    	ObjectOutputStream oos = new ObjectOutputStream(fos);
    	oos.writeObject(recent);
    	oos.close();
    }
    
    public void loadRecent() throws IOException, ClassNotFoundException {
    	FileInputStream fis = new FileInputStream("recent.ser");
    	ObjectInputStream ois = new ObjectInputStream(fis);
    	bookmark = (HashSet<Airport>)ois.readObject();
    	ois.close();
    }
}
