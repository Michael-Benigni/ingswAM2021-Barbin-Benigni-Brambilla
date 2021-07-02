package it.polimi.ingsw.server.controller.exception;

public class InvalidUserException extends Exception {

    public InvalidUserException() {
        super("Wrong user provided in input.");
    }
}
