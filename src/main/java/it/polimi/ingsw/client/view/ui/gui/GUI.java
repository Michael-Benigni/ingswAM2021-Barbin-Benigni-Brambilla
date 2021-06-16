package it.polimi.ingsw.client.view.ui.gui;

import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.client.view.states.ClientState;
import it.polimi.ingsw.client.view.states.GUIState;
import it.polimi.ingsw.client.view.states.GUIWaitingRoomState;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.client.view.ui.cli.Interlocutor;
import it.polimi.ingsw.client.view.ui.cli.Interpreter;
import it.polimi.ingsw.utils.network.Sendable;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayDeque;
import java.util.Queue;

public class GUI extends Application implements UI {

    private final Queue<Sendable> messages;
    private GUIState state;
    private Controller controller;

    public GUI() {
        this.messages = new ArrayDeque<> ();

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.state = new GUIWaitingRoomState();
        primaryStage.setTitle("Master Of Renaissance");
        primaryStage.setScene(state.getScene());


        primaryStage.show ();
    }

    @Override
    public void start() throws Exception {
        launch ();
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
    public Interlocutor getInterlocutor() {
        return null;
    }

    @Override
    public Interpreter getInterpreter() {
        return null;
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

    protected synchronized ClientState getState() {
        return state;
    }

    protected synchronized void addMessage(Sendable sendable) {
        this.messages.add (sendable);
        this.notifyAll ();
    }

    @Override
    public void setNextState() {
    }

    @Override
    public void printMenu() {

    }

    @Override
    public void setView(Controller controller) {
        this.controller = controller;
    }

    @Override
    public Controller getView() {
        return controller;
    }
}
