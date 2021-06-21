package it.polimi.ingsw.client.view.lightweightmodel;

import java.util.ArrayList;

public class LWCell {
    private final ArrayList<String> playersInThisCell;
    private final int victoryPoints;
    private final boolean isPopeSpace;
    private final String section;

    public LWCell(ArrayList<String> playersInThisCell, int victoryPoints, boolean isPopeSpace, String section) {
        this.playersInThisCell = playersInThisCell;
        this.victoryPoints = victoryPoints;
        this.isPopeSpace = isPopeSpace;
        this.section = section;
    }

    public ArrayList<String> getPlayersInThisCell() {
        return playersInThisCell;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public boolean isPopeSpace() {
        return isPopeSpace;
    }

    public void removePlayer(String playerName){
        this.playersInThisCell.remove(playerName);
    }

    public void addPlayer(String playerName){
        this.playersInThisCell.add(playerName);
    }

    public String getSection() {
        return section;
    }
}
