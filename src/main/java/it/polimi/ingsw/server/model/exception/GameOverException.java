package it.polimi.ingsw.server.model.exception;

import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;

import java.util.ArrayList;

public class GameOverException extends Exception{
    public GameOverException(String message) {
        super(message);
    }

    public void handleEndGame(Game game, ArrayList<Player> players) {

    }
}
