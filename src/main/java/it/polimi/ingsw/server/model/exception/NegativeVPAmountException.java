package it.polimi.ingsw.server.model.exception;

/**
 * Exception thrown if the amount provided to create an instance of the class "VictoryPoint" is less than zero.
 */
public class NegativeVPAmountException extends Exception{
    public NegativeVPAmountException() {
        super("One VictoryPoint object has a negative amount.");
    }
}
