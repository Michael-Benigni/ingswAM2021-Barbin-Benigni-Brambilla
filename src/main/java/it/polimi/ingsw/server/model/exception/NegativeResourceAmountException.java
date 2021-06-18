package it.polimi.ingsw.server.model.exception;


import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;

/**
 * Exception throwed if the amount of the provided resource is less than "0"
 */
public class NegativeResourceAmountException extends Exception{
    private final StorableResource remainder;

    public StorableResource getRemainder() {
        return remainder;
    }

    public NegativeResourceAmountException(StorableResource remainder) {
        super("A StorableResource object has a negative amount.");
        this.remainder = remainder;
    }
}
