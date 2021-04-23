package it.polimi.ingsw.model.gameresources.faithtrack;

import it.polimi.ingsw.exception.NegativeResourceAmountException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.gameresources.markettray.Resource;

import java.util.Objects;


/**
 * Class that represents a group of faith points, can be also "0", but not negative
 */
public class FaithPoint extends Resource {

    private int points;


    /**
     * Constructor method of the FaithPoint class
     * @param points -> how many faith points
     */
    public FaithPoint(int points) throws NegativeResourceAmountException {
        if(points < 0)
            throw new NegativeResourceAmountException();
        else
            this.points = points;
    }


    /**
     * Method inherited by the implementation of "Resource" interface.
     * This method find the faith track and move the marker of the provided player by a number of steps equal to this.point.
     */
    @Override
    protected void activate(Player player) throws Exception {
        //TODO: get the faith track, don't provide it in input, because the other resources shouldn't see the faith track.
        /*faithTrack.moveMarkerForward(player, points);*/
    }

    @Override
    protected Object clone() {
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
}
