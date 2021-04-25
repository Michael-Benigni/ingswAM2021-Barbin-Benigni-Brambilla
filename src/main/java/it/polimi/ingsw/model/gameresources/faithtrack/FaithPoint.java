package it.polimi.ingsw.model.gameresources.faithtrack;

import it.polimi.ingsw.model.gamelogic.Game;
import it.polimi.ingsw.model.gamelogic.actions.Player;
import it.polimi.ingsw.model.gameresources.Producible;

import java.util.Objects;


/**
 * Class that represents a group of faith points, can be also "0", but not negative
 */
public class FaithPoint implements Producible {

    private int points;


    /**
     * Constructor method of the FaithPoint class
     * @param points -> how many faith points
     */
    public FaithPoint(int points) {
        if(points < 0)
            this.points = 0;
        else
            this.points = points;
    }


    /**
     * Method inherited by the implementation of "Resource" interface.
     * This method find the faith track and move the marker of the provided player by a number of steps equal to this.point.
     */
    @Override
    public void activate(Player player, Game game) throws Exception {
        //TODO: get the faith track, don't provide it in input, because the other resources shouldn't see the faith track.
        game.getGameBoard().getFaithTrack().moveMarkerForward(player, this.points);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FaithPoint that = (FaithPoint) o;
        return points == that.points;
    }

    @Override
    public int hashCode() {
        return Objects.hash(points);
    }

    @Override
    public void onProduced(Player player, Game game) throws Exception {
        this.activate(player, game);
    }
}
