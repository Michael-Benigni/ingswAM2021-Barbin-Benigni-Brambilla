package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.cards.actiontoken.SoloActionTokenDeck;
import it.polimi.ingsw.server.model.cards.actiontoken.SoloActionTokenDeckTest;
import it.polimi.ingsw.server.model.cards.developmentcards.DevelopmentCardsGrid;
import it.polimi.ingsw.server.model.cards.developmentcards.DevelopmentCardsGridTest;
import it.polimi.ingsw.server.model.cards.leadercards.LeaderCardsDeck;
import it.polimi.ingsw.server.model.cards.leadercards.LeaderCardsDeckTest;
import it.polimi.ingsw.server.model.exception.NegativeResourceAmountException;
import it.polimi.ingsw.server.model.gameresources.faithtrack.SoloPlayerFaithTrack;
import it.polimi.ingsw.server.model.gameresources.markettray.MarketTray;
import it.polimi.ingsw.server.model.gameresources.markettray.MarketTrayTest;

import static it.polimi.ingsw.server.model.gameresources.faithtrack.SoloPlayerFaithTrackTest.initSoloPlayerFaithTrack;

/**
 * Tests for the methods of "SoloPlayerGameBoard" class.
 */
public class SoloPlayerGameBoardTest {

    public static SoloPlayerGameBoard initSoloPlayerGameBoard() throws NegativeResourceAmountException {
        SoloPlayerFaithTrack faithTrack = initSoloPlayerFaithTrack();
        DevelopmentCardsGrid grid = DevelopmentCardsGridTest.initDevelopmentCardsGrid();
        MarketTray marketTray = MarketTrayTest.initMarketTray();
        LeaderCardsDeck deck = new LeaderCardsDeckTest().getLeaderCardsDeck();
        SoloActionTokenDeck actionTokenDeck = SoloActionTokenDeckTest.initSoloActionTokenDeck();
        SoloPlayerGameBoard gameBoard = new SoloPlayerGameBoard(faithTrack, grid, marketTray, deck, actionTokenDeck);
        return gameBoard;
    }
}
