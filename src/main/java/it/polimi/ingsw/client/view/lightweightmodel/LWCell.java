package it.polimi.ingsw.client.view.lightweightmodel;

import java.util.ArrayList;

/**
 * Class that represents the view of the cell of the faith track.
 */
public class LWCell {
    private final ArrayList<String> playersInThisCell;
    private final int victoryPoints;
    private final boolean isPopeSpace;
    private final String section;

    /**
     * Constructor method of this class.
     */
    public LWCell(ArrayList<String> playersInThisCell, int victoryPoints, boolean isPopeSpace, String section) {
        this.playersInThisCell = playersInThisCell;
        this.victoryPoints = victoryPoints;
        this.isPopeSpace = isPopeSpace;
        this.section = section;
    }

    /**
     * Getter method for the array of players in this cell.
     */
    public ArrayList<String> getPlayersInThisCell() {
        return playersInThisCell;
    }

    /**
     * Getter method for the victory points of this cell.
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * Getter method for the boolean that represents if this cell is a pope space.
     */
    public boolean isPopeSpace() {
        return isPopeSpace;
    }

    /**
     * Method that removes the provided player from the arraylist of players in this cell.
     */
    public void removePlayer(String playerName){
        this.playersInThisCell.remove(playerName);
    }

    /**
     * Method that adds a player to the array of players in this cell.
     */
    public void addPlayer(String playerName){
        this.playersInThisCell.add(playerName);
    }

    /**
     * Getter method for the section that contains this cell.
     */
    public String getSection() {
        return section;
    }
}
