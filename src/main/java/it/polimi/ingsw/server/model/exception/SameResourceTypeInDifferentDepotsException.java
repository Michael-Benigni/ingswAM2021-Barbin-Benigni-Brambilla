package it.polimi.ingsw.server.model.exception;

/**
 * Exception thrown if you try to insert a resource in a warehouse but there is already a depot containing the same
 * resource type of the one to be stored
 */
public class SameResourceTypeInDifferentDepotsException extends Exception{
    public SameResourceTypeInDifferentDepotsException() {
        super("Another depot in the warehouse contains resource with same type of the one provided.");
    }
}
