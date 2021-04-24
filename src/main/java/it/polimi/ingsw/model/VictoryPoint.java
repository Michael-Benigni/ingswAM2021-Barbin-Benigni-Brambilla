package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.NegativeVPAmountException;

/**
 * Class that represents the victory points. It's composed by an integer that represents the amount of victory points.
 */
public class VictoryPoint {

    private int amount;


    /**
     * Constructor method of this class.
     * @param amount -> amount of victory points you want to create.
     */
    public VictoryPoint(int amount) {
        if(amount < 0){
            amount = 0;
        }
            this.amount = amount;
    }


    /**
     * Method that sum two instances of "VictoryPoint" class. This object replace its amount with the sum of the two amounts.
     * @param pointToSum -> Object of "VictoryPoints" class you want to be summed to this.
     */
    void increaseVictoryPoints(VictoryPoint pointToSum){
        this.amount = this.amount + pointToSum.amount;
    }


    /**
     * Method that subtract two instances of "VictoryPoint" class.
     * @param pointToSubtract -> Object of "VictoryPoint" class you want to be subtracted to this VictoryPoint.
     * @throws NegativeVPAmountException -> exception thrown if the subtraction creates a VictoryPoint with a
     * negative amount.
     */
    public void decreaseVictoryPoints(VictoryPoint pointToSubtract) throws NegativeVPAmountException {
        if(this.amount >= pointToSubtract.amount)
            this.amount = this.amount - pointToSubtract.amount;
        else
            throw new NegativeVPAmountException();
    }


    /**
     * Method that returns if the provided victory points has the same amounts of this instance of victory point.
     * @return -> boolean: true if the two amounts are the same, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VictoryPoint that = (VictoryPoint) o;
        return amount == that.amount;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
