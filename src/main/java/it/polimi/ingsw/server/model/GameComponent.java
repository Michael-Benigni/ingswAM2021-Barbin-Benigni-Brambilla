package it.polimi.ingsw.server.model;

import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.Subject;
import it.polimi.ingsw.utils.network.Sendable;
import java.util.ArrayList;

public interface GameComponent extends Subject {

    /**
     * This method calls the method attach for all the observers in the List.
     */
    default void attachAll(ArrayList<Observer> observers) {
        observers.forEach (this::attach);
    }

    @Override
    default void notifyUpdate(Sendable sendable){
        this.getObservers().forEach(observer -> observer.onChanged(sendable));
    }

    Iterable<Observer> getObservers();
}
