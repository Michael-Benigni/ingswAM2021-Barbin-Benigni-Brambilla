package it.polimi.ingsw.exception;

/**
 * Exception thrown if you try to search or remove any resource from a strongbox that doesn't contain a resource
 * with the same type.
 */
public class NotContainedResourceException extends Exception{
    public NotContainedResourceException() {
    }
}
