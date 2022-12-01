package com.airportinfo.misc;

public class TestObject implements Observer {
    private final String message;

    public TestObject(String message) {
        this.message = message;
    }

    @Override
    public void update() {
        System.out.println(message);
    }
}
