package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.NegativeVPAmountException;
import it.polimi.ingsw.exception.NotEnoughPlayersException;
import it.polimi.ingsw.exception.TooManyPlayersException;

import java.util.ArrayList;

/**
 * Class that represents one match with 2 or more players.
 */
public class MultiplayerGame {

    int numberOfPlayers;
    ArrayList<Player> arrayOfPlayers;
    GameBoard gameBoard;


    public MultiplayerGame(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        this.gameBoard = new GameBoard();
    }

    public void addPlayer(String username) throws NegativeVPAmountException, TooManyPlayersException {
        if(arrayOfPlayers.size() < numberOfPlayers) {
            Player newPlayer = new Player(username, gameBoard);
        }
        else{
            throw new TooManyPlayersException();
        }
    }

    public void prepareGame() throws Exception {
        if(arrayOfPlayers.size() == numberOfPlayers) {
            gameBoard.prepareGameBoard(arrayOfPlayers);
        }
        else{
            throw new NotEnoughPlayersException();
        }
    }
}
