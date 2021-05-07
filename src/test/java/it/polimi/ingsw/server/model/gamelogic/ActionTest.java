package it.polimi.ingsw.server.model.gamelogic;

import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.actions.GameBoard;
import it.polimi.ingsw.server.model.gamelogic.actions.PersonalBoard;
import it.polimi.ingsw.server.model.gameresources.stores.WarehouseDepots;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

class ActionTest {
    static Game game;

    Game setUp() {
        int [] ints = {1, 2, 3};
        List capacities = asList(ints);
        WarehouseDepots depots = new WarehouseDepots(3, new ArrayList<>(capacities));
        PersonalBoard personalBoard = new PersonalBoard(depots,3,3,4, 2);
        GameBoard gameBoard = new GameBoard(null, null, null, null);
        return null;
    }
}