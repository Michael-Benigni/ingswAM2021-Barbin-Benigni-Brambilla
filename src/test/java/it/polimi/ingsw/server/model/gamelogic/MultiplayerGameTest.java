package it.polimi.ingsw.server.model.gamelogic;

import it.polimi.ingsw.server.model.exception.IllegalNumberOfPlayersException;
import it.polimi.ingsw.server.model.exception.NegativeResourceAmountException;
import it.polimi.ingsw.server.model.exception.TooManyPlayersException;
import it.polimi.ingsw.server.model.cards.leadercards.LeaderCardsDeckTest;
import it.polimi.ingsw.server.model.gamelogic.actions.Action;
import it.polimi.ingsw.server.model.gamelogic.actions.GameBoard;
import it.polimi.ingsw.server.model.gamelogic.actions.PersonalBoard;
import it.polimi.ingsw.server.model.gameresources.faithtrack.FaithTrackTest;
import it.polimi.ingsw.server.model.gameresources.stores.WarehouseDepots;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class MultiplayerGameTest {
    private static MultiplayerGame gameNoPlayers;
    private static MultiplayerGame game3Players;
    private static MultiplayerGame game4Players;
    private static MultiplayerGame gameNegativeNumOfPlayers;
    private static Action testAction;
    private GameBoard gameBoard;
    private ArrayList<PersonalBoard> personalBoards;

    @BeforeEach
    void init() throws IllegalNumberOfPlayersException, TooManyPlayersException, NegativeResourceAmountException {
        gameBoard = new GameBoard(new FaithTrackTest().initFaithTrack(), null, null, new LeaderCardsDeckTest().getLeaderCardsDeck());
        personalBoards = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            personalBoards.add(new PersonalBoard(new WarehouseDepots(0, new ArrayList<>()), 4, 3, 4, 2));
        gameNoPlayers = new MultiplayerGame(4);
        game4Players = new MultiplayerGame(4);
        game3Players = new MultiplayerGame(4);
        game4Players = new MultiplayerGame(4);
        game4Players.createPlayer();
        game4Players.createPlayer();
        game4Players.createPlayer();
        game4Players.createPlayer();
        game3Players.createPlayer();
        game3Players.createPlayer();
        game3Players.createPlayer();
    }

    @Test
    void createPlayerFor1() {
        try {
            gameNoPlayers.createPlayer();
        } catch (TooManyPlayersException e) {
            fail();
        }
    }

    @Test
    void createPlayerFor2() {
        try {
            game4Players.createPlayer();
        } catch (TooManyPlayersException e) {
            assertTrue(true);
        }
    }

    @Test
    void negativeNumOfPlayers() {
        try {
            gameNegativeNumOfPlayers = new MultiplayerGame(-1);
            fail();
        } catch (IllegalNumberOfPlayersException e) {
            assertTrue(true);
            assertNull(gameNegativeNumOfPlayers);
        }
    }

    @Test
    void negativeNumOfPlayers1() {
        try {
            gameNegativeNumOfPlayers = new MultiplayerGame(0);
            fail();
        } catch (IllegalNumberOfPlayersException e) {
            assertTrue(true);
            assertNull(gameNegativeNumOfPlayers);
        }
    }

    @Test
    void getAllPlayers() {
        ArrayList<Player> players = game4Players.getAllPlayers();
        if (players.size() == 4) {
            assertTrue(true);
        }
    }

    @Test
    void getAllPlayers1() throws TooManyPlayersException {
        Player player1 = gameNoPlayers.createPlayer();
        Player player2 = gameNoPlayers.createPlayer();
        Player player3 = gameNoPlayers.createPlayer();
        Player player4 = gameNoPlayers.createPlayer();
        ArrayList<Player> createdPlayers = new ArrayList<>();
        createdPlayers.add(player1);
        createdPlayers.add(player2);
        createdPlayers.add(player3);
        createdPlayers.add(player4);
        ArrayList<Player> gotPlayers = gameNoPlayers.getAllPlayers();
        assertTrue(gotPlayers.size() == 4);
        assertTrue(gotPlayers.containsAll(createdPlayers));
        for (int i = 0; i < 4; i++)
            assertTrue(gotPlayers.get(i) == player1 || gotPlayers.get(i) == player2 ||
                    gotPlayers.get(i) == player3 || gotPlayers.get(i) == player4);
    }

    @Test
    void setNextPlayer() throws IllegalNumberOfPlayersException {
        game4Players.setup(personalBoards, gameBoard, new ArrayList<>());
        Player before = game4Players.getCurrentPlayer();
        game4Players.setNextPlayer();
        Player after = game4Players.getCurrentPlayer();
        assertNotSame(before, after);
    }

    @Test
    void getCurrentTurn() throws IllegalNumberOfPlayersException {
        game4Players.setup(personalBoards, gameBoard, new ArrayList<>());
        Turn before = game4Players.getCurrentTurn();
        game4Players.setNextPlayer();
        Turn after = game4Players.getCurrentTurn();
        assertNotSame(before, after);
        assertInstanceOf(FirstTurn.class, before);
        assertInstanceOf(FirstTurn.class, after);
    }

    @Test
    void getCurrentPlayer() throws IllegalNumberOfPlayersException {
        game4Players.setup(personalBoards, gameBoard, new ArrayList<>());
        Player before = game4Players.getCurrentPlayer();
        game4Players.setNextPlayer();
        Player after = game4Players.getCurrentPlayer();
        assertNotSame(before, after);
        assertInstanceOf(Player.class, before);
        assertInstanceOf(Player.class, after);
    }

    /* Actions not visible here: TODO: move this test in each ActionTest
    @Test
    void performCommand() throws Exception {
        game4Players.setup(null, null);
        try {
            Player previousPlayer = game4Players.getCurrentPlayer();
            game4Players.setNextPlayer();
            game4Players.performCommandOf(previousPlayer, testAction);
        } catch (IsNotCurrentPlayerException e) {
            assertTrue(true);
        }
        try {
            game4Players.performCommandOf(game4Players.getCurrentPlayer(), testAction);
        } catch (IsNotCurrentPlayerException e) {
            fail();
        }
    }*/
}