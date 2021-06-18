package it.polimi.ingsw.server.model.exception;

public class IllegalNumberOfPlayersException extends Exception {
    public IllegalNumberOfPlayersException() {
        super("Impossible to exceeds the max number of players of this game.");
    }
}
