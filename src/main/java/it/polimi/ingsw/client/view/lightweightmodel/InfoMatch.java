package it.polimi.ingsw.client.view.lightweightmodel;

import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.utils.Attachable;

import java.util.ArrayList;
import java.util.HashMap;

public class InfoMatch implements Attachable<UI> {
    private int roomID;
    private String yourUsername;
    private String playerPositionInTurn;
    private int waitingRoomSize;
    private final HashMap<Integer, String> positionsAndPlayers;
    private boolean isLeader;
    private UI ui;
    private String currentPlayer;
    private ArrayList<String> users;

    public InfoMatch() {
        this.positionsAndPlayers = new HashMap<> ();
        this.users = new ArrayList<> ();
    }

    public boolean isLeader() {
        return isLeader;
    }

    public String getPlayerPositionInTurn() {
        return playerPositionInTurn;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
        this.ui.onRoomIDChanged();
    }

    public void setYourUsername(String yourUsername) {
        this.yourUsername = yourUsername;
        this.ui.onSetUsername ();
    }

    public void setPlayerPositionInTurn(String numPlayerInTurn) {
        this.playerPositionInTurn = numPlayerInTurn;
        this.ui.onPositionInTurnChanged();
    }

    public void setWaitingRoomSize(int waitingRoomSize) {
        this.waitingRoomSize = waitingRoomSize;
        this.ui.onRoomSizeChanged();
    }

    public String getYourUsername() {
        return yourUsername;
    }

    public ArrayList<String> getOtherPlayersUsernames() {
        return new ArrayList<> (positionsAndPlayers.values ());
    }

    public String getPlayerAt(String positionInGame){
        String player = positionsAndPlayers.get (positionInGame);
        if (player == null)
            player = yourUsername;
        return player;
    }

    public int getRoomID() {
        return roomID;
    }

    public int getWaitingRoomSize() {
        return waitingRoomSize;
    }

    public void putNewPlayer(int position, String name) {
        positionsAndPlayers.put (position, name);
        this.ui.onNewPlayerInGame ();
    }

    public void setIsLeader(boolean isLeader) {
        this.isLeader = isLeader;
        this.ui.onIsLeaderChanged();
    }

    /**
     * This method is used to attach the attached to the object that implements this interface
     *
     * @param attached
     */
    @Override
    public void attach(UI attached) {
        this.ui = attached;
    }

    public void setCurrentPlayer(String playerPositionInTurn, String additionalInfo) {
        this.currentPlayer = playerPositionInTurn;
        this.ui.onCurrentPlayerChanged(additionalInfo);
    }

    public String getCurrentPlayerPos() {
        return currentPlayer;
    }

    public void putNewUser(String name) {
        this.users.add(name);
        this.ui.onUserInRoomEnteredOrDisconnected();
    }

    public ArrayList<String> getUsers(){
        return this.users;
    }

    public void removeUserFromRoom(String playerDisconnected) {
        this.users.remove(playerDisconnected);
        this.ui.onUserInRoomEnteredOrDisconnected();
    }
}
