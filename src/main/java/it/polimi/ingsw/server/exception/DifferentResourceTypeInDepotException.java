package it.polimi.ingsw.server.exception;


/**
 * Exception thrown if you try to insert a resource into a depot that already contains a resource with different type.
 */
public class DifferentResourceTypeInDepotException extends Exception{
    public DifferentResourceTypeInDepotException() {
    }
}
