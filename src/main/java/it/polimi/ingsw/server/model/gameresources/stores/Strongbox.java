package it.polimi.ingsw.server.model.gameresources.stores;

import it.polimi.ingsw.server.model.GameComponent;
import it.polimi.ingsw.server.model.exception.NegativeResourceAmountException;
import it.polimi.ingsw.server.model.exception.NotContainedResourceException;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.network.Header;
import it.polimi.ingsw.utils.network.MessageWriter;
import it.polimi.ingsw.utils.network.Sendable;

import java.util.ArrayList;

/**
 * Class that represents the player's strongbox on the personal board. It's a collection of resources
 */
public class Strongbox extends UnboundedResourcesContainer implements Cloneable, GameComponent {

    private ArrayList<Observer> observers;

    public Strongbox() {
        this.observers = new ArrayList<> ();
    }

    @Override
    public void store(StorableResource storableResource){
        super.store(storableResource);
        notifyUpdate(generateUpdate());
    }

    @Override
    public void remove(StorableResource storableResource) throws NegativeResourceAmountException,
            NotContainedResourceException {
        super.remove(storableResource);
        notifyUpdate(generateUpdate());
    }

    private Sendable generateUpdate() {
        MessageWriter writer = new MessageWriter ();
        writer.setHeader (Header.ToClient.STRONGBOX_UPDATE);
        writer.addProperty ("strongbox", this.getAllResources());
        return writer.write ();
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
