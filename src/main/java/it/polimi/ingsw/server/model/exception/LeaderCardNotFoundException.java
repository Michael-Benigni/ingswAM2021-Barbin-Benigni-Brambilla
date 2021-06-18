package it.polimi.ingsw.server.model.exception;

public class LeaderCardNotFoundException extends Exception {
    public LeaderCardNotFoundException(Integer cardId) {
        super("Impossible to find the card with ID='" + cardId + "' in the leader cards slot.");
    }
}
