package it.polimi.ingsw.server.model.exception;

/**
 * Exception thrown if trying to add one player to a multiplayer game that has already reached the number of players.
 */
public class TooManyPlayersException extends Exception {
    public TooManyPlayersException() {
    }
}
