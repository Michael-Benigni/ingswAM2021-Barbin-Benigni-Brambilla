package it.polimi.ingsw.server.model.exception;

public class NotHaveThisEffectException extends Exception {
    public NotHaveThisEffectException() {
        super("Impossible to transform the empty resource in a resource.");
    }
}
