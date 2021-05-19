package it.polimi.ingsw.server.model.gamelogic;


import it.polimi.ingsw.client.view.ToClientMessage;
import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.actions.Action;
import it.polimi.ingsw.server.model.gamelogic.actions.GameBoard;
import it.polimi.ingsw.server.model.gamelogic.actions.PersonalBoard;
import it.polimi.ingsw.server.model.gamelogic.actions.VictoryPoint;
import it.polimi.ingsw.server.controller.exception.WrongCommandException;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.Subject;
import it.polimi.ingsw.utils.config.Prefs;

import java.util.ArrayList;
import java.util.LinkedList;
import static java.util.Collections.shuffle;

/**
 * This class represent the abstract class of the game
 */
public abstract class Game  {

    /**
     * It's the Max Number of Players in a game
     */
    private static final int MAX_NUM_PLAYER = Prefs.getMaxNumOfPlayers();

    /**
     * the number of Players in this Game
     */
    private final int numberOfPlayers;

    /**
     * All the players, linked according to the set order of the round
     */
    private final LinkedList<Player> playersOrder;

    /**
     * board of the game, common to all the players
     */
    private GameBoard gameBoard;

    /**
     * number of rounds already played
     */
    private int numberOfRounds;

    /**
     * the player who is playing
     */
    private Player currentPlayer;

    /**
     * the ongoing turn, played by the currentPlayer
     */
    private Turn currentTurn;

    /**
     * this Arraylist represents the initial parameters for the players in the game according to their positions in the game
     * round, that is the index of this ArrayList.
     */
    private ArrayList<InitialParams> params;

    /**
     * true if the game is over, false if is not
     */
    private boolean gameIsOver;


    /**
     * Constructor that will be reused in the subclasses
     * @param numberOfPlayers
     */
    protected Game(int numberOfPlayers) throws IllegalNumberOfPlayersException {
        if (numberOfPlayers <= 0 || numberOfPlayers > MAX_NUM_PLAYER)
            throw new IllegalNumberOfPlayersException();
        this.numberOfPlayers = numberOfPlayers;
        this.playersOrder = new LinkedList<>();
        this.gameIsOver = false;
    }


    /**
     * This method prepare the game, setting the players' order, creating the first turn, setting the first Player, and
     * the game board
     */
    public void setup(ArrayList<PersonalBoard> personalBoards, GameBoard gameBoard, ArrayList<InitialParams> params) throws IllegalNumberOfPlayersException {
        if (isReadyToStart ()) {
            this.params = params;
            this.setPlayersOrder ();
            for (Player player : this.playersOrder) {
                int index = getPlayerIndex (player);
                player.buildBoard (personalBoards.get (index));
                player.setPosition (index);
            }
            this.gameBoard = gameBoard;
            this.gameBoard.prepare (getAllPlayers ());
            this.currentPlayer = playersOrder.getFirst ();
            this.numberOfRounds = 0;
            this.currentTurn = new FirstTurn ();
            this.currentTurn.start ();
        } else
            throw new IllegalNumberOfPlayersException ();
    }


    /**
     * This method creates a Player in the game for a User.
     * @return the new Player
     * @throws TooManyPlayersException if the game has already reached the number of players setted for that specific
     * instance of the game
     */
    public Player createPlayer() throws TooManyPlayersException {
        Player newPlayer;
        if(playersOrder.size() < numberOfPlayers) {
            newPlayer = new Player();
            playersOrder.add(newPlayer);
        }
        else
            throw new TooManyPlayersException();
        return newPlayer;
    }


    /**
     * @return true if is reached the number of the players decided at the game creation
     */
    private boolean isReadyToStart() {
        return numberOfPlayers == getAllPlayers ().size ();
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
    public void setNextPlayer() {
        int currPlayerIndex = this.playersOrder.indexOf(currentPlayer);
        try {
            this.currentPlayer = this.playersOrder.get(currPlayerIndex + 1);
        } catch (IndexOutOfBoundsException e) {
            this.currentPlayer = this.playersOrder.getFirst();
            if(numberOfRounds == -1) {
                this.gameIsOver = true;
                return;
            }
            numberOfRounds++;
        }
        if (numberOfRounds == 0)
            this.currentTurn = new FirstTurn();
        else {
            this.currentTurn = new Turn();
        }
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
        return new ArrayList<> (this.playersOrder);
    }


    /**
     * @return the player that is playing when this method is called
     */
    protected Player getCurrentPlayer () {
        return currentPlayer;
    }


    /**
     * @return the index of the current player, his position in the round
     */
    private int getPlayerIndex(Player player) {
        return this.playersOrder.indexOf(player);
    }


    /**
     * @return the game board
     */
    public GameBoard getGameBoard() {
        return this.gameBoard;
    }


    /**
     *
     */
    public ArrayList<Player> computeWinners() {
        ArrayList<Player> winners = new ArrayList<>();
        if (gameIsOver) {
            Player winner = playersOrder.getFirst();
            VictoryPoint winnerPoints = winner.computeAllVP();
            for (Player player : getAllPlayers()) {
                if(player.computeAllVP().moreThan(winnerPoints)) {
                    winnerPoints = player.computeAllVP();
                    winner = player;
                }
            }
            winners.add(winner);
            for (Player player : getAllPlayers())
                if(player.computeAllVP().equals(winnerPoints))
                    winners.add(player);
        }
        return winners;
    }


    /**
     * @param player
     * @return the initial parameters for this player, according to his position
     */
    public InitialParams getParams(Player player) {
        try {
            return params.get(getPlayerIndex(player));
        } catch (IndexOutOfBoundsException e) {
            return new InitialParams(0, 0);
        }
    }


    /**
     * Set the numberOfRounds to -1, as a convention to communicate that this is the last round to play
     */
    void setLastRound() {
        this.numberOfRounds = -1;
    }
}

