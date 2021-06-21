package it.polimi.ingsw.server.model.cards.actiontoken;


import it.polimi.ingsw.server.model.exception.GameOverByCardsGridException;
import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.SingleplayerGame;

/**
 * Interface used to implements the effects of the action tokens with a strategy pattern.
 */
public interface SoloActionTokenEffect {
    void activateEffect(SingleplayerGame game) throws GameOverByCardsGridException, WrongCellIndexException, CellNotFoundInFaithTrackException, GameOverByFaithTrackException, NegativeVPAmountException, GameOverBlackCrossAtEndOfFaithTrackException;
}
