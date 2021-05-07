package it.polimi.ingsw.server.exception;

/**
 * Exception thrown if one player reaches the last cell of the faith track, so the game is over.
 */
public class GameOverByFaithTrackException extends Exception {
    public GameOverByFaithTrackException() {
    }
}
