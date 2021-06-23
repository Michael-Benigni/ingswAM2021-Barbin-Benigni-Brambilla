package it.polimi.ingsw.client.view.ui.gui;

import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.client.view.exceptions.AlreadyInstantiatedException;
import it.polimi.ingsw.client.view.ui.gui.states.GUIState;
import it.polimi.ingsw.client.view.ui.gui.states.GUIWaitingRoomState;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.utils.network.Sendable;
import javafx.application.Platform;

import java.util.ArrayDeque;

public class GUI implements UI {

    private static GUI instance;
    private GUIState state;
    private Controller controller;
    private final GUIInterlocutor interlocutor;
    private final GUIInterpreter interpreter;
    private final ArrayDeque<Sendable> messages;

    private GUI() {
        this.state = new GUIWaitingRoomState ();
        this.interlocutor = new GUIInterlocutor();
        this.interpreter = new GUIInterpreter();
        this.messages = new ArrayDeque<>();
    }

    public static GUI getInstance() {
        if (instance == null)
            instance = new GUI ();
        return instance;
    }

    @Override
    public void start() {
        new Thread (() -> {
            try {
                JavaFXApp.show ();
            } catch (AlreadyInstantiatedException e) {
                e.printStackTrace ();
            }
        }).start();
    }

    @Override
    public void notifyRoomSizeChanged() {
        Platform.runLater (() -> JavaFXApp.getInstance ().enableButtonStartGame());
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
    public synchronized GUIState getCurrentState() {
        return state;
    }

    @Override
    public void setNextState() {
        Platform.runLater (() -> JavaFXApp.getInstance ().setNextScene ());
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

    @Override
    public void notifyRegistration(boolean isLeader) {
        Platform.runLater (() -> JavaFXApp.getInstance ().setStartMatchScene (isLeader));
    }
}
