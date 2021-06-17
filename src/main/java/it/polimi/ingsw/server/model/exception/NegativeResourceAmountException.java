package it.polimi.ingsw.server.model.exception;


import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;

/**
 * Exception throwed if the amount of the provided resource is less than "0"
 */
public class NegativeResourceAmountException extends Exception{
    public StorableResource getRemainder() {
        return remainder;
    }

    private final StorableResource remainder;

    public NegativeResourceAmountException(StorableResource remainder) {
        this.remainder = remainder;
    }
}
