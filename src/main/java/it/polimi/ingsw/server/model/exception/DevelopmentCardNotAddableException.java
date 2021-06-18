package it.polimi.ingsw.server.model.exception;

import it.polimi.ingsw.server.model.cards.developmentcards.CardLevel;

/**
 * this class manages the error caused by the attempt of adding a development card
 * with a wrong level to the slot of the personal board
 */
public class DevelopmentCardNotAddableException extends Exception {
    public DevelopmentCardNotAddableException(CardLevel lvCardPlaced, CardLevel lvCardToBePlaced) {
        super("Impossible to add one card level " + lvCardToBePlaced.toString() + " over a card with level " + lvCardPlaced.toString());
    }
}
