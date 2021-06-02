package it.polimi.ingsw.client.view.ui;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.states.ClientState;
import it.polimi.ingsw.client.view.states.WaitingRoomState;
import it.polimi.ingsw.client.view.ui.cli.Interlocutor;
import it.polimi.ingsw.client.view.ui.cli.Interpreter;
import it.polimi.ingsw.utils.network.Sendable;
import javafx.application.Application;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Queue;

public abstract class UI extends Application {
    private Queue<Sendable> messages;
    private ClientState state;
    private View view;

    public UI() {
        this.messages = new ArrayDeque<> ();
        this.state = new WaitingRoomState ();
    }

    public abstract void start() throws Exception;

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

    public void setView(View view) {
        this.view = view;
    }

    protected View getView() {
        return view;
    }

    public abstract Interlocutor getInterlocutor();

    public abstract Interpreter getInterpreter();
}
