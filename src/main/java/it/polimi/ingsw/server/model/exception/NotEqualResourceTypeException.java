package it.polimi.ingsw.server.model.exception;

import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;

/**
 * Exception thrown if two resources don't have the same type while required.
 */
public class NotEqualResourceTypeException extends Exception{
    public NotEqualResourceTypeException(StorableResource typeOfStored, StorableResource typeOfProvided) {
        super("Impossible to increase or decrease the amount of this resource: the stored resource '" + typeOfStored + "' has a different type with respect to the provided one '" + typeOfProvided + "'.");
    }
}
