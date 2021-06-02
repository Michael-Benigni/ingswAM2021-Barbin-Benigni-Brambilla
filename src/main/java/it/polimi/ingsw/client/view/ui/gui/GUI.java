package it.polimi.ingsw.client.view.ui.gui;

import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.client.view.ui.cli.Interlocutor;
import it.polimi.ingsw.client.view.ui.cli.Interpreter;
import javafx.stage.Stage;

public class GUI extends UI {

    public GUI() {
        super ();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.show ();
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
}
