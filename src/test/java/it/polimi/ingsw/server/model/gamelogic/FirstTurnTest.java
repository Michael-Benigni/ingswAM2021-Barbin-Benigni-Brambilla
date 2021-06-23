package it.polimi.ingsw.server.model.gamelogic;

import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.actions.*;
import it.polimi.ingsw.server.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.server.model.gameresources.stores.WarehouseDepots;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Tests for "FirstTurn" class.
 */
public class FirstTurnTest {

    /**
     * Test on "add" method of this class.
     * It tests if the method throws an exception when trying to add an action to a first turn.
     */
    @Test
    void checkAddIfCorrect() throws NegativeResourceAmountException {
        FirstTurn firstTurn = new FirstTurn();
        Action action = new StrongboxAction(PayAction.StoreOrRemove.STORE, new StorableResource(ResourceType.COIN, 1));
        try {
            firstTurn.add(action);
            fail();
        } catch (NoValidActionException e) {
            assertTrue(true);
        } catch (IllegalTurnState illegalTurnState) {
            fail();
        }
    }

    /**
     * Test on "terminate" method of this class.
     * It tests if the method successfully throws an exception when the game isn't ready to end the first turn.
     */
    @Test
    void checkTerminateIfGameNotReady() throws TooManyPlayersException, NegativeResourceAmountException, EmptyDeckException, IllegalNumberOfPlayersException, CellNotFoundInFaithTrackException, WrongBoardException {

        //Create a new multiplayer game.
        Game game = new MultiplayerGame(4);
        ArrayList<PersonalBoard> personalBoards = new ArrayList<>();
        int numOfResourcesForProduction = 2;
        int numOfResourcesToProduce = 1;
        for (int i = 0; i < 4; i++)
            personalBoards.add(new PersonalBoard(numOfResourcesForProduction, numOfResourcesToProduce, new WarehouseDepots(0, new ArrayList<>()), 4, 3, 0, 0));
        GameBoard gameBoard = GameBoardTest.initGameBoard();
        ArrayList<InitialParams> listOfParams = new ArrayList<>();
        listOfParams.add(new InitialParams(1, 0));
        listOfParams.add(new InitialParams(1, 0));
        listOfParams.add(new InitialParams(1, 0));
        listOfParams.add(new InitialParams(1, 0));
        game.createPlayer();
        game.createPlayer();
        game.createPlayer();
        game.createPlayer();
        game.setup(personalBoards, gameBoard, listOfParams);

        Turn turn = game.getCurrentTurn();
        try {
            turn.terminate(game);
            fail();
        } catch (WrongInitialConfiguration e) {
            assertTrue(true);
        } catch (YouMustEndTheProductionPhaseException e) {
            fail();
        }
    }

    /*@Test
    void checkTerminateIfCorrect() throws NegativeResourceAmountException, EmptyDeckException, IllegalNumberOfPlayersException, TooManyPlayersException, CellNotFoundInFaithTrackException, WrongBoardException, EndGameException {
        //Create a new multiplayer game.
        Game game = new MultiplayerGame(4);
        ArrayList<PersonalBoard> personalBoards = new ArrayList<>();
        int numOfResourcesForProduction = 2;
        int numOfResourcesToProduce = 1;
        for (int i = 0; i < 4; i++)
            personalBoards.add(new PersonalBoard(numOfResourcesForProduction, numOfResourcesToProduce, new WarehouseDepots(0, new ArrayList<>()), 4, 3, 0, 0));
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

        Turn turn = game.getCurrentTurn();
        Player player = game.getCurrentPlayer();
        player.setIsConnected(false, game);
        try {
            turn.terminate(game);
            fail();
        } catch (WrongInitialConfiguration e) {
            assertTrue(true);
        } catch (YouMustEndTheProductionPhaseException e) {
            fail();
        }
    }*/
}
