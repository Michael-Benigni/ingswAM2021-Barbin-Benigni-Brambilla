package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.*;
import it.polimi.ingsw.server.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.server.model.gameresources.stores.WarehouseDepots;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Tests for "EndTurnMultiplayerAction" class.
 */
public class EndTurnMultiplayerActionTest {

    Game game;

    @BeforeEach
    private void init() throws IllegalNumberOfPlayersException, NegativeResourceAmountException, EmptyDeckException, TooManyPlayersException, WrongBoardException, CellNotFoundInFaithTrackException {
        game = new MultiplayerGame(4);
        ArrayList<PersonalBoard> personalBoards = new ArrayList<>();
        int numOfResourcesForProduction = 2;
        for (int i = 0; i < 4; i++)
            personalBoards.add(new PersonalBoard(numOfResourcesForProduction, new WarehouseDepots(0, new ArrayList<>()), 4, 3, 2, 2));
        GameBoard gameBoard = GameBoardTest.initGameBoard();
        ArrayList<InitialParams> listOfParams = new ArrayList<>();
        listOfParams.add(new InitialParams(0, 0));
        listOfParams.add(new InitialParams(0, 0));
        listOfParams.add(new InitialParams(0, 0));
        listOfParams.add(new InitialParams(0, 0));
        game.createPlayer();
        game.createPlayer();
        game.createPlayer();
        game.createPlayer();
        game.setup(personalBoards, gameBoard, listOfParams);
    }

    /**
     * Test on "perform" method of this class.
     * It tests if the method successfully empties the temporary container of the provided player and sets the next turn "AVAILABLE".
     */
    @Test
    void checkPerform() throws Exception {
        Action action = new MultiplayerGame.EndTurnMultiplayerAction ();
        Player player0 = game.getAllPlayers().get(0);
        player0.getPersonalBoard().getTempContainer().store(new StorableResource(ResourceType.COIN, 1));
        action.perform(game, player0);
        Turn turn = game.getCurrentTurn();
        try {
            turn.consumeToken();
            assertTrue(true);
        } catch(NoValidActionException e) {
            fail();
        }
    }

    /**
     * Test on "perform" method of this class.
     * It tests if the method successfully throws an exception if one player reaches the end of the faith track because
     * the amount of the discarded resource is too high.
     */
    @Test
    void checkPerformIfGameOver() throws Exception {
        Action action = new MultiplayerGame.EndTurnMultiplayerAction ();
        Player player0 = game.getAllPlayers().get(0);
        player0.getPersonalBoard().getTempContainer().store(new StorableResource(ResourceType.COIN, 120));
        try {
            action.perform(game, player0);
            fail();
        } catch (GameOverByFaithTrackException e) {
            assertTrue(true);
        }
    }
}
