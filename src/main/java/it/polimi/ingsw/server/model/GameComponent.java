package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.view.Update;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.Subject;
import it.polimi.ingsw.utils.network.Header;
import it.polimi.ingsw.utils.network.ToClientMessage;

import java.util.ArrayList;

public abstract class GameComponent  implements Subject{
    private ArrayList<Observer> observers;
    
    protected GameComponent () {
        this.observers = new ArrayList<> ();
    }
    
    @Override
    public void attach(Observer observer) {
        this.observers.add (observer);        
    }

    /**
     * This method calls the method attach for all the observers in the List.
     */
    protected void attachAll() {
        this.observers.forEach (this::attach);
    }

    @Override
    public void notifyUpdate(Update update, Header.ToClient header){
        this.observers.forEach((observer -> observer.onChanged(new ToClientMessage(header, update))));
    }
}
