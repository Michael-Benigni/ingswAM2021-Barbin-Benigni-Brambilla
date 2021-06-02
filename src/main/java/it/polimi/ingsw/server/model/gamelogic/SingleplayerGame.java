package it.polimi.ingsw.server.model.gamelogic;

import it.polimi.ingsw.server.model.exception.IllegalNumberOfPlayersException;
import it.polimi.ingsw.server.model.exception.NotEnoughPlayersException;
import it.polimi.ingsw.server.model.gamelogic.actions.GameBoard;
import it.polimi.ingsw.server.model.gamelogic.actions.PersonalBoard;
import it.polimi.ingsw.server.model.gamelogic.actions.SoloPlayerGameBoard;
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
     * the game board
     * @throws NotEnoughPlayersException
     */
    private void setup(ArrayList<PersonalBoard> personalBoards, SoloPlayerGameBoard gameBoard, ArrayList<InitialParams> params) throws IllegalNumberOfPlayersException {
        super.setup(personalBoards, gameBoard, params);
    }


    /**
     * This method prepare the game, setting the players' order, creating the first turn, setting the first Player, and
     * the game board
     * @throws NotEnoughPlayersException
     */
    @Override
    public void setup(ArrayList<PersonalBoard> personalBoards, GameBoard gameBoard, ArrayList<InitialParams> params) throws IllegalNumberOfPlayersException {
        if (gameBoard instanceof SoloPlayerGameBoard)
            setup(personalBoards, (SoloPlayerGameBoard) gameBoard, params);
        /*else
            throw WrongBoardException();*/
    }

    @Override
    public SoloPlayerGameBoard getGameBoard() {
        return (SoloPlayerGameBoard) super.getGameBoard();
    }

    @Override
    protected void attachToGameBoard() {
        super.attachToGameBoard ();
        getGameBoard ().getActionTokenDeck ().attachAll(new ArrayList<> ((Collection<? extends Observer>) getObservers ()));
    }
}
