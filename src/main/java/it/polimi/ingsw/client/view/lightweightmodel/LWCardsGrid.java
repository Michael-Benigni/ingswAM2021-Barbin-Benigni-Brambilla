package it.polimi.ingsw.client.view.lightweightmodel;

public class LWCardsGrid {
    LWDevCard[][] cardsGrid;
    int rows;
    int columns;

    public LWCardsGrid(LWDevCard[][] cardsGrid, int rows, int columns) {
        this.cardsGrid = cardsGrid;
        this.rows = rows;
        this.columns = columns;
    }
}
