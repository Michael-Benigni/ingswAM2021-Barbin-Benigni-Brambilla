package it.polimi.ingsw.model.gameresources.faithtrack;

import it.polimi.ingsw.exception.NegativeResourceAmountException;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.gameresources.markettray.Resource;


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
     * Getter method for the attribute "points".
     * @return -> the attribute "points".
     */
    private int getPoints() {
        return points;
    }


    /**
     * Method inherited by the implementation of "Resource" interface.
     * This method find the faith track and move the marker of the provided player by a number of steps equal to this.point.
     */
    @Override
    protected void activate(Player player) throws Exception {
        GameBoard gameBoard = player.getGameBoard();
        FaithTrack faithTrack = gameBoard.getFaithTrack();
        faithTrack.moveMarkerForward(player, points);
    }


    /**
     * this method creates a copy of the object FaithPoint
     * @return the created copy
     */
    @Override
    protected Resource clone() throws CloneNotSupportedException {
        FaithPoint faithPoint = null;
        try {
            faithPoint = new FaithPoint(points);
        } catch (NegativeResourceAmountException e) {
            this.points = 0;
            faithPoint = (FaithPoint) this.clone();
        }
        return faithPoint;
    }
}
