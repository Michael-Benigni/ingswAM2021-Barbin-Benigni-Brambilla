package it.polimi.ingsw.utils.config;

import it.polimi.ingsw.server.model.exception.EmptyDeckException;
import it.polimi.ingsw.server.model.gamelogic.InitialParams;
import it.polimi.ingsw.server.model.gamelogic.actions.GameBoard;
import it.polimi.ingsw.server.model.gamelogic.actions.PersonalBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

class ConfigLoaderTest {
    private ConfigLoader loader;

    @BeforeEach
    void setUp() throws FileNotFoundException {
        loader = new ConfigLoader();
    }

    @Test
    void loadGameBoard() throws FileNotFoundException, EmptyDeckException {
        GameBoard board = loader.loadGameBoard();
    }

    @Test
    void loadPersonalBoard() throws FileNotFoundException {
        PersonalBoard board = loader.loadPersonalBoards(1).get(0);
    }

    @Test
    void loadInitialParams() throws FileNotFoundException {
        ArrayList<InitialParams> params = loader.loadInitialParams(4);
    }
}