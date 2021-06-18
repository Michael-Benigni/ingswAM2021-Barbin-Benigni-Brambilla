package it.polimi.ingsw.server.model.exception;

public class LeaderCardNotDiscardableException extends Exception {
    public LeaderCardNotDiscardableException() {
        super("Impossible to discard a card that is already played.");
    }
}
