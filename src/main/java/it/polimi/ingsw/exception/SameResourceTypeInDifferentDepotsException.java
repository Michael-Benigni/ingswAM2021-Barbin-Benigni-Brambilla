package it.polimi.ingsw.exception;

/**
 * Exception thrown if you try to insert a resource in a warehouse but there is already a depot containing the same
 * resource type of the one to be stored
 */
public class SameResourceTypeInDifferentDepotsException extends Exception{
    public SameResourceTypeInDifferentDepotsException() {
    }
}