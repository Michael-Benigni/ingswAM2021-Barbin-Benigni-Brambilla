package it.polimi.ingsw.server.model.exception;

public class NullResourceAmountException extends Exception {
    public NullResourceAmountException() {
        super("Amount of the resource is equal to '0'");
    }
}
