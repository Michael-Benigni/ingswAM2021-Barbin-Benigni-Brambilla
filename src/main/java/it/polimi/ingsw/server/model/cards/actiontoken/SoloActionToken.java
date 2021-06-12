package it.polimi.ingsw.server.model.cards.actiontoken;


import it.polimi.ingsw.server.model.cards.developmentcards.CardColour;
import it.polimi.ingsw.server.model.cards.developmentcards.GeneralDevelopmentCard;
import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.SingleplayerGame;
import it.polimi.ingsw.server.model.gamelogic.actions.SoloPlayerGameBoard;

/**
 * Class that represents an action token used in the singleplayer game.
 */
public class SoloActionToken {

    private SoloActionTokenEffect effect;

    /**
     * Method that sets the effect of this token. When it is activated,
     * 2 development cards with the provided colour are discarded
     * from the development cards grid, starting from the lowest level.
     * @param colourCard card with colour of cards to be discarded to be discarded.
     * @param numCardToDiscard
     */
    public void setDiscard2CardsEffect(GeneralDevelopmentCard colourCard, int numCardToDiscard) {
        this.effect = (game) -> game.getGameBoard ().getDevelopmentCardGrid ().removeNCardsFromGrid (colourCard, numCardToDiscard);
    }

    /**
     * Method that sets the effect of this token. When it is activated, the black cross is moved forward by two cells.
     * @param numMoves
     */
    public void setMoveBlackCross(int numMoves) {
        this.effect = (game) -> game.getGameBoard().getFaithTrack().moveBlackCross(numMoves);
    }

    /**
     * Method that sets the effect of this token. When it is activated,
     * the black cross is moved forward by one cell, then all the tokens are reshuffled.
     * @param numMoves
     */
    public void setMoveBlackCrossAndReShuffle(int numMoves) {
        this.effect = (game) -> {
            SoloPlayerGameBoard gameBoard = game.getGameBoard();
            gameBoard.getFaithTrack().moveBlackCross(numMoves);
            gameBoard.getActionTokenDeck().shuffle();
        };
    }

    /**
     * Method that activates the effect of this token.
     */
    public void activateEffect(SingleplayerGame game)
            throws WrongCellIndexException, CellNotFoundInFaithTrackException, GameOverByGridException,
            GameOverByFaithTrackException, NegativeVPAmountException {
        this.effect.activateEffect(game);
    }
}
