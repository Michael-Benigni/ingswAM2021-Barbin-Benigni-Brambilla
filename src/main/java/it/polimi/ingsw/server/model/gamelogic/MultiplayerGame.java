package it.polimi.ingsw.server.model.gamelogic;

import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.actions.*;

import java.util.ArrayList;

/**
 * Class that represents one match with 2 or more players.
 */
public class MultiplayerGame extends Game {


    /**
     * Constructor.
     * @param numberOfPlayers
     */
    public MultiplayerGame(int numberOfPlayers) throws IllegalNumberOfPlayersException {
        super(numberOfPlayers);
        if (numberOfPlayers == 1)
            throw new IllegalNumberOfPlayersException();
    }


    /**
     * This method prepare the game, setting the players' order, creating the first turn, setting the first Player, and
     * the game board.
     */
    @Override
    public void setup(ArrayList<PersonalBoard> personalBoards, GameBoard gameBoard, ArrayList<InitialParams> params) throws IllegalNumberOfPlayersException, WrongBoardException, CellNotFoundInFaithTrackException {
        super.setup(personalBoards, gameBoard, params);
    }

    @Override
    public GameBoard getGameBoard() {
        return super.getGameBoard();
    }

    @Override
    public void performEndTurnAction() throws WrongCellIndexException, CellNotFoundInFaithTrackException, GameOverByFaithTrackException, WrongInitialConfiguration, NegativeVPAmountException, YouMustEndTheProductionPhaseException, EndGameException {
        new EndTurnMultiplayerAction ().perform (this, this.getCurrentPlayer ());
    }

    public static class EndTurnMultiplayerAction extends EndTurnAction {

        @Override
        public void perform(Game game, Player player) throws WrongInitialConfiguration, WrongCellIndexException,
                CellNotFoundInFaithTrackException, GameOverByFaithTrackException, NegativeVPAmountException, YouMustEndTheProductionPhaseException, EndGameException {
            game.getCurrentTurn().terminate(game);
            new DiscardAllResources ().perform(game, player);
            game.setNextPlayer();
        }
    }
}
