package it.polimi.ingsw.server.model.exception;

import it.polimi.ingsw.server.model.cards.developmentcards.CardColour;

public class GameOverByCardsGridException extends GameOverException {
    public GameOverByCardsGridException(CardColour colour) {
        super("This game is over! All " + colour.toString() + " cards of the grid are discarded.");
    }
}
