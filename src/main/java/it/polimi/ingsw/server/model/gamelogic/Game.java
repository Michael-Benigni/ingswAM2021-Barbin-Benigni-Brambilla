package it.polimi.ingsw.server.model.gamelogic;

import it.polimi.ingsw.server.model.GameComponent;
import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.actions.*;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.config.ServerPrefs;
import it.polimi.ingsw.utils.network.Header;
import it.polimi.ingsw.utils.network.MessageWriter;
import it.polimi.ingsw.utils.network.Sendable;
import java.util.ArrayList;
import java.util.LinkedList;

import static java.util.Collections.shuffle;

/**
 * This class represent the abstract class of the game
 */
public abstract class Game implements GameComponent {

    /**
     * It's the Max Number of Players in a game
     */
    private static final int MAX_NUM_PLAYER = ServerPrefs.getMaxNumOfPlayers();

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
     * the observers of the game: this is to implement the pattern Observer-Subgect
     */
    private ArrayList<Observer> observers;


    /**
     * Constructor that will be reused in the subclasses
     * @param numberOfPlayers integer that represents the number of players playing this match.
     */
    protected Game(int numberOfPlayers) throws IllegalNumberOfPlayersException {
        this.observers = new ArrayList<> ();
        if (numberOfPlayers <= 0 || numberOfPlayers > MAX_NUM_PLAYER)
            throw new IllegalNumberOfPlayersException();
        this.numberOfPlayers = numberOfPlayers;
        this.playersOrder = new LinkedList<>();
        this.gameIsOver = false;
    }


    /**
     * @return true if the game is over, otherwise returns false
     */
    public boolean isGameOver() {
        return gameIsOver;
    }


