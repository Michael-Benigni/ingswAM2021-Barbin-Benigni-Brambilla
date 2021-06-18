package it.polimi.ingsw.server.model.exception;

public class IsNotCurrentPlayerException extends Exception {
    public IsNotCurrentPlayerException(String usernameCurrentPlayer) {
        super("Impossible to do actions, now it's player '" + usernameCurrentPlayer + "' turn.");
    }
}
