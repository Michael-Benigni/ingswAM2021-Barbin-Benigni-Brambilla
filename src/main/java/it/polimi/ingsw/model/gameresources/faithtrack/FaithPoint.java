package it.polimi.ingsw.model.gameresources.faithtrack;

import it.polimi.ingsw.exception.NegativeResourceAmountException;
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


    int getPoints() {
        return points;
    }

    /**
     * Method inherited by the implementation of "Resource" interface
     */
    @Override
    public void activate() {
        //TODO: what kind of actions will be performed ?
    }

    /**
     * this method creates a copy of the object FaithPoint
     * @return the created copy
     * @throws NegativeResourceAmountException
     */
    @Override
    public Resource clone() throws CloneNotSupportedException {
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
