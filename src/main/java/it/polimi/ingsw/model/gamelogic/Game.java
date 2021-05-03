package it.polimi.ingsw.model.gamelogic;

import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.gamelogic.actions.GameBoard;
import it.polimi.ingsw.model.gamelogic.actions.PersonalBoard;
import it.polimi.ingsw.model.gamelogic.actions.Player;
import java.util.ArrayList;
import java.util.LinkedList;
import static java.util.Collections.shuffle;

/**
 * This class represent the abstract class of the game
 */
public abstract class Game {

    /**
     * It's the Max Number of Players in a game
     */
    private static final int MAX_NUM_PLAYER = 4;

    /**
     * the number of Players in this Game
     */
    private final int numberOfPlayers;

    /**
     * All the players, linked according to the set order of the round
     */
    private LinkedList<Player> playersOrder;

    /**
     * the player who is playing
     */
    private Player currentPlayer;

    /**
     * the ongoing turn, played by the currentPlayer
     */
    private Turn currentTurn;

    /**
     * the gameboard of the game
     */
    private GameBoard gameBoard;


    /**
     * Constructor that will be reused in the subclasses
     * @param numberOfPlayers
     */
    protected Game(int numberOfPlayers) throws IllegalNumberOfPlayersException {
        if (numberOfPlayers <= 0 || numberOfPlayers > MAX_NUM_PLAYER)
            throw new IllegalNumberOfPlayersException();
        this.numberOfPlayers = numberOfPlayers;
        this.playersOrder = new LinkedList<>();
    }


    /**
     * This method prepare the game, setting the players' order, creating the first turn, setting the first Player, and
     * the game board
     */
    public void setup(PersonalBoard personalBoard, GameBoard gameBoard) {
        this.setPlayersOrder();
        for (Player player : this.playersOrder)
            player.buildBoard(personalBoard);
        this.gameBoard = gameBoard;
        this.currentPlayer = playersOrder.getFirst();
        this.currentTurn = new Turn();
        this.currentTurn.start();
    }


    /**
     * This method creates a Player in the game for a User.
     * @return the new Player
     * @throws TooManyPlayersException if the game has already reached the number of players setted for that specific
     * instance of the game
     */
    public Player createPlayer() throws TooManyPlayersException {
        Player newPlayer = null;
        if(playersOrder.size() <= numberOfPlayers) {
            newPlayer = new Player();
            playersOrder.add(newPlayer);
        }
        else
            throw new TooManyPlayersException();
        return newPlayer;
    }


    /**
     *
     * @throws NoValidActionException
     * @throws IsNotCurrentPlayerException
     * @throws WrongCommandException
     */
    public void performCommandOf(Player player, Action action) throws Exception {
        Player currentPlayer = getCurrentPlayer();
        if (player == currentPlayer) {
            this.getCurrentTurn().add(action);
            action.perform(this, currentPlayer);
        }
        else
            throw new IsNotCurrentPlayerException();
    }


    /**
     * This method sets the order of the Players in a random way
     */
    private void setPlayersOrder() {
        shuffle(this.playersOrder);
    }


    /**
     * This method sets the next currentPlayer, according with the playersOrder, and creates and starts the next new
     * currentPlayer's Turn.
     */
    protected void setNextPlayer() {
        int currPlayerIndex = this.playersOrder.indexOf(currentPlayer);
        try {
            this.currentPlayer = this.playersOrder.get(currPlayerIndex + 1);
        } catch (IndexOutOfBoundsException e) {
            this.currentPlayer = this.playersOrder.getFirst();
        }
        this.currentTurn = new Turn();
        currentTurn.start();
    }


    /**
     * @return the current ongoing Turn
     */
    public Turn getCurrentTurn() {
        return this.currentTurn;
    }


    /**
     *
     * @return the ArrayList of all the actual (not copies) Players registered in the instance of the game on which this method has been called.
     * This ArrayList is ordered according to the Players' order of registration in the game.
     * @throws NotEnoughPlayersException if has not been reached the numberOfPlayers registered when this method is called.
     */
    public ArrayList<Player> getAllPlayers() {
        return new ArrayList (this.playersOrder);
    }


    /**
     * @return the player that is playing when this method is called
     */
    protected Player getCurrentPlayer () {
        return currentPlayer;
    }


    public GameBoard getGameBoard() {
        return this.gameBoard;
    }

}
