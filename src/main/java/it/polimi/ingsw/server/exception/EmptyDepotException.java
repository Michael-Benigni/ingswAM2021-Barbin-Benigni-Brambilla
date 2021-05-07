package it.polimi.ingsw.server.exception;

/**
 * Exception thrown by the "Depot" class if trying to get the contained resource of an empty depot.
 */
public class EmptyDepotException extends Exception {
    public EmptyDepotException() {
    }
}
