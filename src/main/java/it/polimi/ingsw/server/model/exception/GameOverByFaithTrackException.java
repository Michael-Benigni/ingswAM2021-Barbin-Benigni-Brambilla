package it.polimi.ingsw.server.model.exception;

/**
 * Exception thrown if one player reaches the last cell of the faith track, so the game is over.
 */
public class GameOverByFaithTrackException extends GameOverException {
    public GameOverByFaithTrackException() {
    }
}
