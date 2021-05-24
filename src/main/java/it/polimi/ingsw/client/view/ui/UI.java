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

    public synchronized Sendable getNextMessage() {
        while (this.messages.isEmpty ()) {
            try {
                wait ();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }
        notifyAll ();
        return this.messages.remove ();
    }

    public synchronized ClientState getState() {
        return state;
    }

    protected synchronized void addMessage(Sendable sendable) {
        this.messages.add (sendable);
        this.notifyAll ();
        try {
            wait ();
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
    }

    public void setNextState() {
        this.state = this.state.getNextState ();
    }

    public abstract void notifyError(String info);
}
