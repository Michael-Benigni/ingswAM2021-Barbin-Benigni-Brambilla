package it.polimi.ingsw.server.model.gamelogic;

import it.polimi.ingsw.server.model.exception.IllegalNumberOfPlayersException;
import it.polimi.ingsw.server.utils.config.ConfigLoader;

import java.io.FileNotFoundException;

public class GameFactory {
    private int numOfPlayers;
    private Game game;
    private ConfigLoader loader;

    public Game MultiOrSingleplayerGame (int numberOfPlayers) throws IllegalNumberOfPlayersException {
        this.numOfPlayers = numberOfPlayers;
        if (numberOfPlayers == 1)
            game = new SingleplayerGame();
        else
            game = new MultiplayerGame(numberOfPlayers);
        return game;
    }

    public void setup(Game game) throws FileNotFoundException, IllegalNumberOfPlayersException {
        loader = new ConfigLoader();
            if (game.getAllPlayers().size() != 1)
                game.setup(
                        loader.loadPersonalBoards(numOfPlayers),
                        loader.loadGameBoard(),
                        loader.loadInitialParams(numOfPlayers)
                );
            else
                game.setup(
                        loader.loadPersonalBoards(numOfPlayers),
                        loader.loadGameBoard1P(),
                        loader.loadInitialParams(numOfPlayers)
                );
    }
}
