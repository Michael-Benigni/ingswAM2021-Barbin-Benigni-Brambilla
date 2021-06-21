package it.polimi.ingsw.server.model.gamelogic;

import it.polimi.ingsw.server.model.cards.actiontoken.SoloActionToken;
import it.polimi.ingsw.server.model.cards.actiontoken.SoloActionTokenDeck;
import it.polimi.ingsw.server.model.cards.actiontoken.SoloActionTokenDeckTest;
import it.polimi.ingsw.server.model.cards.developmentcards.*;
import it.polimi.ingsw.server.model.cards.leadercards.LeaderCardsDeck;
import it.polimi.ingsw.server.model.cards.leadercards.LeaderCardsDeckTest;
import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.actions.*;
import it.polimi.ingsw.server.model.gameresources.faithtrack.Cell;
import it.polimi.ingsw.server.model.gameresources.faithtrack.SoloPlayerFaithTrack;
import it.polimi.ingsw.server.model.gameresources.markettray.MarketTray;
import it.polimi.ingsw.server.model.gameresources.markettray.MarketTrayTest;
import it.polimi.ingsw.server.model.gameresources.stores.WarehouseDepots;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static it.polimi.ingsw.server.model.gameresources.faithtrack.SoloPlayerFaithTrackTest.initSoloPlayerFaithTrack;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for "SingleplayerGame" class
 */
class SingleplayerGameTest {


    /*public static SingleplayerGame initSingleplayerGame() {

    }*/

    /**
     * Test on "setup" method of this class.
     * It tests if the method successfully creates a new singleplayer game with one SoloPlayerGameBoard and one personal board.
     */
    @Test
    void setupIfCorrect() throws IllegalNumberOfPlayersException, NegativeResourceAmountException, EmptyDeckException, CellNotFoundInFaithTrackException, TooManyPlayersException {
        SingleplayerGame game = new SingleplayerGame();
        game.createPlayer();

        //Initial Params
        ArrayList<InitialParams> listOfParams = new ArrayList<>();
        listOfParams.add(new InitialParams(0, 0));

        //Personal boards
        PersonalBoard personalBoard = PersonalBoardTest.init();
        ArrayList<PersonalBoard> listOfBoards = new ArrayList<>();
        listOfBoards.add(personalBoard);

        //GameBoard
        GameBoard gameBoard = SoloPlayerGameBoardTest.initSoloPlayerGameBoard();

        try {
            game.setup(listOfBoards, gameBoard, listOfParams);
            assertInstanceOf(SoloPlayerGameBoard.class, game.getGameBoard());
            Player player = game.getCurrentPlayer();
            assertInstanceOf(InitialParams.class, game.getParams(player));
            assertInstanceOf(PersonalBoard.class, player.getPersonalBoard());
        } catch (WrongBoardException e) {
            fail();
        }
    }

    /**
     * Test on "createPlayer" method of this class.
     * It tests if the method adds successfully a new player to the game and if, when trying to add another player to
     * the same game, it throws correctly an exception.
     */
    @Test
    void checkIfTooManyPlayers() throws IllegalNumberOfPlayersException {
        SingleplayerGame game = new SingleplayerGame();
        try {
            game.createPlayer();
            assertTrue(game.getAllPlayers().size() > 0);
        } catch (TooManyPlayersException e) {
            fail();
        }
        try {
            game.createPlayer();
            fail();
        } catch (TooManyPlayersException e) {
            assertEquals(1, game.getAllPlayers().size());
        }
    }

    /**
     * Test on "setup" method of this class.
     * It tests if the method successfully throws an exception when the action token deck is null.
     */
    @Test
    void checkSetupWhenNoActionTokensDeck() throws IllegalNumberOfPlayersException, TooManyPlayersException, NegativeResourceAmountException, EmptyDeckException, CellNotFoundInFaithTrackException {
        SingleplayerGame game = new SingleplayerGame();
        game.createPlayer();

        //Initial Params
        ArrayList<InitialParams> listOfParams = new ArrayList<>();
        listOfParams.add(new InitialParams(0, 0));

        //Personal boards
        PersonalBoard personalBoard = PersonalBoardTest.init();
        ArrayList<PersonalBoard> listOfBoards = new ArrayList<>();
        listOfBoards.add(personalBoard);

        //GameBoard
        SoloPlayerFaithTrack faithTrack = initSoloPlayerFaithTrack();
        DevelopmentCardsGrid grid = DevelopmentCardsGridTest.initDevelopmentCardsGrid();
        MarketTray marketTray = MarketTrayTest.initMarketTray();
        LeaderCardsDeck deck = new LeaderCardsDeckTest().getLeaderCardsDeck();
        GameBoard gameBoard = new SoloPlayerGameBoard(faithTrack, grid, marketTray, deck, null);

        try {
            game.setup(listOfBoards, gameBoard, listOfParams);
            fail();
        } catch (WrongBoardException e) {
            assertTrue(true);
        }
    }

    /**
     * Test on "performEndTurnAction" method of this class.
     * It tests if the method successfully terminates the turn and activates the action token.
     */
    @Test
    void performEndTurnAction() throws NegativeResourceAmountException, EmptyDeckException, TooManyPlayersException, IllegalNumberOfPlayersException, CellNotFoundInFaithTrackException, WrongBoardException {
        SingleplayerGame game = new SingleplayerGame();
        game.createPlayer();

        //Initial Params
        ArrayList<InitialParams> listOfParams = new ArrayList<>();
        listOfParams.add(new InitialParams(0, 0));

        //Personal boards
        ArrayList<Integer> capacities = new ArrayList<>(Arrays.asList(1, 2, 3));
        PersonalBoard personalBoard = new PersonalBoard(2, 1,
                new WarehouseDepots(3, capacities),
                3, 3, 0, 0);
        ArrayList<PersonalBoard> listOfBoards = new ArrayList<>();
        listOfBoards.add(personalBoard);

        //Game board
        SoloPlayerFaithTrack faithTrack = initSoloPlayerFaithTrack();
        DevelopmentCardsGrid grid = DevelopmentCardsGridTest.initDevelopmentCardsGrid();
        MarketTray marketTray = MarketTrayTest.initMarketTray();
        LeaderCardsDeck deck = new LeaderCardsDeckTest().getLeaderCardsDeck();
        ArrayList<SoloActionToken> tokens = new ArrayList<>(0);
        SoloActionToken token1 = new SoloActionToken();
        token1.setMoveBlackCross(1);
        tokens.add(token1);
        SoloActionTokenDeck soloActionTokenDeck = new SoloActionTokenDeck(tokens);
        GameBoard gameBoard = new SoloPlayerGameBoard(faithTrack, grid, marketTray, deck, soloActionTokenDeck);

        game.setup(listOfBoards, gameBoard, listOfParams);
        Turn firstTurn = game.getCurrentTurn();
        Cell initialCell = faithTrack.getBlackCrossPosition();

        try {
            game.performEndTurnAction();
            Cell finalCell = faithTrack.getBlackCrossPosition();
            assertNotSame(initialCell, finalCell);
            assertNotSame(firstTurn, game.getCurrentTurn());
        } catch(Exception e) {
            fail();
        }
    }
}