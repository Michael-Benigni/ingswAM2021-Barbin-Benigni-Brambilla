package it.polimi.ingsw.server.model.exception;

/**
 * Exception thrown if trying to search a not existing cell in the faith track.
 */
public class CellNotFoundInFaithTrackException extends Exception {
    public CellNotFoundInFaithTrackException() {
        super("Cell not found in the faith track.");
    }
}
