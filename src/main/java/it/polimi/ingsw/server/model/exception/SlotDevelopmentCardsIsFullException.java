package it.polimi.ingsw.server.model.exception;

public class SlotDevelopmentCardsIsFullException extends Exception{
    public SlotDevelopmentCardsIsFullException(Integer maxNumberInSlot) {
        super("Impossible to exceeds the limit of cards in this slot, that is '" + maxNumberInSlot + "'.");
    }
}
