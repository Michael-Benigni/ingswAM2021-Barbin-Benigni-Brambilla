package it.polimi.ingsw.server.controller.exception;

/**
 * Exception thrown by "disconnect" method of "WaitingRoom" class and caught in the method "disconnect" of "Controller" class.
 */
public class EmptyWaitingRoomException extends Exception {

    /**
     * No messages, because each time this exception is thrown, it's also caught.
     */
    public EmptyWaitingRoomException() {
    }
}
