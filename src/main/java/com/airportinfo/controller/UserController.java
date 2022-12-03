package com.airportinfo.controller;

import com.airportinfo.misc.Aspect;
import com.airportinfo.misc.Subject;
import com.airportinfo.model.Airport;

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
}