    /**
     * This method prepare the game, setting the players' order, creating the first turn, setting the first Player, and
     * the game board
     */
    public void setup(ArrayList<PersonalBoard> personalBoards, GameBoard gameBoard, ArrayList<InitialParams> params) throws IllegalNumberOfPlayersException, WrongBoardException, CellNotFoundInFaithTrackException {
        if (isReadyToStart ()) {
            this.params = params;
            this.setPlayersOrder ();
            for (Player player : this.playersOrder) {
                int index = getPlayerIndex (player);
                player.buildBoard (personalBoards.get (index));
                player.setPosition (index + 1);
            }
            playersOrder.forEach ((p1) -> playersOrder.stream().filter ((p2) -> p2 != p1).forEach ((p3) -> p1.notifyUpdate (p3.getPlayerInfoForOtherUpdate ())));
            this.gameBoard = gameBoard;
            this.gameBoard.prepare (getAllPlayers ());
            this.playersOrder.forEach ((player) -> this.gameBoard.attachToAllComponents (player));
            this.currentTurn = new FirstTurn ();
            this.currentTurn.start (this);
            this.currentPlayer = playersOrder.getFirst ();
            this.currentPlayer.notifyUpdate (currentTurn.getNextPlayerMessage (this));
            this.sendWaitMessage ();
            this.numberOfRounds = 0;
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
            throw new TooManyPlayersException(numberOfPlayers);
        return newPlayer;
    }


    /**
     * @return true if is reached the number of the players decided at the game creation
     */
    private boolean isReadyToStart() {
        return numberOfPlayers == getAllPlayers ().size ();
    }

    /**
     * Method that executes the action in input, but only if the provided player is the current player.
     */
    public void performActionOf(Player player, Action action) throws Exception {
        if (!isGameOver ()) {
            Player currentPlayer = getCurrentPlayer ();
            if (player == currentPlayer) {
                this.getCurrentTurn ().add (action);
                try {
                    action.perform (this, currentPlayer);
                } catch (GameOverException e) {
                    if (player != playersOrder.getLast ())
                        notifyLastRoundUpdate ();
                    setLastRound ();
                    e.handleEndGame(this, getAllPlayers ());
                }
            } else
                throw new IsNotCurrentPlayerException (currentPlayer.getUsername());
        } else
            throw new NoValidActionException (action);
    }

    protected void notifyLastRoundUpdate() {
        this.playersOrder.forEach ((p) -> p.notifyUpdate (getLastRoundUpdate()));
    }

    private Sendable getLastRoundUpdate() {
        MessageWriter writer = new MessageWriter ();
        writer.setHeader (Header.ToClient.LAST_ROUND_UP);
        return writer.write ();
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
    public void setNextPlayer() throws EndGameException {
        int currPlayerIndex = this.playersOrder.indexOf(currentPlayer);
        try {
            this.currentPlayer = this.playersOrder.get(currPlayerIndex + 1);
        } catch (IndexOutOfBoundsException e) {
            this.currentPlayer = this.playersOrder.getFirst();
            if(numberOfRounds == -1) {
                this.gameIsOver = true;
                onGameOver();
                return;
            }
            numberOfRounds++;
        }
        nextTurn();
        this.currentPlayer.notifyUpdate (currentTurn.getNextPlayerMessage (this));
        sendWaitMessage();
        if (!this.currentPlayer.isConnected ()) {
            boolean isAnyoneConnected = this.playersOrder.stream ().map (Player::isConnected).reduce (true, Boolean::logicalAnd);
            if (isAnyoneConnected)
                setNextPlayer ();
            else {
                this.gameIsOver = true;
                throw new EndGameException();
            }
        }
    }

    private void sendWaitMessage() {
        this.playersOrder.stream ().filter ((p1) -> p1 != currentPlayer)
                .forEach ((p2) -> p2.notifyUpdate (getWaitMessage ()));
    }

    private void onGameOver() {
        if (isGameOver ()) {
            ArrayList<Player> winners = computeWinners ();
            playersOrder.forEach ((p) -> p.notifyUpdate (getEndGameUpdate(winners)));
        }
    }

    private Sendable getEndGameUpdate(ArrayList<Player> winners) {
        MessageWriter writer = new MessageWriter ();
        writer.setHeader (Header.ToClient.GAME_OVER_UP);
        String VPs = "";
        String usernames = "";
        for (Player player : playersOrder) {
            if (winners.contains (player)) {
                usernames = "winnersNames";
                VPs = "winnersVPs";
            }
            else {
                usernames = "losersNames";
                VPs = "losersVPs";
            }
            writer.addProperty (VPs, player.computeAllVP ().getPoints ());
            writer.addProperty (usernames, player.getUsername ());
        }
        ArrayList<Integer> VPAsArray = new ArrayList<> ();
        ArrayList<String> usernamesAsArray = new ArrayList<> ();
        if (winners.size () == 1) {
            usernamesAsArray.add (winners.get (0).getUsername ());
            VPAsArray.add (winners.get (0).computeAllVP ().getPoints ());
            writer.resetProperty (VPs, VPAsArray);
            writer.resetProperty (usernames, usernamesAsArray);
        } else if (winners.size () == numberOfPlayers - 1) {
            for (Player player : playersOrder)
                if (!winners.contains (player)) {
                    usernamesAsArray.add (player.getUsername ());
                    VPAsArray.add (player.computeAllVP ().getPoints ());
                    writer.resetProperty (VPs, VPAsArray);
                    writer.resetProperty (usernames, usernamesAsArray);
                }
        }
        return writer.write ();
    }


    /**
     * This method sends to all the Observers of the players different from the current one that they have to wait that
     * the current player ends his turn.
     */
    private Sendable getWaitMessage() {
        MessageWriter writer = new MessageWriter ();
        writer.setHeader (Header.ToClient.WAIT_YOUR_TURN);
        writer.addProperty ("currPlayer", currentPlayer.getPosition ());
        writer.addProperty("currPlayerName", currentPlayer.getUsername());
        return writer.write ();
    }


    /**
     * this method starts the next turn
     */
    private void nextTurn() {
        if (numberOfRounds == 0)
            this.currentTurn = new FirstTurn();
        else {
            this.currentTurn = new Turn();
        }
        this.currentTurn.start(this);
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
     * Method that calculates the total victory points for each players, then returns the array of winners.
     */
    private ArrayList<Player> computeWinners() {
        ArrayList<Player> winners = new ArrayList<>();
        if (isGameOver ()) {
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
                if(player.computeAllVP().equals(winnerPoints) && !winners.contains (player))
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


    /**
     * @return the Iterable object of Observers of this.
     */
    @Override
    public ArrayList<Observer> getObservers() {
        return this.observers;
    }


    /**
     * This method is used to attach the observer to the object that implements this interface
     *
     * @param observer
     */
    @Override
    public void attach(Observer observer) {
        this.observers.add (observer);
    }

    public abstract void performEndTurnAction() throws WrongCellIndexException, CellNotFoundInFaithTrackException, GameOverByFaithTrackException, WrongInitialConfiguration, NegativeVPAmountException, YouMustEndTheProductionPhaseException, EndGameException, GameOverByCardsGridException, GameOverBlackCrossAtEndOfFaithTrackException;

    public void reconnectionOf(Player player) {
        player.setPosition (player.getPosition ());
        playersOrder.forEach ((p) -> player.notifyUpdate (p.getPlayerInfoForOtherUpdate ()));
        player.notifyUpdate (getWaitMessage ());
    }

    @Override
    public void detach(Observer observer) {
        getObservers ().remove(observer);
    }

    public int getCurrentRound() {
        return numberOfRounds + 1;
    }
}

