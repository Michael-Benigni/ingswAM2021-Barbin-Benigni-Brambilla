package it.polimi.ingsw.model.gamelogic;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.exception.IllegalNumberOfPlayersException;
import it.polimi.ingsw.exception.NotEnoughPlayersException;
import it.polimi.ingsw.exception.TooManyPlayersException;
import it.polimi.ingsw.exception.UserAlreadyPresentInThisGame;
import it.polimi.ingsw.model.gamelogic.actions.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MultiplayerGameTest {
    private static MultiplayerGame gameNoPlayers;
    private static MultiplayerGame game3Players;
    private static MultiplayerGame game4Players;
    private static User user1;
    private static User user2;
    private static User user3;
    private static User user4;
    private static MultiplayerGame gameNegativeNumOfPlayers;
    private static Action testAction;

    @BeforeEach
    void init() throws IllegalNumberOfPlayersException, TooManyPlayersException, UserAlreadyPresentInThisGame {
        testAction = new StartTurnAction();
        user1 = new User("user1");
        user2 = new User("user2");
        user3 = new User("user3");
        user4 = new User("user4");
        gameNoPlayers = new MultiplayerGame(4);
        game4Players = new MultiplayerGame(4);
        game3Players = new MultiplayerGame(4);
        game4Players = new MultiplayerGame(4);
        game4Players.createPlayerFor(user1);
        game4Players.createPlayerFor(user2);
        game4Players.createPlayerFor(user3);
        game4Players.createPlayerFor(user4);
        game3Players.createPlayerFor(new User("user1"));
        game3Players.createPlayerFor(new User("user2"));
        game3Players.createPlayerFor(new User("user3"));
    }

    @Test
    void createPlayerFor1() {
        try {
            gameNoPlayers.createPlayerFor(new User ("test"));
        } catch (TooManyPlayersException | UserAlreadyPresentInThisGame e) {
            fail();
        }
    }

    @Test
    void createPlayerFor2() {
        try {
            game4Players.createPlayerFor(new User ("test"));
        } catch (TooManyPlayersException e) {
            assertTrue(true);
        } catch (UserAlreadyPresentInThisGame userAlreadyPresentInThisGame) {
            fail();
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
    void getAllPlayers1() throws TooManyPlayersException, UserAlreadyPresentInThisGame {
        Player player1 = gameNoPlayers.createPlayerFor(new User("user1"));
        Player player2 = gameNoPlayers.createPlayerFor(new User("user2"));
        Player player3 = gameNoPlayers.createPlayerFor(new User("user3"));
        Player player4 = gameNoPlayers.createPlayerFor(new User("user4"));
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
    void setNextPlayer() throws NotEnoughPlayersException {
        game4Players.setup(null, null);
        Player before = game4Players.getCurrentPlayer();
        game4Players.setNextPlayer();
        Player after = game4Players.getCurrentPlayer();
        assertNotSame(before, after);
    }

    @Test
    void getCurrentTurn() throws NotEnoughPlayersException {
        game4Players.setup(null, null);
        Turn before = game4Players.getCurrentTurn();
        game4Players.setNextPlayer();
        Turn after = game4Players.getCurrentTurn();
        assertNotSame(before, after);
        assertInstanceOf(Turn.class, before);
        assertInstanceOf(Turn.class, after);
    }

    @Test
    void getCurrentPlayer() {
        game4Players.setup(null, null);
        Player before = game4Players.getCurrentPlayer();
        game4Players.setNextPlayer();
        Player after = game4Players.getCurrentPlayer();
        assertNotSame(before, after);
        assertInstanceOf(Player.class, before);
        assertInstanceOf(Player.class, after);
    }

    /*@Test
    void performUserCommand() throws NotEnoughPlayersException {
        game4Players.setup(null, null);
        try {
            User user = game4Players.getCurrentUser();
            game4Players.performUserCommand(user, testAction);
        } catch (Exception e) {
            fail();
        }
    }*/
}