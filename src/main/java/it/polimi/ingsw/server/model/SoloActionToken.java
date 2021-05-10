package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.exception.*;
import it.polimi.ingsw.server.model.cards.developmentcards.CardColour;
import it.polimi.ingsw.server.model.gamelogic.SingleplayerGame;
import it.polimi.ingsw.server.model.gamelogic.actions.SoloPlayerGameBoard;

/**
 * Class that represents an action token used in the singleplayer game.
 */
public class SoloActionToken {

    private SoloActionTokenEffect effect;

    /**
     * Constructor method of this class.
     */
    public SoloActionToken() {
    }

    /**
     * Method that sets the effect of this token. When it is activated, 2 development cards with the provided colour are discarded
     * from the development cards grid, starting from the lowest level.
     * @param colour colour of the cards to be discarded.
     */
    public void setDiscard2CardsEffect(CardColour colour) {
        this.effect = (game) -> {
            try {
                game.getGameBoard().getDevelopmentCardGrid().removeNCardsFromGrid(colour, 2);
            } catch (NoMoreCardsWithThisColourException e) {
                throw new GameOverByGridException();
            }
        };
    }

    /**
     * Method that sets the effect of this token. When it is activated, the black cross is moved forward by two cells.
     */
    public void setMoveBlackCrossBy2() {
        this.effect = (game) -> game.getGameBoard().getFaithTrack().moveBlackCross(2);
    }

    /**
     * Method that sets the effect of this token. When it is activated, the black cross is moved forward by one cell, then all the tokens are reshuffled.
     */
    public void setMoveBlackCrossAndReShuffle() {
        this.effect = (game) -> {
            SoloPlayerGameBoard gameBoard = game.getGameBoard();
            gameBoard.getFaithTrack().moveBlackCross(1);
            gameBoard.getActionTokenDeck().shuffle();
        };
    }

    /**
     * Method that activates the effect of this token.
     */
    public void activateEffect(SingleplayerGame game)
            throws WrongCellIndexException, CellNotFoundInFaithTrackException, GameOverByGridException, GameOverByFaithTrackException, NegativeVPAmountException {
        this.effect.activateEffect(game);
    }
}
