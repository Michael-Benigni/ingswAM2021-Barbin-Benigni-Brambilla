package it.polimi.ingsw.utils;

import it.polimi.ingsw.utils.network.Sendable;

public interface Subject extends Attachable<Observer> {

    /**
     * This method notifies a change in the status of the publisher to the Observers registered, usually
     */
    void notifyUpdate(Sendable message);
}
