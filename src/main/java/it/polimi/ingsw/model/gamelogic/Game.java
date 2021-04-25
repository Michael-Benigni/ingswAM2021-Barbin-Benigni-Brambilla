package it.polimi.ingsw.model.gamelogic;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.gamelogic.actions.GameBoard;
import it.polimi.ingsw.model.gamelogic.actions.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * This class represent the abstract class of the game
 */
public abstract class Game {

    /**
     * the number of Players in this Game
     */
    private final int numberOfPlayers;

    /**
     * HashMap between Users and the correspondent Players
     */
    private HashMap<User, Player> userPlayerHashMap;

    /**
     * All the players, linked according to the setted order of the round
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
     * Constructor that will be reused in the subclasses
     * @param numberOfPlayers
     */
    protected Game(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        this.userPlayerHashMap = new HashMap<>();
    }


    /**
     * This method prepare the game, setting the players' order, creating the first turn, setting the first Player, and
     * the game board
     * @throws NotEnoughPlayersException (see getAllPlayers())
     */
    public void setup() throws NotEnoughPlayersException {
        this.playersOrder = new LinkedList<>(setPlayersOrder(getAllPlayers()));
        this.setNextPlayer();
    }


    /**
     * This method creates a Player in the game for a User.
     * @param user
     * @return the new Player
     * @throws TooManyPlayersException if the game has already reached the number of players setted for that specific
     * instance of the game
     */
    public Player createPlayerFor(User user) throws TooManyPlayersException {
        if(userPlayerHashMap.size() < numberOfPlayers) {
            Player newPlayer = new Player();
            userPlayerHashMap.put(user, newPlayer);
            return newPlayer;
        }
        else
            throw new TooManyPlayersException();
    }


    /**
     *
     * @param user
     * @throws NoValidActionException
     * @throws IsNotCurrentPlayerException
     * @throws WrongCommandException
     */
    public void performUserCommand(User user, Action action) throws Exception {
        Player player = userPlayerHashMap.get(user);
        if (player.equals(currentPlayer)) {
            this.getCurrentTurn().add(action);
            action.perform(this, player);
        }
        throw new IsNotCurrentPlayerException();
    }


    /**
     * @param players
     * @return the ArrayList of Players ordered in a certain way (see subclasses: by default the method return the same
     * ArrayList with the same order)
     */
    protected ArrayList<Player> setPlayersOrder(ArrayList<Player> players) {
        return players;
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
     * @return the ArrayList of all the Players registered in the instance of the game on which this method has been called.
     * This ArrayList is ordered according to the Players' order of registration in the game.
     * @throws NotEnoughPlayersException if has not been reached the numberOfPlayers registered when this method is called.
     */
    public ArrayList<Player> getAllPlayers() throws NotEnoughPlayersException {
        if (userPlayerHashMap.size() == numberOfPlayers) {
            ArrayList<Player> players = this.userPlayerHashMap
                    .values()
                    .stream()
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
            return players;
        }
        throw new NotEnoughPlayersException();
    }


    /**
     * @return the player that is playing when this method is called
     */
    protected Player getCurrentPlayer () {
        return currentPlayer;
    }

    public abstract GameBoard getGameBoard();

}
