package it.polimi.ingsw.server.controller.exception;

public class FullWaitingRoomException extends Exception {

    public FullWaitingRoomException() {
        super("This waiting room reaches the limit of users.");
    }
}
