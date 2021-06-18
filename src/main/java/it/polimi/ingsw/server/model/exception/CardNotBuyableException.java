package it.polimi.ingsw.server.model.exception;

public class CardNotBuyableException extends Exception {
    public CardNotBuyableException() {
        super("Impossible to buy this card.");
    }
}
