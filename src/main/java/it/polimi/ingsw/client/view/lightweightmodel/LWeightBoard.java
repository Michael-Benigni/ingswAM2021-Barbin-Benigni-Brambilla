package it.polimi.ingsw.client.view.lightweightmodel;

import it.polimi.ingsw.client.view.ui.cli.Colour;
import it.polimi.ingsw.server.model.gameresources.markettray.MarbleColour;
import java.util.ArrayList;

public class LWeightBoard {

    private LWCardsGrid grid;
    private ArrayList<LWCell> faithTrack;
    private LWMarket market;

    public void updateInitialCardsGrid(LWDevCard[][] initialCardsGrid, int rows, int columns) {
        this.grid = new LWCardsGrid(initialCardsGrid, rows, columns);
    }

    public void updateCardsGrid(LWDevCard cardToRemove, LWDevCard cardToShow) {
        for(int i = 0; i < grid.rows; i++){
            for(int j = 0; j < grid.columns; j++){
                if(cardToRemove.equals(grid.cardsGrid[i][j]))
                    grid.cardsGrid[i][j] = cardToShow;
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
