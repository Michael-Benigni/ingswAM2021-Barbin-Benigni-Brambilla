package it.polimi.ingsw.model.gamelogic;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.exception.IllegalNumberOfPlayersException;
import it.polimi.ingsw.exception.NotEnoughPlayersException;
import it.polimi.ingsw.exception.TooManyPlayersException;
import it.polimi.ingsw.exception.UserAlreadyPresentInThisGame;
import it.polimi.ingsw.model.gamelogic.actions.GameBoard;
import it.polimi.ingsw.model.gamelogic.actions.PersonalBoard;
import it.polimi.ingsw.model.gamelogic.actions.Player;

/**
 * Class that represents one match with 1 player, that will play with a COM
 */
public class SingleplayerGame extends Game {
    private Player LORENZO_THE_MAGNIFICENT;

    /**
     * Constructor. The number of players is 1 + 1 for the COM
     */
    SingleplayerGame() throws IllegalNumberOfPlayersException {
        super(2);
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
        this.LORENZO_THE_MAGNIFICENT = createPlayer();
        return newPlayer;
    }


    /**
     * This method prepare the game, setting the players' order, creating the first turn, setting the first Player, and
     * the game board
     * @throws NotEnoughPlayersException
     */
    @Override
    public void setup(PersonalBoard personalBoard, GameBoard gameBoard) {
        super.setup(personalBoard, gameBoard);
        Player currPlayer = getCurrentPlayer();
        if (currPlayer == this.LORENZO_THE_MAGNIFICENT)
            setNextPlayer();
    }
}
