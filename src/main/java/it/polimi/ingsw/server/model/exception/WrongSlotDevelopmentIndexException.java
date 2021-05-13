package it.polimi.ingsw.server.model.exception;

/**
 * Exception thrown by personal board if the provided index for SlotDevelopmentCards is wrong, for example if it's
 * negative or exceeds the number of slots.
 */
public class WrongSlotDevelopmentIndexException extends Exception{
    public WrongSlotDevelopmentIndexException() {
    }
}
