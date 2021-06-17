package it.polimi.ingsw.client.view.ui.gui;

import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.client.view.states.ClientState;
import it.polimi.ingsw.client.view.states.GUIState;
import it.polimi.ingsw.client.view.states.GUIWaitingRoomState;
import it.polimi.ingsw.client.view.ui.JavaFXApp;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.utils.network.Sendable;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.ArrayDeque;

public class GUI implements UI {

    private GUIState state;
    private Controller controller;
    private final GUIInterlocutor interlocutor;
    private final GUIInterpreter interpreter;
    private final ArrayDeque<Sendable> messages;

    public GUI() {
        interlocutor = new GUIInterlocutor();
        interpreter = new GUIInterpreter();
        messages = new ArrayDeque<>();
        new Thread (JavaFXApp::show).start();
    }


    @Override
    public void showPersonalBoard() {

    }

    @Override
    public void showGameBoard() {

    }

    @Override
    public void showInfoGame() {

    }

    @Override
    public void notifyError(String info) {

    }

    @Override
    public void notifyMessage(String info) {

    }

    @Override
    public void nextInputRequest() {

    }

    @Override
    public GUIInterlocutor getInterlocutor() {
        return interlocutor;
    }

    @Override
    public GUIInterpreter getInterpreter() {
        return interpreter;
    }

    protected synchronized ClientState getState() {
        return state;
    }

    @Override
    public void setNextState() {
    }


    @Override
    public void printMenu() {

    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public Controller getController() {
        return controller;
    }

    @Override
    public synchronized void addMessage(Sendable sendable) {
        this.messages.addLast (sendable);
        notifyAll ();
    }

    @Override
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
}
