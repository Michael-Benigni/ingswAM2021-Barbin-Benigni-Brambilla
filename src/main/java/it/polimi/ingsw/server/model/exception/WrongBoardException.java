package it.polimi.ingsw.server.model.exception;

public class WrongBoardException extends Exception {
    public WrongBoardException() {
        super("Impossible to setup a single player game without one single player game board.");
    }
}
