package it.polimi.ingsw.server.model.exception;

public class NoEmptyResourceException extends Exception {
    public NoEmptyResourceException(String usernamePlayer) {
        super("Impossible to transform an empty resource to a storable resource, cause the player '" + usernamePlayer + "' has not EmptyResource object in his temporary container.");
    }
}
