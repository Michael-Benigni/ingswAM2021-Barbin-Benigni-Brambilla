package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.exception.GameOverByGridException;
import it.polimi.ingsw.server.exception.NoMoreCardsWithThisColourException;
import it.polimi.ingsw.server.model.cards.developmentcards.CardColour;
import it.polimi.ingsw.server.model.gamelogic.actions.SoloPlayerGameBoard;

public class SoloActionToken {

    private SoloActionTokenEffect effect;

    /**
     * Constructor method of this class.
     */
    public SoloActionToken() {
    }

    public void setDiscard2CardsEffect(CardColour colour) {
        this.effect = (game) -> {
            try {
                game.getGameBoard().getDevelopmentCardGrid().removeNCardsFromGrid(colour, 2);
            } catch (NoMoreCardsWithThisColourException e) {
                throw new GameOverByGridException();
            }
        };
    }

    public void setMoveBlackCrossBy2() {
        this.effect = (game) -> game.getGameBoard().getFaithTrack().moveBlackCross(2);
    }

    public void setMoveBlackCrossAndReShuffle() {
        this.effect = (game) -> {
            SoloPlayerGameBoard gameBoard = game.getGameBoard();
            gameBoard.getFaithTrack().moveBlackCross(1);
            gameBoard.getActionTokenDeck().shuffleSoloActionTokens();
        };
    }
}
