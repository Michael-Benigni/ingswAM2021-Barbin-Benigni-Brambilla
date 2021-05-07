package it.polimi.ingsw.server.exception;

/**
 * Exception thrown if the provided integer, representing a depot index, doesn't respect the criteria, for example less
 * than zero or exceeds the number of depots.
 */
public class WrongDepotIndexException extends Exception{
    public WrongDepotIndexException() {
    }
}
