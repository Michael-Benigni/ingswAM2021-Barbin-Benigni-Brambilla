package it.polimi.ingsw.server.controller.exception;

public class WrongIdWaitingRoomException extends Exception{

    public WrongIdWaitingRoomException(Integer ID) {
        super("Impossible to find a waiting room with ID: " + ID.toString() + " .");
    }
}
