package it.polimi.ingsw.server.model.exception;

public class TempContainerForProductionException extends Exception {
    public TempContainerForProductionException() {
        super("This temporary container isn't a container for production.");
    }
}
