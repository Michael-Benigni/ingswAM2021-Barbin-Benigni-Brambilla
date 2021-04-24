package it.polimi.ingsw.model.gamelogic;

import it.polimi.ingsw.exception.IllegalNumberOfPlayersException;

public class GameFactory {

    public Game MultiOrSingleplayerGame (int numberOfPlayers) throws IllegalNumberOfPlayersException {
        if (numberOfPlayers > 0) {
            if (numberOfPlayers == 1)
                return new SingleplayerGame();
            if (numberOfPlayers <= 4)
                return new MultiplayerGame(numberOfPlayers);
        }
        throw new IllegalNumberOfPlayersException();
    }
}
