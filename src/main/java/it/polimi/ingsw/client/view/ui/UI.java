package it.polimi.ingsw.client.view.ui;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.ui.cli.Interlocutor;
import it.polimi.ingsw.client.view.ui.cli.Interpreter;
import it.polimi.ingsw.utils.network.Sendable;

public interface UI {

    void start() throws Exception;

    void showPersonalBoard();

    void showGameBoard();

    void showInfoGame();

    Sendable getNextMessage();

    void setNextState();

    void notifyError(String info);

    void notifyMessage(String info);

    void nextInputRequest();

    void setView(View view);

    Interlocutor getInterlocutor();

    Interpreter getInterpreter();

    View getView();
}
