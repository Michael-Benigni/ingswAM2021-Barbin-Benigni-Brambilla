package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.cards.developmentcards.DevelopmentCardsGrid;
import it.polimi.ingsw.server.model.cards.leadercards.LeaderCardsDeck;
import it.polimi.ingsw.server.model.exception.CellNotFoundInFaithTrackException;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gameresources.faithtrack.FaithTrack;
import it.polimi.ingsw.server.model.gameresources.markettray.MarketTray;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.Subject;

import java.util.ArrayList;
import java.util.List;


/**
 * Class that represents the game board, that's a collection of elements that are common to all players.
 */
public class GameBoard {

    private FaithTrack faithTrack;
    private DevelopmentCardsGrid developmentCardGrid;
    private MarketTray marketTray;
    private LeaderCardsDeck leaderCardsDeck;


    /**
     * Constructor.
     * @param faithTrack
     * @param developmentCardGrid
     * @param marketTray
     * @param leaderCardsDeck
     */
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
    public void prepare(ArrayList<Player> listOfPlayers) {
        this.faithTrack.initMarkers(listOfPlayers);
        for (Player player : listOfPlayers) {
            int howManyCards = player.getPersonalBoard().getSlotLeaderCards().getMaxNumberOfCards();
            player.getPersonalBoard().getSlotLeaderCards().init(this.leaderCardsDeck.draw(howManyCards));
        }
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

    public void sendInitialUpdate() throws CellNotFoundInFaithTrackException {
        this.getDevelopmentCardGrid ().notifyInitialUpdate();
        this.getMarketTray ().notifyInitialUpdate ();
        this.getFaithTrack ().notifyInitialUpdate ();
    }
}
