package it.polimi.ingsw.client.view.ui.gui;

import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.client.view.exceptions.AlreadyInstantiatedException;
import it.polimi.ingsw.client.view.exceptions.IllegalInputException;
import it.polimi.ingsw.client.view.ui.gui.states.GUIPlayState;
import it.polimi.ingsw.client.view.ui.gui.states.GUIState;
import it.polimi.ingsw.client.view.ui.gui.states.GUIWaitingRoomState;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.utils.network.QuitMessage;
import it.polimi.ingsw.utils.network.Sendable;
import javafx.application.Platform;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class GUI implements UI {

    private static GUI instance;
    private GUIState state;
    private Controller controller;
    private final GUIInterlocutor interlocutor;
    private final GUIInterpreter interpreter;
    private final ArrayDeque<Sendable> messages;

    private GUI() {
        this.state = GUIWaitingRoomState.getInstance();
        this.interlocutor = new GUIInterlocutor();
        this.interpreter = new GUIInterpreter();
        this.messages = new ArrayDeque<>();
    }

    public synchronized static GUI getInstance() {
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
    public synchronized void onMarketChanged() {
        Platform.runLater (() -> GUIPlayState.getInstance ().initMarketGrid ());
    }

    @Override
    public synchronized void onFaithTrackChanged() {

    }

    @Override
    public synchronized void onPlayerPositionFaithTrackChanged() {
        Platform.runLater(() -> GUIPlayState.getInstance().initFaithTrack());
    }

    @Override
    public synchronized void onCardsGridBuilt() {
        Platform.runLater (() -> {
            try {
                GUIPlayState.getInstance ().initCardsGrid ();
            } catch (IllegalInputException thrownByGetCardOfCardsGrid) {
                thrownByGetCardOfCardsGrid.printStackTrace();
            }
        });
    }

    @Override
    public synchronized void onCardBoughtFromGrid() {
        onCardsGridBuilt ();
    }

    @Override
    public synchronized void onRoomIDChanged() {
        GUIWaitingRoomState.getInstance().updateRoomIDLabelInMatchSettings(this);
    }

    @Override
    public synchronized void onSetUsername() {

    }

    @Override
    public synchronized void onPositionInTurnChanged() {

    }

    @Override
    public synchronized void onRoomSizeChanged() {
        Platform.runLater(() -> GUIWaitingRoomState.getInstance().notifyNewRoomSize(this));
    }

    @Override
    public synchronized void onNewPlayerInGame() {

    }

    public synchronized void notifyRoomFull() {
        Platform.runLater (() -> GUIWaitingRoomState.getInstance ().enableButtonStartGame());
    }

    @Override
    public synchronized void notifyErrorConnection() {
        String info = "Connection Refused. The Server is not working.\n Try again in few minutes.";
        Platform.runLater (() -> Popup.alert ("ERROR", info, e -> JavaFXApp.getInstance ().getWindow().close()));
    }

    @Override
    public synchronized void connectionSuccessful() {

    }

    @Override
    public synchronized void onUserInRoomEnteredOrDisconnected(boolean entered) {
        Platform.runLater(() ->
                GUIWaitingRoomState.getInstance().notifyNewPlayerInRoom(this.controller.getModel().getInfoMatch()));
    }

    @Override
    public synchronized void onIsLeaderChanged() {
        Platform.runLater(() -> JavaFXApp.getInstance().setSecondScene(controller.getModel().getInfoMatch().isLeader()));
    }

    @Override
    public synchronized void onWarehouseChanged() {
        Platform.runLater (() -> {
            GUIPlayState.getInstance ().initWarehouse();
        });
    }

    @Override
    public synchronized void onLeaderDisconnected(){
        Platform.runLater(() -> {
            JavaFXApp.getInstance().goToSecondSceneOnLeaderChanged();
            notifyMessage("You are the new leader of the party");
        });



    }

    @Override
    public synchronized void onStrongboxChanged() {
        Platform.runLater(() -> GUIPlayState.getInstance().initStrongbox());
    }

    @Override
    public synchronized void onTempContainerChanged() {
        Platform.runLater (() -> {
            GUIPlayState.getInstance ().initTempContainer ();
        });
    }

    @Override
    public synchronized void onSlotDevCardsChanged() {
        Platform.runLater(() -> GUIPlayState.getInstance().initSlots());
    }

    @Override
    public synchronized void onSlotLeaderCardsChanged() {
        Platform.runLater (() -> {
            GUIPlayState.getInstance ().initSlotLeaderCards ();
        });
    }

    @Override
    public synchronized void onWMPowerChanged() {

    }

    @Override
    public synchronized void onXPowersChanged() {
        Platform.runLater(() -> GUIPlayState.getInstance().initExtraProductionPowers());
    }

    @Override
    public synchronized void onCurrentPlayerChanged(String additionalMsg) {
        if(controller.getModel().getInfoMatch().getPlayerAt(controller.getModel().getInfoMatch().getCurrentPlayerPos()).
                equals(controller.getModel().getInfoMatch().getYourUsername())){
            Platform.runLater(() -> GUIPlayState.getInstance().resetButtonsForTurnChanging());
        }
    }


    @Override
    public synchronized void showPersonalBoard() {

    }

    @Override
    public synchronized void onGameOver(ArrayList<String> winnersNames, ArrayList<String> losersNames,
                           ArrayList<Integer> winnersVPs, ArrayList<Integer> losersVPs, String addInfo) {

    }

    @Override
    public synchronized void showGameBoard() {

    }

    @Override
    public synchronized  void showInfoGame() {

    }

    @Override
    public synchronized void notifyError(String info) {
        Platform.runLater (() -> Popup.alert ("ERROR", info));
    }

    @Override
    public synchronized void notifyMessage(String info) {
        Platform.runLater (() -> Popup.alert ("INFO", info));
    }

    @Override
    public synchronized void nextInputRequest() {

    }

    @Override
    public synchronized GUIInterlocutor getInterlocutor() {
        return interlocutor;
    }

    @Override
    public synchronized GUIInterpreter getInterpreter() {
        return interpreter;
    }

    @Override
    public synchronized GUIState getCurrentState() {
        return state;
    }

    @Override
    public synchronized void setNextState() {
        state = state.getNextState ();
        Platform.runLater (() -> JavaFXApp.getInstance ().setNextScene ());
    }


    @Override
    public synchronized void printMenu() {

    }

    @Override
    public synchronized void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public synchronized Controller getController() {
        return controller;
    }

    @Override
    public synchronized void addMessage(Sendable sendable) {
        this.messages.addLast (sendable);
        notifyAll ();
        if (QuitMessage.isQuitMessage (sendable.transmit ()))
            Thread.currentThread ().interrupt ();
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
    public synchronized void notifyRegistration(boolean isLeader, int orderOfRegistration) {
        Platform.runLater (() -> JavaFXApp.getInstance ().setNextScene ());
    }
}
