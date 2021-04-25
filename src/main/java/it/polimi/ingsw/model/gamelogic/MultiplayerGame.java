package it.polimi.ingsw.model.gamelogic;

import it.polimi.ingsw.exception.NotEnoughPlayersException;
import it.polimi.ingsw.model.gamelogic.actions.GameBoard;
import it.polimi.ingsw.model.gamelogic.actions.Player;
import java.util.*;

/**
 * Class that represents one match with 2 or more players.
 */
public class MultiplayerGame extends Game {

    /**
     * the board of the game, common to all the players
     */
    private GameBoard gameBoard;


    /**
     * Constructor.
     * @param numberOfPlayers
     */
    protected MultiplayerGame(int numberOfPlayers) {
        super(numberOfPlayers);
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


    /**
     * @param players
     * @return the list of players ordered in a shuffle way
     */
    @Override
    protected ArrayList<Player> setPlayersOrder(ArrayList<Player> players) {
        Collections.shuffle(players);
        return players;
    }

    @Override
    public GameBoard getGameBoard() {
        return null;
    }
}
