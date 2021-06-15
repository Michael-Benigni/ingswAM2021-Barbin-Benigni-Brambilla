package it.polimi.ingsw.server.model.gameresources.faithtrack;

import it.polimi.ingsw.server.model.exception.CellNotFoundInFaithTrackException;
import it.polimi.ingsw.server.model.exception.NegativeVPAmountException;
import it.polimi.ingsw.server.model.exception.TileAlreadyActivatedException;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gamelogic.actions.VictoryPoint;
import it.polimi.ingsw.utils.network.Header;
import it.polimi.ingsw.utils.network.MessageWriter;

import java.util.HashMap;

/**
 * Class that represents a Pope Space cell on the faith track.
 */
public class PopeSpace extends Cell{

    private final PopeFavourTile tile;

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
    protected void activateCell(FaithTrack faithTrack, Player player) throws CellNotFoundInFaithTrackException,
            NegativeVPAmountException {
        VictoryPoint pointsFromTile = new VictoryPoint (0);
        try{
            pointsFromTile = this.tile.activateTile();
        } catch (TileAlreadyActivatedException e) {
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToClient.GENERIC_INFO);
            writer.addProperty ("text", "Tile already activated before from another player!");
            player.notifyUpdate (writer.write ());
        }
        player.addVictoryPointsToPlayer(pointsFromTile);
        notifyPopeSpaceBenefits (player, player, pointsFromTile);
        HashMap<Player, FaithMarker> mapOfFaithMarker = faithTrack.getMapOfFaithMarkers();
        Cell activatedCell = mapOfFaithMarker.get(player).getCurrentCell();
        Section activatedSection = faithTrack.findSectionOfThisCell(activatedCell);
        for(Player p : mapOfFaithMarker.keySet()) {
            if(!(p.equals(player)) && mapOfFaithMarker.get(p).ifIsInThisSection(activatedSection)) {
                p.addVictoryPointsToPlayer(pointsFromTile);
                notifyPopeSpaceBenefits(player, p, pointsFromTile);
            }
            else
                notifyPopeSpaceActivation(player, p, pointsFromTile);
        }
    }

    private void notifyPopeSpaceActivation(Player activator, Player subject, VictoryPoint point) {
        MessageWriter writer = new MessageWriter ();
        writer.setHeader (Header.ToClient.GENERIC_INFO);
        if (activator != subject)
            writer.addProperty ("text", "Player " + activator.getUsername () + " has activated the Pope Space Effect.\n" +
                    "You won' t earn " + point + " like the other players because you were not in the same Section!");
        if (!point.equals (new VictoryPoint (0)) && activator != subject)
            subject.notifyUpdate (writer.write ());
    }

    private void notifyPopeSpaceBenefits(Player activator, Player subject, VictoryPoint point) {
        MessageWriter writer = new MessageWriter ();
        writer.setHeader (Header.ToClient.GENERIC_INFO);
        if (activator != subject)
            writer.addProperty ("text", "Player " + activator.getUsername () + " has activated the Pope Space Effect.\n" +
                    "You will earn " + point + " because you were in the same Section!");
        else
            writer.addProperty ("text", "You have activated a Pope Space, you will earn " + point);
        if (!point.equals (new VictoryPoint (0)))
            subject.notifyUpdate (writer.write ());
    }

    @Override
    public void getInfo(MessageWriter writer){
        writer.addProperty ("VP", 0);
        writer.addProperty ("isPopeSpace", true);
    }


    /**
     * Method that return if two objects are both instances of this class.
     * @param o object to be compared.
     * @return boolean: true if both are instances of "PopeSpace" class.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass () == o.getClass ();
    }
}
