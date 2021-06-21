package it.polimi.ingsw.server.model.gameresources.faithtrack;

import it.polimi.ingsw.server.model.exception.NegativeVPAmountException;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gamelogic.actions.VictoryPoint;
import it.polimi.ingsw.utils.network.MessageWriter;

import java.util.HashMap;


/**
 * Class that represents a cell in the faith truck that can earn a player some victory points.
 */
public class VPCell extends Cell{

    private VictoryPoint victoryPoints;

    /**
     * Constructor method of this class. It reads from the database the value of "this.numVictoryPoints".
     */
    public VPCell(VictoryPoint numVictoryPoints) {
        this.victoryPoints = numVictoryPoints;
    }


    /**
     * Method inherited by "Cell" class. In this case it would create an instance of VictoryPoints...
     * @throws NegativeVPAmountException -> can be thrown by constructor method of "VictoryPoint" class.
     */
    @Override
    protected void activateCell(FaithTrack faithTrack, Player player) throws NegativeVPAmountException {
        VictoryPoint victoryPoint = (VictoryPoint) this.victoryPoints.clone();
        HashMap<Player, FaithMarker> mapOfFaithMarker = faithTrack.getMapOfFaithMarkers();
        VictoryPoint pointsToBeAddedToPlayer = mapOfFaithMarker.get(player).updateLastVictoryPoint(victoryPoint);
        player.addVictoryPointsToPlayer(pointsToBeAddedToPlayer);
    }

    @Override
    public void getInfo(MessageWriter writer){
        writer.addProperty ("VP", this.victoryPoints.getPoints());
        writer.addProperty ("isPopeSpace", false);
    }


    /**
     * Method that return if two objects are both instances of this class
     * and they have the same number of victory points..
     * @param o object to be compared.
     * @return boolean: true if the two objects are equals.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VPCell vpCell = (VPCell) o;
        return victoryPoints.equals(vpCell.victoryPoints);
    }

}
