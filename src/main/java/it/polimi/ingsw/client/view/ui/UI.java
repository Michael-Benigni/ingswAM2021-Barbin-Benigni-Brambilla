package it.polimi.ingsw.client.view.ui;

import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.client.view.ui.cli.Interlocutor;
import it.polimi.ingsw.client.view.ui.cli.Interpreter;
import it.polimi.ingsw.utils.network.Sendable;

public interface UI {

    void showPersonalBoard();

    void showGameBoard();

    void showInfoGame();

    void setNextState();

    void printMenu();

    void notifyError(String info);

    void notifyMessage(String info);

    void nextInputRequest();

    void setController(Controller controller);

    Interlocutor getInterlocutor();

    Interpreter getInterpreter();

    Controller getController();

    void addMessage(Sendable sendable);


    Sendable getNextMessage();
}
