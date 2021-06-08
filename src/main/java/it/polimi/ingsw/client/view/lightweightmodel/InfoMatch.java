package it.polimi.ingsw.client.view.lightweightmodel;

import java.util.ArrayList;

public class InfoMatch {
    private int roomID;
    private String yourUsername;
    private int numPlayerInTurn;
    private int totalNumPlayers;
    private ArrayList<String> otherPlayersUsernames;

    public InfoMatch() {
        this.otherPlayersUsernames = new ArrayList<> ();
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public void setYourUsername(String yourUsername) {
        this.yourUsername = yourUsername;
    }

    public void setNumPlayerInTurn(int numPlayerInTurn) {
        this.numPlayerInTurn = numPlayerInTurn;
    }

    public void setTotalNumPlayers(int totalNumPlayers) {
        this.totalNumPlayers = totalNumPlayers;
    }

    public void addOtherPlayerUsername(String otherPlayersUsernames) {
        this.otherPlayersUsernames.add (otherPlayersUsernames);
    }

    public String getYourUsername() {
        return yourUsername;
    }

    public ArrayList<String> getOtherPlayersUsernames() {
        return otherPlayersUsernames;
    }

    public String getPlayerAt(int positionInGame){
        return null;
    }
}
