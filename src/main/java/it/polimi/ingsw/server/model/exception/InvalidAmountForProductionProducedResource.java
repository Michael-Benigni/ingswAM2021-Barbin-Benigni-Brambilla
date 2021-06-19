package it.polimi.ingsw.server.model.exception;

public class InvalidAmountForProductionProducedResource extends Exception {
    public InvalidAmountForProductionProducedResource(int amount) {
        super("The amount of the produced resource must be " + amount + "." );
    }
}
