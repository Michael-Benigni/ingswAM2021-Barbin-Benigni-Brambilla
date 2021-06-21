package it.polimi.ingsw.client.view.ui.gui;

import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.client.view.states.GUIState;
import it.polimi.ingsw.client.view.states.GUIWaitingRoomState;
import it.polimi.ingsw.client.view.ui.JavaFXApp;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.utils.network.Sendable;
import javafx.application.Platform;

import java.util.ArrayDeque;

public class GUI implements UI {

    private GUIState state;
    private Controller controller;
    private final GUIInterlocutor interlocutor;
    private final GUIInterpreter interpreter;
    private final ArrayDeque<Sendable> messages;

    public GUI() {
        this.state = new GUIWaitingRoomState ();
        this.interlocutor = new GUIInterlocutor();
        this.interpreter = new GUIInterpreter();
        this.messages = new ArrayDeque<>();
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
        Platform.runLater (() -> Popup.alert ("ERROR", info));
    }

    @Override
    public void notifyMessage(String info) {
        Platform.runLater (() -> Popup.alert ("INFO", info));
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

    @Override
    public synchronized GUIState getState() {
        return state;
    }

    @Override
    public void setNextState() {
        Platform.runLater (() -> getState ());
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
