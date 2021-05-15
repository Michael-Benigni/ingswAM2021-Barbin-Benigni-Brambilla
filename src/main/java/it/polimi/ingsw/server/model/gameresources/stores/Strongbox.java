package it.polimi.ingsw.server.model.gameresources.stores;


import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.Publisher;

import java.util.ArrayList;

/**
 * Class that represents the player's strongbox on the personal board. It's a collection of resources
 */
public class Strongbox extends UnboundedResourcesContainer implements Cloneable, Publisher {

    private ArrayList<Observer> observers;


    /**
     * Constructor
     */
    public Strongbox () {
        super();
        this.observers = new ArrayList<> ();
    }


    /**
     * @return the cloned object
     */
    @Override
    protected Object clone() {
        return new Strongbox();
    }


    /**
     * This method notifies a change in the status of the publisher to the Observers registered, usually
     */
    @Override
    public void publish() {

    }


    /**
     * This method is used to attach the observer to the object that implements this interface
     *
     * @param observer
     */
    @Override
    public void attach(Observer observer) {
    }
}
