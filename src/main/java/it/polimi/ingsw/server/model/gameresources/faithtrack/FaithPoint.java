package it.polimi.ingsw.server.model.gameresources.faithtrack;

import com.google.gson.annotations.SerializedName;
import it.polimi.ingsw.server.model.exception.CellNotFoundInFaithTrackException;
import it.polimi.ingsw.server.model.exception.GameOverByFaithTrackException;
import it.polimi.ingsw.server.model.exception.NegativeVPAmountException;
import it.polimi.ingsw.server.model.exception.WrongCellIndexException;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gameresources.Producible;

import java.util.Objects;

/**
 * Class that represents a group of faith points, can be also "0", but not negative
 */
public class FaithPoint implements Producible {

    private final int points;


    /**
     * Constructor method of the FaithPoint class
     * @param points indicates how many faith points
     */
    public FaithPoint(int points) {
        this.points = Math.max (points, 0);
    }


    /**
     * Method inherited by the implementation of "Resource" interface.
     * This method find the faith track and move the marker of the provided player by a number of steps equal to this.point.
     */
    @Override
    public void activate(Player player, Game game) throws WrongCellIndexException, CellNotFoundInFaithTrackException,
            GameOverByFaithTrackException, NegativeVPAmountException {
        game.getGameBoard().getFaithTrack().moveMarkerForward(player, this.points);
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
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

    @Override
    public String toString() {
        return points + " Faith Point";
    }
}
