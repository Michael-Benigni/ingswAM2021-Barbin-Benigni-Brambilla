package it.polimi.ingsw.client.view.ui;

import it.polimi.ingsw.client.view.states.ClientState;
import it.polimi.ingsw.client.view.states.WaitingRoomState;
import it.polimi.ingsw.utils.network.Sendable;

import java.util.ArrayDeque;
import java.util.Queue;

public abstract class UI {
    private Queue<Sendable> messages;
    private ClientState state;

    public UI () {
        this.messages = new ArrayDeque<> ();
        this.state = new WaitingRoomState ();
    }

    public abstract void start();

    public synchronized Sendable getUserInput() {
        while (this.messages.isEmpty ()) {
            try {
                wait ();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }
        return this.messages.remove ();
    }

    public synchronized ClientState getState() {
        return state;
    }

    public synchronized void setNextState() {
        this.state = getState ().getNextState ();
    }

    protected synchronized void addMessage(Sendable sendable) {
        this.messages.add (sendable);
        try {
            wait ();
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
    }

    public void notifyStateChange(ClientState state) {
        state.getOptions ();
    }
}
