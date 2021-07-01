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
    public void onMarketChanged() {
        Platform.runLater (() -> GUIPlayState.getInstance ().initMarketGrid ());
    }

    @Override
    public void onFaithTrackChanged() {

    }

    @Override
    public void onPlayerPositionFaithTrackChanged() {

    }

    @Override
    public void onCardsGridBuilt() {
        Platform.runLater (() -> {
            try {
                GUIPlayState.getInstance ().initCardsGrid ();
            } catch (IllegalInputException thrownByGetCardOfCardsGrid) {
                thrownByGetCardOfCardsGrid.printStackTrace();
            }
        });
    }

    @Override
    public void onCardBoughtFromGrid() {
        onCardsGridBuilt ();
    }

    @Override
    public void onRoomIDChanged() {
        GUIWaitingRoomState.getInstance().updateRoomIDLabelInMatchSettings(this);
    }

    @Override
    public void onSetUsername() {

    }

    @Override
    public void onPositionInTurnChanged() {

    }

    @Override
    public void onRoomSizeChanged() {
        Platform.runLater(() -> GUIWaitingRoomState.getInstance().notifyNewRoomSize(this));
    }

    @Override
    public void onNewPlayerInGame() {

    }

    public void notifyRoomFull() {
        Platform.runLater (() -> GUIWaitingRoomState.getInstance ().enableButtonStartGame());
    }

    @Override
    public void notifyErrorConnection() {
        String info = "Connection Refused. The Server is not working.\n Try again in few minutes.";
        Platform.runLater (() -> Popup.alert ("ERROR", info, e -> JavaFXApp.getInstance ().getWindow().close()));
    }

    @Override
    public void connectionSuccessful() {

    }

    @Override
    public void onUserInRoomEnteredOrDisconnected(boolean entered) {
        Platform.runLater(() ->
                GUIWaitingRoomState.getInstance().notifyNewPlayerInRoom(this.controller.getModel().getInfoMatch()));
    }

    @Override
    public void onIsLeaderChanged() {
        Platform.runLater(() -> JavaFXApp.getInstance().setSecondScene(controller.getModel().getInfoMatch().isLeader()));
    }

    @Override
    public void onWarehouseChanged() {
        Platform.runLater (() -> {
            GUIPlayState.getInstance ().initWarehouse();
        });
    }

    @Override
    public void onLeaderDisconnected(){
        Platform.runLater(() -> {
            JavaFXApp.getInstance().goToSecondSceneOnLeaderChanged();
            notifyMessage("You are the new leader of the party");
        });



    }

    @Override
    public void onStrongboxChanged() {
        Platform.runLater(() -> GUIPlayState.getInstance().initStrongbox());
    }

    @Override
    public void onTempContainerChanged() {
        Platform.runLater (() -> {
            GUIPlayState.getInstance ().initTempContainer ();
        });
    }

    @Override
    public void onSlotDevCardsChanged() {
        Platform.runLater(() -> GUIPlayState.getInstance().initSlots());
    }

    @Override
    public void onSlotLeaderCardsChanged() {
        Platform.runLater (() -> {
            GUIPlayState.getInstance ().initSlotLeaderCards ();
        });
    }

    @Override
    public void onWMPowerChanged() {

    }

    @Override
    public void onXPowersChanged() {

    }

    @Override
    public void onCurrentPlayerChanged(String additionalMsg) {
        if(controller.getModel().getInfoMatch().getPlayerAt(controller.getModel().getInfoMatch().getCurrentPlayerPos()).
                equals(controller.getModel().getInfoMatch().getYourUsername())){
            Platform.runLater(() -> GUIPlayState.getInstance().resetButtonsForTurnChanging());
        }
    }


    @Override
    public void showPersonalBoard() {

    }

    @Override
    public void onGameOver(ArrayList<String> winnersNames, ArrayList<String> losersNames,
                           ArrayList<Integer> winnersVPs, ArrayList<Integer> losersVPs, String addInfo) {

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
        state = state.getNextState ();
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
    public void notifyRegistration(boolean isLeader, int orderOfRegistration) {
        Platform.runLater (() -> JavaFXApp.getInstance ().setNextScene ());
    }
}
