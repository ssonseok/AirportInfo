package com.airportinfo.misc;

import java.util.HashMap;

/**
 * Subject observers watching.
 *
 * @author lalaalal
 */
public abstract class Subject {
    private final HashMap<Observer, Aspect> observers = new HashMap<>();

    /**
     * Register a new observer with aspect.
     *
     * @param observer New observer
     * @param aspect Aspect for observer
     */
    public void attach(Observer observer, Aspect aspect) {
        observers.put(observer, aspect);
    }

    /**
     * Register a new observer with general aspect.
     *
     * @param observer New observer
     */
    public void attach(Observer observer) {
        attach(observer, Aspect.GENERAL_ASPECT);
    }

    /**
     * Unregister observer.
     *
     * @param observer Observer to remove
     */
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notice all observers with following aspect.
     *
     * @param aspect Aspect for observer
     */
    public void notice(Aspect aspect) {
        for (Observer observer : observers.keySet()) {
            Aspect observerAspect = observers.get(observer);
            if (observerAspect.equals(aspect))
                observer.update();
        }
    }

    /**
     * Notice all observers with general aspect.
     */
    public void notice() {
        notice(Aspect.GENERAL_ASPECT);
    }
}
