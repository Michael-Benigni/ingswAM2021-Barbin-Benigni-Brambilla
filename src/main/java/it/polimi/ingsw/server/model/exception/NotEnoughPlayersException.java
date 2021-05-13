package it.polimi.ingsw.server.model.exception;

/**
 * Exception thrown if trying to prepare or start a game with less logged players than required.
 */
public class NotEnoughPlayersException extends Exception{
    public NotEnoughPlayersException() {
    }
}
