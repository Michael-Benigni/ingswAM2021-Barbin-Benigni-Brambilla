package it.polimi.ingsw.utils;

public interface Subject {

    /**
     * This method notifies a change in the status of the publisher to the Observers registered, usually
     */
    void notifyEvent();

    /**
     * This method is used to attach the observer to the object that implements this interface
     * @param observer
     */
    void attach(Observer observer);
}
