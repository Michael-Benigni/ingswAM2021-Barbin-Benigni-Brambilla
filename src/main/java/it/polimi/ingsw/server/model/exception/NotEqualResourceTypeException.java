package it.polimi.ingsw.server.model.exception;

import it.polimi.ingsw.server.model.gameresources.stores.ResourceType;

/**
 * Exception thrown if two resources don't have the same type while required.
 */
public class NotEqualResourceTypeException extends Exception{
    public NotEqualResourceTypeException(ResourceType typeOfStored, ResourceType typeOfProvided) {
        super("Impossible to increase or decrease the amount of this resource: the stored type '" + typeOfStored + "' is different from the provided one '" + typeOfProvided + "'.");
    }
}
