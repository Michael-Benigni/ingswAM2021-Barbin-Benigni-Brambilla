package it.polimi.ingsw.client.view.ui;

import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.client.view.ui.cli.states.ClientState;
import it.polimi.ingsw.utils.network.Sendable;

import java.util.ArrayList;

public interface UI {

    ClientState getCurrentState();

    void showPersonalBoard();

    void onGameOver(ArrayList<String> winnersNames, ArrayList<String> losersNames, ArrayList<Integer> winnersVPs, ArrayList<Integer> losersVPs, String addInfo);

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

    void notifyRegistration(boolean isLeader, int orderOfRegistration);

    void start();

    void onMarketChanged();

    void onFaithTrackChanged();

    void onPlayerPositionFaithTrackChanged();

    void onCardsGridBuilt();

    void onCardBoughtFromGrid();

    void onRoomIDChanged();

    void onSetUsername();

    void onPositionInTurnChanged();

    void onRoomSizeChanged();

    void onNewPlayerInGame();

    void onIsLeaderChanged();

    void onLeaderDisconnected();

    void onWarehouseChanged();

    void onStrongboxChanged();

    void onTempContainerChanged();

    void onSlotDevCardsChanged();

    void onSlotLeaderCardsChanged();

    void onWMPowerChanged();

    void onXPowersChanged();

    void onCurrentPlayerChanged(String additionalMsg);

    void notifyRoomFull();

    void onUserInRoomEnteredOrDisconnected(boolean entered);

    void notifyErrorConnection();

    void connectionSuccessful();
}
