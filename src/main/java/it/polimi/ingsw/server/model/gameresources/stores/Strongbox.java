package it.polimi.ingsw.server.model.gameresources.stores;


import it.polimi.ingsw.server.model.GameComponent;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.Subject;

import java.util.ArrayList;

/**
 * Class that represents the player's strongbox on the personal board. It's a collection of resources
 */
public class Strongbox extends UnboundedResourcesContainer implements Cloneable, GameComponent {


    private ArrayList<Observer> observers;

    public Strongbox() {
        this.observers = new ArrayList<> ();
    }

    /**
     * @return the cloned object
     */
    @Override
    protected Object clone() {
        return new Strongbox();
    }

    @Override
    public Iterable<Observer> getObservers() {
        return this.observers;
    }

    /**
     * This method is used to attach the observer to the object that implements this interface
     *
     * @param observer
     */
    @Override
    public void attach(Observer observer) {
        this.observers.add (observer);
    }
}
