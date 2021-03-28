package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.NegativeResourceAmountException;


/**
 * Class that represents a group of faith points, can be also "0", but not negative
 */
public class FaithPoint implements Resource{

    private int points;


    /**
     * Constructor method of the FaithPoint class
     * @param points -> how many faith points
     */
    FaithPoint(int points) throws NegativeResourceAmountException {
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
}
