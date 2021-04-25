package it.polimi.ingsw.model.gamelogic;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.exception.TooManyPlayersException;
import it.polimi.ingsw.model.gamelogic.actions.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MultiplayerGameTest {
    private static MultiplayerGame gameNoPlayers;
    private static MultiplayerGame gameWithPlayers;
    private static MultiplayerGame gameTooMuchPlayers;
    private static MultiplayerGame gameNegativeNumOfPlayers;

    @BeforeAll
    static void init() {
        gameNoPlayers = new MultiplayerGame(4);
        gameTooMuchPlayers = new MultiplayerGame(5);
        gameNegativeNumOfPlayers = new MultiplayerGame(-1);
    }

    @BeforeAll
    static void initWithPlayers() {
        gameWithPlayers = new MultiplayerGame(4);
        try {
            gameWithPlayers.createPlayerFor(new User("user1"));
            gameWithPlayers.createPlayerFor(new User("user2"));
            gameWithPlayers.createPlayerFor(new User("user3"));
        } catch (TooManyPlayersException e) {
        }
    }

    @Test
    void createPlayerFor() {
        Player newPlayer;
        try {
            newPlayer = gameNoPlayers.createPlayerFor(new User ("test"));
            assertEquals(newPlayer, new Player());
        } catch (TooManyPlayersException e) {
            fail();
        }
    }

    @Test
    void performUserCommand() {
    }

    @Test
    void setPlayersOrder() {

    }

    @Test
    void setNextPlayer() {
    }

    @Test
    void getCurrentTurn() {
    }

    @Test
    void getCurrentPlayer() {
    }
}