package it.polimi.ingsw.client.view.ui;

import it.polimi.ingsw.client.view.states.ClientState;
import it.polimi.ingsw.client.view.states.WaitingRoomState;
import it.polimi.ingsw.utils.network.Sendable;

import java.util.ArrayDeque;
import java.util.Queue;

public abstract class UI {
    private Queue<Sendable> userInputs;
    private ClientState state;

    public UI () {
        this.userInputs = new ArrayDeque<> ();
        this.state = new WaitingRoomState ();
    }

    public abstract void start();

    public synchronized Sendable getUserInput() {
        while (this.userInputs.isEmpty ()) {
            try {
                wait ();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }
        return this.userInputs.remove ();
    }

    public synchronized ClientState getState() {
        return state;
    }

    protected synchronized void addUserInput(Sendable sendable) {
        this.userInputs.add (sendable);
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
