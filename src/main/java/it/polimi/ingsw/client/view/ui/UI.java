package it.polimi.ingsw.client.view.ui;

import it.polimi.ingsw.client.view.states.ClientState;
import it.polimi.ingsw.client.view.states.WaitingRoomState;
import it.polimi.ingsw.utils.network.Sendable;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Queue;

public abstract class UI {
    private Queue<Sendable> messages;
    private ClientState state;

    public UI () {
        this.messages = new ArrayDeque<> ();
        this.state = new WaitingRoomState ();
    }

    public abstract void start();

    public abstract void showPersonalBoard();

    public abstract void showGameBoard();

    public abstract void showInfoGame();


    public synchronized Sendable getNextMessage() {
        while (this.messages.size () == 0) {
            try {
                wait ();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }
        return messages.remove ();
    }

    protected synchronized ClientState getState() {
        return state;
    }

    protected synchronized void addMessage(Sendable sendable) {
        this.messages.add (sendable);
        this.notifyAll ();
    }

    public void setNextState() {
        this.state = this.state.getNextState ();
    }

    public abstract void notifyError(String info);

    public abstract void notifyMessage(String info);

    public abstract void nextInputRequest();
}
