package it.polimi.ingsw.client.view.lightweightmodel;

import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.utils.Attachable;

import java.util.ArrayList;

/**
 * Class that represents the view of the game board.
 */
public class LWGameBoard implements Attachable<UI> {

    /**
     * Grid of development cards.
     */
    private LWCardsGrid grid;

    /**
     * Faith track, that is implemented as an array of LWCell.
     */
    private ArrayList<LWCell> faithTrack;

    /**
     * Market of marbles
     */
    private LWMarket market;

    /**
     * User interface
     */
    private UI ui;

    /**
     * Constructor method of this class. It sets the market and the grid of cards as empty arrays.
     */
    public LWGameBoard() {
        this.market = new LWMarket (new ArrayList<> (), null);
        this.grid = new LWCardsGrid (new ArrayList<> (), 0, 0);
    }

    /**
     * Method that updates the development cards grid with the provided parameters.
     * @param initialCardsGrid list of cards to be inserted in the grid.
     * @param rows number of rows.
     * @param columns number of columns.
     */
    public void buildDevCardsGrid(ArrayList<ArrayList<LWDevCard>> initialCardsGrid, int rows, int columns) {
        this.grid.setGrid(initialCardsGrid, rows, columns);
    }

    /**
     * Getter method for the grid of development cards of this game board.
     */
    public LWCardsGrid getGrid() {
        return grid;
    }

    /**
     * Getter method for the faith track of this game board.
     */
    public ArrayList<LWCell> getFaithTrack() {
        return faithTrack;
    }

    /**
     * Getter method for the market of this game board.
     */
    public LWMarket getMarket() {
        return market;
    }

    /**
     * Method that updates the faith track with the provided array of LWCell.
     */
    public void buildFaithTrack(ArrayList<LWCell> faithTrack) {
        this.faithTrack = faithTrack;
        this.ui.onFaithTrackChanged();
    }

    /**
     * Method that updates the position of a player in the faith track.
     */
    public void updatePlayerPosition(String movedPlayerUsrn, int playerPositionInFT){
        if (playerPositionInFT > 0)
            this.faithTrack.get(playerPositionInFT - 1).removePlayer(movedPlayerUsrn);
        this.faithTrack.get(playerPositionInFT).addPlayer(movedPlayerUsrn);
        this.ui.onPlayerPositionFaithTrackChanged();
    }

    /**
     * This method is used to attach the attached to the object that implements this interface.
     */
    @Override
    public void attach(UI attached) {
        this.ui = attached;
        this.market.attach (ui);
        this.grid.attach (ui);
    }
}
