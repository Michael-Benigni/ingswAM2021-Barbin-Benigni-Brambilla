package it.polimi.ingsw.server.model.gamelogic;

import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.actions.*;
import it.polimi.ingsw.server.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.server.model.gameresources.stores.WarehouseDepots;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TurnTest {
    private Turn gameTurn;
    private Action alwaysValid, endTurn, unique;
    private StrongboxAction payAction;
    MultiplayerGame game;
    Player player;

    @BeforeEach
    void init() throws Exception {
        alwaysValid = new LeaderAction("play", 1);
        payAction = new StrongboxAction("store", new StorableResource(ResourceType.COIN, 1));
        endTurn = new EndTurnAction();
        unique = new MarketAction(1, "row");
        game = new MultiplayerGame(4);
        ArrayList<PersonalBoard> personalBoards = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            personalBoards.add(new PersonalBoard(new WarehouseDepots(0, new ArrayList<>()), 4, 3, 2, 2));
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
        for (Player p: game.getAllPlayers()) {
            game.performActionOf(p, endTurn);
        }
        player = game.getCurrentPlayer();
        gameTurn = game.getCurrentTurn();
    }

    /**
     * Test on "add" method of this class.
     * It tests if the method throws successfully an exception when trying to add an action to a not started turn.
     */
    @Test
    void addWhenNotStarted() {
        Turn turn = new Turn();
        try {
            turn.add(alwaysValid);
            fail();
        } catch (NoValidActionException e) {
            fail();
        } catch (IllegalTurnState illegalTurnState) {
            assertTrue(true);
        }
        try {
            turn.add(endTurn);
            fail();
        } catch (NoValidActionException e) {
            fail();
        } catch (IllegalTurnState illegalTurnState) {
            assertTrue(true);
        }
    }

    /**
     * Test on "add" method of this class.
     * It tests if the method throws successfully an exception when trying to add a "MutualExclusiveAction" to a not started turn.
     */
    @Test
    void addUniqueWhenNotStarted() {
        Turn turn = new Turn();
        try {
            turn.add(unique);
            fail();
        } catch (NoValidActionException e) {
            assertTrue(true);
        } catch (IllegalTurnState illegalTurnState) {
            fail();
        }
    }

    /**
     * Test on "add" method of this class.
     * It tests if the method adds successfully an action when the turn is already started.
     */
    @Test
    void addAfterStart() {
        Turn turn = new Turn();
        turn.start(game);
        try {
            turn.add(alwaysValid);
            turn.add(unique);
            turn.add(alwaysValid);
            assertTrue(true);
        } catch (NoValidActionException | IllegalTurnState e) {
            fail();
        }
    }

    /**
     * Test on "add" method of this class.
     * It tests if the method successfully throws an exception when trying to add a "MutualExclusiveAction" 2 times in the same turn.
     */
    @Test
    void addUniqueTwoTimes() {
        Turn turn = new Turn();
        turn.start(game);
        try {
            turn.add(unique);
            turn.add(unique);
            fail();
        } catch (IllegalTurnState illegalTurnState) {
            fail();
        } catch (NoValidActionException e) {
            assertTrue(true);
        }
    }

    /**
     * Test on "add" method of this class.
     * It tests if the method successfully throws an exception when trying to add an action to an ended turn.
     */
    @Test
    void addAfterEnd() throws WrongInitialConfiguration {
        Turn turn = new Turn();
        turn.start(game);
        turn.terminate(game);
        try {
            turn.add(unique);
            fail();
        } catch (IllegalTurnState illegalTurnState) {
            fail();
        } catch (NoValidActionException e) {
            assertTrue(true);
        }
    }

    /**
     * Test on the "terminate" method of this class.
     * It tests if the method prevents adding actions after its activation.
     */
    @Test
    void terminateBeforeAction() throws WrongInitialConfiguration {
        Turn turn = new Turn();
        turn.start(game);
        turn.terminate(game);
        try {
            turn.add(unique);
            fail();
        } catch (IllegalTurnState illegalTurnState) {
            fail();
        } catch (NoValidActionException e) {
            assertTrue(true);
        }
    }

    /**
     * Test on the "terminate" method of this class.
     * It tests if the method prevents starting the same turn after its activation.
     */
    @Test
    void terminateBeforeStart() throws WrongInitialConfiguration {
        Turn turn = new Turn();
        turn.terminate(game);
        turn.start(game);
        try {
            turn.add(alwaysValid);
            fail();
        } catch (IllegalTurnState illegalTurnState) {
            assertTrue(true);
        } catch (NoValidActionException e) {
            fail();
        }
    }

    /**
     * Test on "addUndoableAction" method of this class.
     * It tests if the method works correctly also if the turn is not started yet.
     */
    @Test
    void addUndoableActionWhenNotStarted() {
        Turn turn = new Turn();
        turn.addUndoableAction(payAction);
        assertTrue(true);
    } //TODO: counterintuitive workflow: why pay if the turn hasn't started yet?

    /**
     * Test on "addUndoableAction" method of this class.
     * It tests if the method adds successfully a "payAction" when the turn is already started.
     */
    @Test
    void addUndoableActionAfterStart() {
        Turn turn = new Turn();
        turn.start(game);
        turn.addUndoableAction(payAction);
        assertTrue(true);
    }

    /**
     * Test on "addUndoableAction" method of this class.
     * It tests if the method successfully throws an exception when trying to add a "payAction" to an ended turn.
     */
    @Test
    void addUndoableActionAfterEnd() {
        Turn turn = new Turn();
        turn.start(game);
        try {
            turn.add(endTurn);
            assertTrue(true);
        } catch (IllegalTurnState | NoValidActionException e) {
            fail();
        }
        turn.addUndoableAction(payAction);
        assertTrue(true);
    } //TODO: counterintuitive workflow: why pay if the turn has ended yet?

    /**
     * Test on "undo" method of this class.
     * It tests if the method successfully reverts the changes
     * @throws Exception
     */
    @Test
    void undo() throws Exception {
        StrongboxAction store5 = new StrongboxAction("store", new StorableResource(ResourceType.STONE, 5));
        ArrayList<StorableResource> listOfResources = new ArrayList<>();
        listOfResources.add(new StorableResource(ResourceType.STONE, 5));
        gameTurn.addUndoableAction(store5);
        game.performActionOf(player, store5);
        assertEquals(listOfResources, player.getPersonalBoard().getStrongbox().getAllResources());
        gameTurn.undo(game, player);
        assertEquals(player.getPersonalBoard().getStrongbox().getAllResources(), new ArrayList<StorableResource>());
    }

    /**
     * Test on "consumeToken" method of this class
     * It tests if the method successfully throws an exception when the method is called twice.
     */
    @Test
    void consumeToken() {
        Turn turn = new Turn();
        turn.start(game);
        try {
            turn.consumeToken();
        } catch (NoValidActionException e) {
            fail();
        }
        try {
            turn.consumeToken();
            fail();
        } catch (NoValidActionException e) {
            assertTrue(true);
        }
    }

    /**
     * Test on "startProductionPhase" method of this class.
     * It tests if the method correctly changes the token from "AVAILABLE" to "AVAILABLE_FOR_PRODUCTION"
     */
    @Test
    void startProductionPhase() {
        Turn turn = new Turn();
        turn.start(game);
        try {
            turn.startProductionPhase();
        } catch (NoValidActionException e) {
            fail();
        }
        try {
            turn.consumeToken();
            fail();
        } catch (NoValidActionException e) {
            assertTrue(true);
        }
        try {
            turn.consumeTokenInProductionPhase();
            assertTrue(true);
        } catch (NoValidActionException e) {
            fail();
        }
    }

    /**
     * Test on "startProductionPhase" method of this class.
     * It tests if the method successfully throws an exception when called when the token is "UNAVAILABLE".
     */
    @Test
    void startProductionPhaseIfUnavailable() {
        Turn turn = new Turn();
        turn.start(game);
        try {
            turn.consumeToken();
            assertTrue(true);
        } catch (NoValidActionException e) {
            fail();
        }
        try {
            turn.startProductionPhase();
            fail();
        } catch (NoValidActionException e) {
            assertTrue(true);
        }
    }

    /**
     * Test on "consumeTokenInProductionPhase" method of this class
     * It tests if the method successfully throws an exception when trying to consume the token for the production without starting it before.
     */
    @Test
    void consumeTokenInProductionPhaseIfNotStarted() {
        Turn turn = new Turn();
        turn.start(game);
        try {
            turn.consumeTokenInProductionPhase();
            fail();
        } catch (NoValidActionException e) {
            assertTrue(true);
        }
        try {
            turn.consumeToken();
        } catch (NoValidActionException e) {
            fail();
        }
        try {
            turn.consumeTokenInProductionPhase();
            fail();
        } catch (NoValidActionException e) {
            assertTrue(true);
        }
    }

    /**
     * Test on "endProductionPhase" method of this class.
     * It tests if the method works correctly when the token is "AVAILABLE_FOR_PRODUCTION" and changes it to "UNAVAILABLE".
     */
    @Test
    void endProductionPhase() {
        Turn turn = new Turn();
        turn.start(game);
        try {
            turn.startProductionPhase();
        } catch (NoValidActionException e) {
            fail();
        }
        try {
            turn.endProductionPhase();
            assertTrue(true);
        } catch (NoValidActionException e) {
            fail();
        }
        try {
            turn.consumeTokenInProductionPhase();
            fail();
        } catch (NoValidActionException e) {
            assertTrue(true);
        }
        try {
            turn.consumeToken();
            fail();
        } catch (NoValidActionException e) {
            assertTrue(true);
        }
    }

    /**
     * Test on "endProductionPhase" method of this class.
     * It tests if the method successfully throws an exception when the token isn't "AVAILABLE_FOR_PRODUCTION"
     */
    @Test
    void endProductionPhaseWhenNotAvailable() {
        Turn turn = new Turn();
        turn.start(game);
        try {
            turn.endProductionPhase();
            fail();
        } catch (NoValidActionException e) {
            assertTrue(true);
        }
        try {
            turn.consumeToken();
        } catch (NoValidActionException e) {
            fail();
        }
        try {
            turn.endProductionPhase();
            fail();
        } catch (NoValidActionException e) {
            assertTrue(true);
        }
    }
}