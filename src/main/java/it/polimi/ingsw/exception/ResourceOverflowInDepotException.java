package it.polimi.ingsw.exception;


/**
 * Exception thrown if you try to insert a resource into a depot, but the amount exceeds the capacity.
 */
public class ResourceOverflowInDepotException extends Exception{
    public ResourceOverflowInDepotException() {
    }
}
