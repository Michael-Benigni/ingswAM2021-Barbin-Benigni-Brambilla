package it.polimi.ingsw.client.view.lightweightmodel;

import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.utils.Attachable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that represents the collection of information about the game.
 * There will be an instance of this class for each player in this game.
 */
public class InfoMatch implements Attachable<UI> {

    /**
     * Identification number for the instance of the game.
     */
    private int roomID;

    /**
     * Username of the player linked to this GameInfo object.
     */
    private String yourUsername;

    /**
     * Position of this player in the players order.
     */
    private String playerPositionInTurn;

    /**
     * Number of players set for this game.
     */
    private int waitingRoomSize;

    /**
     * Map that represents the players of the game with the play order.
     */
    private final HashMap<Integer, String> positionsAndPlayers;

    /**
     * True if this players is the creator of the game.
     */
    private boolean isLeader;

    private UI ui;
    private String currentPlayer;

    /**
     * List of players in this game.
     */
    private ArrayList<String> users;

    /**
     * Constructor method of this class. It sets two empty arrays.
     */
    public InfoMatch() {
        this.positionsAndPlayers = new HashMap<> ();
        this.users = new ArrayList<> ();
    }

    /**
     * Getter method for the boolean that represents if this player is the creator of the game.
     */
    public boolean isLeader() {
        return isLeader;
    }

    /**
     * Getter method for the position of this player in the players order.
     */
    public String getPlayerPositionInTurn() {
        return playerPositionInTurn;
    }

    /**
     * Method that updates the ID of the room.
     */
    public void setRoomID(int roomID) {
        this.roomID = roomID;
        this.ui.onRoomIDChanged();
    }

    /**
     * Method that updates the username of this player.
     */
    public void setYourUsername(String yourUsername) {
        this.yourUsername = yourUsername;
        this.ui.onSetUsername ();
    }

    /**
     * Method that updates the position of this player.
     */
    public void setPlayerPositionInTurn(String numPlayerInTurn) {
        this.playerPositionInTurn = numPlayerInTurn;
        this.ui.onPositionInTurnChanged();
    }

    /**
     * Method that updates the number of players of this game.
     */
    public void setWaitingRoomSize(int waitingRoomSize) {
        this.waitingRoomSize = waitingRoomSize;
        this.ui.onRoomSizeChanged();
    }

    /**
     * Getter method for the username of this player.
     */
    public String getYourUsername() {
        return yourUsername;
    }

    /**
     * Getter method for the other players' username.
     */
    public ArrayList<String> getOtherPlayersUsernames() {
        return new ArrayList<> (positionsAndPlayers.values ());
    }

    /**
     * Getter method for a specific player.
     * @param positionInGame position of the player, in the players order, you want to be returned
     */
    public String getPlayerAt(String positionInGame){
        String player = positionsAndPlayers.get (positionInGame);
        if (player == null)
            player = yourUsername;
        return player;
    }

    /**
     * Getter method for the ID of this room.
     */
    public int getRoomID() {
        return roomID;
    }

    /**
     * Getter method for the number of players in this game.
     */
    public int getWaitingRoomSize() {
        return waitingRoomSize;
    }

    /**
     * Method that adds a new player to the map of players.
     */
    public void putNewPlayer(int position, String name) {
        positionsAndPlayers.put (position, name);
        this.ui.onNewPlayerInGame ();
    }

    /**
     * Method that updates the boolean -> true if this player is the creator of the game, false otherwise.
     */
    public void setIsLeader(boolean isLeader) {
        this.isLeader = isLeader;
        this.ui.onIsLeaderChanged();
    }

    /**
     * This method is used to attach the attached to the object that implements this interface.
     */
    @Override
    public void attach(UI attached) {
        this.ui = attached;
    }

    /**
     * Method that updates the current player.
     */
    public void setCurrentPlayer(String playerPositionInTurn, String additionalInfo) {
        this.currentPlayer = playerPositionInTurn;
        this.ui.onCurrentPlayerChanged(additionalInfo);
    }

    /**
     * Getter method for the current player.
     */
    public String getCurrentPlayerPos() {
        return currentPlayer;
    }

    /**
     * Method that adds a new player to the array of users.
     */
    public void putNewUser(String name) {
        this.users.add(name);
        this.ui.onUserInRoomEnteredOrDisconnected();
    }

    /**
     * Getter method for the array of users connected to this room.
     */
    public ArrayList<String> getUsers(){
        return this.users;
    }

    /**
     * Method that removes one player from the room after a disconnection.
     */
    public void removeUserFromRoom(String playerDisconnected) {
        this.users.remove(playerDisconnected);
        this.ui.onUserInRoomEnteredOrDisconnected();
    }
}
