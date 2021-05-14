package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.cards.developmentcards.DevelopmentCardsGrid;
import it.polimi.ingsw.server.model.cards.developmentcards.DevelopmentCardsGridTest;
import it.polimi.ingsw.server.model.cards.leadercards.LeaderCardsDeck;
import it.polimi.ingsw.server.model.cards.leadercards.LeaderCardsDeckTest;
import it.polimi.ingsw.server.model.exception.NegativeResourceAmountException;
import it.polimi.ingsw.server.model.gameresources.faithtrack.FaithTrack;
import it.polimi.ingsw.server.model.gameresources.faithtrack.FaithTrackTest;
import it.polimi.ingsw.server.model.gameresources.markettray.MarketTray;
import it.polimi.ingsw.server.model.gameresources.markettray.MarketTrayTest;

class GameBoardTest {

    public static GameBoard initGameBoard() throws NegativeResourceAmountException {
        FaithTrack faithTrack = new FaithTrackTest().initFaithTrack();
        DevelopmentCardsGrid grid = DevelopmentCardsGridTest.initDevelopmentCardsGrid();
        MarketTray marketTray = MarketTrayTest.initMarketTray();
        LeaderCardsDeck deck = new LeaderCardsDeckTest().getLeaderCardsDeck();
        GameBoard gameBoard = new GameBoard(faithTrack, grid, marketTray, deck);
        return gameBoard;
    }
}