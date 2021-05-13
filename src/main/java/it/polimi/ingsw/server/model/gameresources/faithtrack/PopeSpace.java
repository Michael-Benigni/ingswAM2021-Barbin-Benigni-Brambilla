package it.polimi.ingsw.server.model.gameresources.faithtrack;

import it.polimi.ingsw.server.model.exception.CellNotFoundInFaithTrackException;
import it.polimi.ingsw.server.model.exception.NegativeVPAmountException;
import it.polimi.ingsw.server.model.exception.TileAlreadyActivatedException;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gamelogic.actions.VictoryPoint;

import java.util.HashMap;

/**
 * Class that represents a Pope Space cell on the faith track.
 */
public class PopeSpace extends Cell{

    private PopeFavourTile tile;

    /**
     * Constructor method of this class.
     */
    public PopeSpace(PopeFavourTile tile) {
        this.tile = tile;
    }


    /**
     * Method inherited from "Cell" class. In this case this method would activate the right Pope Favour Tile and
     * checks if the markers of the other players point to a cell of the same section.
     * @param faithTrack
     */
    @Override
    protected void activateCell(FaithTrack faithTrack, Player player) throws CellNotFoundInFaithTrackException, NegativeVPAmountException {
        try{
            VictoryPoint pointsFromTile = this.tile.activateTile();
            player.addVictoryPointsToPlayer(pointsFromTile);
            HashMap<Player, FaithMarker> mapOfFaithMarker = faithTrack.getMapOfFaithMarkers();
            Cell activatedCell = mapOfFaithMarker.get(player).getCurrentCell();
            Section activatedSection = faithTrack.findSectionOfThisCell(activatedCell);
            for(Player p : mapOfFaithMarker.keySet()) {
                if(!(p.equals(player)) && mapOfFaithMarker.get(p).ifIsInThisSection(activatedSection)) {
                    p.addVictoryPointsToPlayer(pointsFromTile);
                }
            }
        } catch (TileAlreadyActivatedException e) {
            //tile already activated, so do nothing.
        }
    }


    /**
     * Method that return if two objects are both instances of this class.
     * @param o object to be compared.
     * @return boolean: true if both are instances of "PopeSpace" class.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }
}
