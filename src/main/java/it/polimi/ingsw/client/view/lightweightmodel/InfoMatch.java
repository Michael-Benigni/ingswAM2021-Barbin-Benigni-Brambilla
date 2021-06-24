package it.polimi.ingsw.client.view.lightweightmodel;

import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.utils.Attachable;

import java.util.ArrayList;
import java.util.HashMap;

public class InfoMatch implements Attachable<UI> {
    private int roomID;
    private String yourUsername;
    private int playerPositionInTurn;
    private int waitingRoomSize;
    private HashMap<Integer, String> positionsAndPlayers;
    private boolean isLeader;
    private UI ui;
    private int currentPlayer;

    public boolean isLeader() {
        return isLeader;
    }

    public InfoMatch() {
        this.positionsAndPlayers = new HashMap<> ();
    }

    public int getPlayerPositionInTurn() {
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

    public void setPlayerPositionInTurn(int numPlayerInTurn) {
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

    public String getPlayerAt(int positionInGame){
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

    public int getNumCurrentPlayersInRoom(){
        return positionsAndPlayers.size();
    }

    public void putNewPlayer(int position, String name) {
        positionsAndPlayers.put (position, name);
        this.ui.onNewPlayerInRoom();
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

    public void setCurrentPlayer(int playerPositionInTurn) {
        this.currentPlayer = playerPositionInTurn;
        this.ui.onCurrentPlayerChanged();
    }

    public int getCurrentPlayerPos() {
        return currentPlayer;
    }
}
