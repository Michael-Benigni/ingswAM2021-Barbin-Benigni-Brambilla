package it.polimi.ingsw.utils;

import it.polimi.ingsw.client.view.ToClient;
import it.polimi.ingsw.server.view.Update;
import it.polimi.ingsw.utils.network.Header;

public interface Subject {

    /**
     * This method notifies a change in the status of the publisher to the Observers registered, usually
     */
    void notifyUpdate(Update update, ToClient header);

    /**
     * This method is used to attach the observer to the object that implements this interface
     * @param observer
     */
    void attach(Observer observer);
}
