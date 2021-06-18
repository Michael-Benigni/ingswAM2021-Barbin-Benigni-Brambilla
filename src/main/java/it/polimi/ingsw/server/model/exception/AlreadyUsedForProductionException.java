package it.polimi.ingsw.server.model.exception;

public class AlreadyUsedForProductionException extends Exception {
    public AlreadyUsedForProductionException() {
        super("Object already used for production.");
    }
}
