package it.polimi.ingsw.server.model.gamelogic;

import it.polimi.ingsw.server.model.exception.IllegalNumberOfPlayersException;
import it.polimi.ingsw.server.model.exception.NotEnoughPlayersException;
import it.polimi.ingsw.server.model.gamelogic.actions.GameBoard;
import it.polimi.ingsw.server.model.gamelogic.actions.PersonalBoard;

import java.util.ArrayList;

/**
 * Class that represents one match with 2 or more players.
 */
public class MultiplayerGame extends Game {


    /**
     * Constructor.
     * @param numberOfPlayers
     */
    protected MultiplayerGame(int numberOfPlayers) throws IllegalNumberOfPlayersException {
        super(numberOfPlayers);
        if (numberOfPlayers == 1)
            throw new IllegalNumberOfPlayersException();
    }


    /**
     * This method prepare the game, setting the players' order, creating the first turn, setting the first Player, and
     * the game board
     * @throws NotEnoughPlayersException
     */
    @Override
    public void setup(ArrayList<PersonalBoard> personalBoards, GameBoard gameBoard, ArrayList<InitialParams> params) throws IllegalNumberOfPlayersException {
        super.setup(personalBoards, gameBoard, params);
    }

    @Override
    public GameBoard getGameBoard() {
        return super.getGameBoard();
    }
}
