package it.polimi.ingsw.server.model.exception;

public class LeaderCardNotPlayedException extends Exception {
    public LeaderCardNotPlayedException(Integer cardId) {
        super("The leader card ID='" + cardId + "' is already played or the requirements are not satisfied.");
    }
}
