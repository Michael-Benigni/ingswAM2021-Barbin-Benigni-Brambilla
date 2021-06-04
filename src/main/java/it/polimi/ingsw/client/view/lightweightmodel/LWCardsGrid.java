package it.polimi.ingsw.client.view.lightweightmodel;

public class LWCardsGrid {
    private LWDevCard[][] cardsGrid;
    private int rows;
    private int columns;

    public LWCardsGrid(LWDevCard[][] cardsGrid, int rows, int columns) {
        this.cardsGrid = cardsGrid;
        this.rows = rows;
        this.columns = columns;
    }

    public void update(LWDevCard cardToRemove, LWDevCard cardToShow) {
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                if(cardToRemove.equals(cardsGrid[i][j]))
                    cardsGrid[i][j] = cardToShow;
            }
        }
    }

    public LWDevCard[][] getCardsGrid() {
        return cardsGrid;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}
