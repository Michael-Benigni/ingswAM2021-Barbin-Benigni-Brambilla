package it.polimi.ingsw.model.gameresources.faithtrack;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.VictoryPoint;

import java.util.HashMap;

/**
 * Class that represents a particular type of pope space that gives some victory points.
 */
public class VictoryPointsPopeSpace extends PopeSpace{

    private VictoryPoint victoryPoints;

    /**
     * Constructor method of this class.
     * @param victoryPoints -> number of victory points.
     */
    public VictoryPointsPopeSpace(VictoryPoint victoryPoints, PopeFavourTile tile) {
        super(tile);
        this.victoryPoints = victoryPoints;
    }


    /**
     * Method inherited by "Cell" class. In this case this method does the same actions of "VPCell" and "PopeSpace".
     * @param faithTrack
     * @param player
     * @throws Exception
     */
    @Override
    public void activateCell(FaithTrack faithTrack, Player player) throws Exception {
        VictoryPoint victoryPoint = (VictoryPoint) this.victoryPoints.clone();
        HashMap<Player, FaithMarker> mapOfFaithMarker = faithTrack.getMapOfFaithMarker();
        VictoryPoint pointsToBeAdded = mapOfFaithMarker.get(player).updateLastVictoryPoint(victoryPoint);
        player.addVictoryPointsToPlayer(pointsToBeAdded);
        super.activateCell(faithTrack, player);
    }


    /**
     * Method that return if two objects are instances of this class and have the same number of victory points.
     * @param o -> object to be compared.
     * @return -> boolean: true if they are equals.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        VictoryPointsPopeSpace that = (VictoryPointsPopeSpace) o;
        return victoryPoints == that.victoryPoints;
    }
}
