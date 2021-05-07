package it.polimi.ingsw.server.model.gamelogic;

import it.polimi.ingsw.server.exception.IllegalNumberOfPlayersException;

public class GameFactory {

    public Game MultiOrSingleplayerGame (int numberOfPlayers) throws IllegalNumberOfPlayersException {
        if (numberOfPlayers == 1)
            return new SingleplayerGame();
        return new MultiplayerGame(numberOfPlayers);
    }
}
