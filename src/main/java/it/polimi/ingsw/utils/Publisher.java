package it.polimi.ingsw.utils;

import java.util.List;

public interface Publisher {

    /**
     * This method notifies a change in the status of the publisher to the Observers registered, usually
     */
    void publish();

    /**
     * This method is used to attach the observer to the object that implements this interface
     * @param observer
     */
    void attach(Observer observer);

    /**
     * This method uses the method attach for all the observers in the List.
     * @see Publisher
     * @param observers
     */
    default void attachAll(List<Observer> observers) {
        observers.forEach (this::attach);
    }
}
