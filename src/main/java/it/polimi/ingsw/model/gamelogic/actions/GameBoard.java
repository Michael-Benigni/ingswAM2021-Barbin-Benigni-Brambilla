package it.polimi.ingsw.model.gamelogic.actions;

import it.polimi.ingsw.model.cards.developmentcards.DevelopmentCardsGrid;
import it.polimi.ingsw.model.cards.leadercards.LeaderCardsDeck;
import it.polimi.ingsw.model.gameresources.faithtrack.FaithTrack;
import it.polimi.ingsw.model.gameresources.markettray.MarketTray;
import java.io.FileNotFoundException;
import java.util.ArrayList;


/**
 * Class that represents the game board, that's a collection of elements that are common to all players.
 */
public class GameBoard {

    private FaithTrack faithTrack;
    private DevelopmentCardsGrid developmentCardGrid;
    private MarketTray marketTray;
    private LeaderCardsDeck leaderCardsDeck;


    public GameBoard(FaithTrack faithTrack, DevelopmentCardsGrid developmentCardGrid, MarketTray marketTray, LeaderCardsDeck leaderCardsDeck) {
        this.faithTrack = faithTrack;
        this.developmentCardGrid = developmentCardGrid;
        this.marketTray = marketTray;
        this.leaderCardsDeck = leaderCardsDeck;
    }


    /**
     * Method that initializes the elements of this game board.
     * @param listOfPlayers -> array that contains all the players.
     * @return
     */
    GameBoard prepare(ArrayList<Player> listOfPlayers) throws FileNotFoundException {
        //TODO: initialize faithTrack with players
        return this;
    }


    /**
     * Getter method for "marketTray" attribute of this class.
     * @return -> the market tray.
     */
    public MarketTray getMarketTray() {
        return this.marketTray;
    }


    /**
     * Getter method for "faithTrack" attribute of this class.
     * @return -> the faith track.
     */
    public FaithTrack getFaithTrack() {
        return this.faithTrack;
    }


    /**
     * Getter method for "developmentCardsGrid" attribute of this class.
     * @return -> the faith track.
     */
    public DevelopmentCardsGrid getDevelopmentCardGrid() {
        return this.developmentCardGrid;
    }

}
