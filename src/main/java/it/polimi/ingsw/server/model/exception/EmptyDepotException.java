package it.polimi.ingsw.server.model.exception;

/**
 * Exception thrown by the "Depot" class if trying to get the contained resource of an empty depot.
 */
public class EmptyDepotException extends Exception {
    public EmptyDepotException() {
        super("This depot is empty.");
    }
}
