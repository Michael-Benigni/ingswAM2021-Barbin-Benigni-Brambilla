package it.polimi.ingsw.client.view.lightweightmodel;

import java.util.ArrayList;

public class LWCell {
    private ArrayList<String> playersInThisCell;
    private int victoryPoints;
    private Boolean isPopeSpace;

    public LWCell(ArrayList<String> playersInThisCell, int victoryPoints, Boolean isPopeSpace) {
        this.playersInThisCell = playersInThisCell;
        this.victoryPoints = victoryPoints;
        this.isPopeSpace = isPopeSpace;
    }

    public ArrayList<String> getPlayersInThisCell() {
        return playersInThisCell;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public Boolean isPopeSpace() {
        return isPopeSpace;
    }

    public void removePlayer(String playerName){
        this.playersInThisCell.remove(playerName);
    }

    public void addPlayer(String playerName){
        this.playersInThisCell.add(playerName);
    }
}
