package it.polimi.ingsw.client.view.lightweightmodel;

import java.util.ArrayList;

public class LWGameBoard {

    private LWCardsGrid grid;
    private ArrayList<LWCell> faithTrack;
    private LWMarket market;

    public LWGameBoard() {
        this.market = new LWMarket (null, null);
    }

    public void buildDevCardsGrid(ArrayList<ArrayList<LWDevCard>> initialCardsGrid, int rows, int columns) {
        this.grid = new LWCardsGrid(initialCardsGrid, rows, columns);
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

    public void updateFaithTrack(ArrayList<LWCell> faithTrack) {
        this.faithTrack = faithTrack;
    }

    public void updatePlayerPosition(String movedPlayerUsrn, int playerPositionInFT){
        this.faithTrack.get(playerPositionInFT - 1).removePlayer(movedPlayerUsrn);
        this.faithTrack.get(playerPositionInFT).addPlayer(movedPlayerUsrn);
    }
}
