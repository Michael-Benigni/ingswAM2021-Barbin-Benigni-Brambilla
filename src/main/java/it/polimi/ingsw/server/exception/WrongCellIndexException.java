package it.polimi.ingsw.server.exception;

/**
 * Exception thrown if the provided index is less than zero or greater than the size of the section.
 */
public class WrongCellIndexException extends Exception{
    public WrongCellIndexException() {
    }
}
