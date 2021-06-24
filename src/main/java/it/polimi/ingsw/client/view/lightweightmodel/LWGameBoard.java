package it.polimi.ingsw.client.view.lightweightmodel;

import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.utils.Attachable;

import java.util.ArrayList;

public class LWGameBoard implements Attachable<UI> {

    private LWCardsGrid grid;
    private ArrayList<LWCell> faithTrack;
    private LWMarket market;
    private UI ui;

    public LWGameBoard() {
        this.market = new LWMarket (new ArrayList<> (), null);
        this.grid = new LWCardsGrid (new ArrayList<> (), 0, 0);
    }

    public void buildDevCardsGrid(ArrayList<ArrayList<LWDevCard>> initialCardsGrid, int rows, int columns) {
        this.grid.setGrid(initialCardsGrid, rows, columns);
    }

    public LWCardsGrid getGrid() {
        return grid;
    }

    public ArrayList<LWCell> getFaithTrack() {
        return faithTrack;
    }

    public LWMarket getMarket() {
        return market;
    }

    public void buildFaithTrack(ArrayList<LWCell> faithTrack) {
        this.faithTrack = faithTrack;
        this.ui.onFaithTrackChanged();
    }

    public void updatePlayerPosition(String movedPlayerUsrn, int playerPositionInFT){
        if (playerPositionInFT > 0)
            this.faithTrack.get(playerPositionInFT - 1).removePlayer(movedPlayerUsrn);
        this.faithTrack.get(playerPositionInFT).addPlayer(movedPlayerUsrn);
        this.ui.onPlayerPositionFaithTrackChanged();
    }

    /**
     * This method is used to attach the attached to the object that implements this interface
     *
     * @param attached
     */
    @Override
    public void attach(UI attached) {
        this.ui = attached;
        this.market.attach (ui);
        this.grid.attach (ui);
    }
}
