package it.polimi.ingsw.client.view.lightweightmodel;

import it.polimi.ingsw.server.model.gameresources.markettray.MarbleColour;
import java.util.ArrayList;

public class LWeightBoard {

    /**
     *
     */
    private class LWMarket {
        private ArrayList<ArrayList<MarbleColour>> marbles;
        private MarbleColour marbleOnSlide;
    }

    private ArrayList<ArrayList<LWDevCard>> cardsGrid;
    private ArrayList<LWCell> faithTrack;
    private LWMarket market;

    public void updateCardsGrid(ArrayList<ArrayList<LWDevCard>> cardsGrid) {
        this.cardsGrid = cardsGrid;
    }

    public void updateFaithTrack(ArrayList<LWCell> faithTrack) {
        this.faithTrack = faithTrack;
    }

    public void updateMarket(LWMarket market) {
        this.market = market;
    }
}
