package it.polimi.ingsw.server.model.gamelogic;

import it.polimi.ingsw.server.model.exception.GameOverByCardsGridException;
import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.actions.*;
import it.polimi.ingsw.utils.Observer;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Class that represents one match with 1 player, that will play with a COM
 */
public class SingleplayerGame extends Game {

    /**
     * Constructor. The number of players is 1 + 1 for the COM
     */
    public SingleplayerGame() throws IllegalNumberOfPlayersException {
        super(1);
    }


    /**
     * This method prepare the game, setting the players' order, creating the first turn, setting the first Player, and
     * the game board.
     */
    private void setup(ArrayList<PersonalBoard> personalBoards, SoloPlayerGameBoard gameBoard, ArrayList<InitialParams> params) throws IllegalNumberOfPlayersException, WrongBoardException, CellNotFoundInFaithTrackException {
        if (gameBoard.getActionTokenDeck () != null)
            super.setup(personalBoards, gameBoard, params);
        else
            throw new WrongBoardException ();
    }


    /**
     * This method prepare the game, setting the players' order, creating the first turn, setting the first Player, and
     * the game board.
     */
    @Override
    public void setup(ArrayList<PersonalBoard> personalBoards, GameBoard gameBoard, ArrayList<InitialParams> params) throws IllegalNumberOfPlayersException, WrongBoardException, CellNotFoundInFaithTrackException {
        setup(personalBoards, (SoloPlayerGameBoard) gameBoard, params);
    }

    @Override
    public SoloPlayerGameBoard getGameBoard() {
        return (SoloPlayerGameBoard) super.getGameBoard();
    }

    @Override
    public void performEndTurnAction() throws WrongCellIndexException, CellNotFoundInFaithTrackException,  GameOverByFaithTrackException, WrongInitialConfiguration, NegativeVPAmountException {
        try {
            new EndTurnSingleplayerAction ().perform (this);
        } catch (GameOverByCardsGridException e) {
            e.printStackTrace ();
        }
    }

    @Override
    protected void attachToGameBoard() {
        super.attachToGameBoard ();
        getGameBoard ().getActionTokenDeck ().attachAll(new ArrayList<> ((Collection<? extends Observer>) getObservers ()));
    }

    public static class EndTurnSingleplayerAction extends MultiplayerGame.EndTurnMultiplayerAction {
        /**
         * This is the method that performs this Action in the Game, and changes the actual state of the Game
         *
         * @param game   -> the Game on which this Action will be performed
         */
        public void perform(SingleplayerGame game) throws WrongCellIndexException, CellNotFoundInFaithTrackException,
                GameOverByFaithTrackException, WrongInitialConfiguration, NegativeVPAmountException,  GameOverByCardsGridException {
            super.perform (game, game.getCurrentPlayer ());
            game.getGameBoard ().getActionTokenDeck ().drawFirst ().activateEffect (game, game.getCurrentPlayer ());
        }
    }
}
