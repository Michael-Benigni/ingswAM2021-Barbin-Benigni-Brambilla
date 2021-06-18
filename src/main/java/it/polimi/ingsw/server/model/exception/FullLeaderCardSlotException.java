package it.polimi.ingsw.server.model.exception;

public class FullLeaderCardSlotException extends Exception {
    public FullLeaderCardSlotException(Integer maxNumberOfCards) {
        super("Impossible to exceeds the limit of " + maxNumberOfCards.toString() + " leader cards played by a single player.");
    }
}
