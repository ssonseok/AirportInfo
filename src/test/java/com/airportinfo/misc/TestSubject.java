package com.airportinfo.misc;

public class TestSubject extends Subject {
    public static final Aspect PARENT = new Aspect("parent");
    public static final Aspect CHILD = new Aspect(PARENT, "child");
}
