package it.polimi.ingsw.server.model.gamelogic;

import it.polimi.ingsw.server.exception.IllegalNumberOfPlayersException;
import it.polimi.ingsw.server.exception.NotEnoughPlayersException;
import it.polimi.ingsw.server.exception.TooManyPlayersException;
import it.polimi.ingsw.server.model.gamelogic.actions.GameBoard;
import it.polimi.ingsw.server.model.gamelogic.actions.PersonalBoard;
import it.polimi.ingsw.server.model.gamelogic.actions.SoloPlayerGameBoard;

import java.util.ArrayList;

/**
 * Class that represents one match with 1 player, that will play with a COM
 */
public class SingleplayerGame extends Game {

    SoloPlayerGameBoard gameBoard;

    /**
     * Constructor. The number of players is 1 + 1 for the COM
     */
    public SingleplayerGame() throws IllegalNumberOfPlayersException {
        super(1);
    }


    /**
     * Creates a Player for a User, and since in this game the Player is one, and he'll play with the COM, it's created
     * an alias for a fictitious User "Lorenzo_The_Magnificent" that will represent the COM in the game.
     * @return
     * @throws TooManyPlayersException
     */
    @Override
    public Player createPlayer() throws TooManyPlayersException {
        Player newPlayer = super.createPlayer();
        return newPlayer;
    }


    /**
     * This method prepare the game, setting the players' order, creating the first turn, setting the first Player, and
     * the game board
     * @throws NotEnoughPlayersException
     */
    @Override
    public void setup(ArrayList<PersonalBoard> personalBoards, GameBoard gameBoard, ArrayList<InitialParams> params) {
        this.gameBoard = (SoloPlayerGameBoard) gameBoard;
        this.gameBoard.prepare(getAllPlayers());
        super.setup(personalBoards, gameBoard, params);
    }

    @Override
    public SoloPlayerGameBoard getGameBoard() {
        return this.gameBoard;
    }
}
