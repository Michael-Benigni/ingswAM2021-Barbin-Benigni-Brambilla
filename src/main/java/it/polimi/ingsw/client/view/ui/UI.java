package it.polimi.ingsw.client.view.ui;

import it.polimi.ingsw.client.view.states.ClientState;
import it.polimi.ingsw.client.view.states.WaitingRoomState;
import it.polimi.ingsw.utils.network.Sendable;

import java.util.ArrayDeque;
import java.util.Queue;

public abstract class UI {
    private Queue<Sendable> messages;
    private ClientState state;
    private boolean readyForNextMove;

    public UI () {
        this.messages = new ArrayDeque<> ();
        this.state = new WaitingRoomState ();
        this.readyForNextMove = false;
    }

    public abstract void start();

    protected boolean isReadyForNextMove() {
        return readyForNextMove;
    }

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

    protected synchronized ClientState getState() {
        return state;
    }

    protected synchronized void addMessage(Sendable sendable) {
        this.messages.add (sendable);
        this.notifyAll ();
        while (this.messages.size () > 0) {
            try {
                wait ();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }
    }

    public void setNextState() {
        this.state = this.state.getNextState ();
    }

    public abstract void notifyError(String info);

    public abstract void notifyMessage(String info);

    public synchronized void ready(boolean YesOrNot) {
        this.readyForNextMove = YesOrNot;
        if (isReadyForNextMove ())
            this.notifyAll ();
    }
}
