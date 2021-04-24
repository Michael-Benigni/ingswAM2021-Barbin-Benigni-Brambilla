package it.polimi.ingsw.model.gamelogic;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.exception.NotEnoughPlayersException;
import it.polimi.ingsw.exception.TooManyPlayersException;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;

/**
 * Class that represents one match with 1 player, that will play with a COM
 */
public class SingleplayerGame extends Game {

    /**
     * Constructor. The number of players is 1 + 1 for the COM
     */
    SingleplayerGame() {
        super(2);
    }


    /**
     * Creates a Player for a User, and since in this game the Player is one, and he'll play with the COM, it's created
     * an alias for a fictitious User "Lorenzo_The_Magnificent" that will represent the COM in the game.
     * @param user
     * @return
     * @throws TooManyPlayersException
     */
    @Override
    public Player createPlayerFor(User user) throws TooManyPlayersException {
        Player player = super.createPlayerFor(user);
        try {
            createPlayerFor(new User("Lorenzo_The_Magnificent"));
        } catch (TooManyPlayersException e) {
        }
        return player;
    }

    @Override
    public GameBoard getGameBoard() {
        return null;
    }


    /**
     * This method prepare the game, setting the players' order, creating the first turn, setting the first Player, and
     * the game board
     * @throws NotEnoughPlayersException
     */
    @Override
    public void setup() throws NotEnoughPlayersException {
        super.setup();
    }
}
