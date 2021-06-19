package it.polimi.ingsw.server.model.exception;

public class TempContainerForProductionException extends Exception {
    public TempContainerForProductionException() {
        super("You can't move resources from and to the temporary container " +
                "\nduring the production phase." +
                "\nIf you end the production phase, all the resources in the" +
                "\ntemporary container will be stored into the strongbox.");
    }
}
