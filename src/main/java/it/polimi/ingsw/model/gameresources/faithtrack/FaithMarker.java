package it.polimi.ingsw.model.gameresources.faithtrack;

import it.polimi.ingsw.exception.CellNotFoundInSectionException;
import it.polimi.ingsw.exception.NegativeVPAmountException;
import it.polimi.ingsw.exception.WrongPlayerException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.VictoryPoint;

/**
 * Class that represents the indicator of a player's position on the faith track.
 */
public class FaithMarker {

    private Cell currentCell;
    private VictoryPoint lastVictoryPoint;
    private Player player;

    /**
     * Constructor method of this class.
     * @throws NegativeVPAmountException -> can be thrown by constructor method of "VictoryPoint" class.
     */
    public FaithMarker(Player player, Cell startingCell) throws NegativeVPAmountException {
        lastVictoryPoint = new VictoryPoint(0);
        this.player = player;
        this.currentCell = startingCell;
    }


    /**
     * Method that update the victory points gained by the last VPCell reached by the faith marker. Then also increase
     * the amount of victory points of that player.
     * @param newVictoryPoint -> number of earned victory points.
     * @throws NegativeVPAmountException -> can be thrown by "decreaseVictoryPoint" method of "VictoryPoint" class.
     */
    void updateVictoryPointInFaithMarker(VictoryPoint newVictoryPoint) throws NegativeVPAmountException {
        VictoryPoint temporaryVictoryPoint = newVictoryPoint;
        temporaryVictoryPoint.decreaseVictoryPoints(lastVictoryPoint);
        this.player.addVictoryPointsToPlayer(temporaryVictoryPoint);
        lastVictoryPoint = newVictoryPoint;
    }


    /**
     * Method that return if the provided player is the owner of this faith marker.
     * @param player -> player to compare.
     * @return -> boolean: true if this faith marker belongs to the provided player.
     */
    boolean belongsTo(Player player){
        return this.player.equals(player);
    }


    /**
     * Method that update the current cell of this faith marker with the next on the track. Then it activates
     * the cell effect.
     * @param faithTrack -> faith track to which this faith marker refers.
     * @throws Exception
     */
    void moveForward(FaithTrack faithTrack, Player player) throws Exception {
        if(player.equals(this.player)) {
            this.currentCell = faithTrack.nextCell(this.currentCell);
            this.currentCell.activateCell(this);
        }
        else {throw new WrongPlayerException();}
    }


    /**
     * Method that returns if the current cell is contained in the provided section.
     * @param currentSection -> section to be scouted.
     * @return -> boolean: true if the current cell is in the provided section.
     */
    boolean ifIsInThisSection(Section currentSection) {
        try {
            currentSection.searchInThisSection(this.currentCell);
            return true;
        } catch(CellNotFoundInSectionException e){
            return false;
        }
    }


    /**
     * Getter method for "player" attribute of this class.
     * @return -> an instance of "Player" class.
     */
    Player getPlayer() {
        return player;
    }
}
