package it.polimi.ingsw.server.model.exception;

public class TooMuchResourcesProvided extends Exception{

    public TooMuchResourcesProvided(int numOfResourcesToPay) {
        super("The amount of the resources to pay must be " + numOfResourcesToPay + ".");
    }
}
