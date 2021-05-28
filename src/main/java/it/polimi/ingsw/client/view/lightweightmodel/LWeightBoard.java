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

    private LWCardsGrid grid;
    private ArrayList<LWCell> faithTrack;
    private LWMarket market;

    public void updateInitialCardsGrid(int[][] initialCardsGrid, int rows, int columns) {
        grid.rows = rows;
        grid.columns = columns;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                LWDevCard newLWDevCard = new LWDevCard(initialCardsGrid[i][j]);
                grid.cardsGrid[i][j] = newLWDevCard;
            }
        }
    }

    public void updateCardsGrid(int cardToRemove, int cardToShow) {
        if(cardToShow > 0){
            LWDevCard showCard = new LWDevCard(cardToShow);
            for(int i = 0; i < grid.rows; i++){
                for(int j = 0; j < grid.columns; j++){
                    if(cardToRemove == grid.cardsGrid[i][j].id)
                        grid.cardsGrid[i][j] = showCard;
                }
            }
        }
    }

    public void updateFaithTrack(ArrayList<LWCell> faithTrack) {
        this.faithTrack = faithTrack;
    }

    public void updateMarket(LWMarket market) {
        this.market = market;
    }
}
