package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.View;

public class CardsGridInitialUpdate {

    private  int[][] initialGrid;
    private int rows, columns;

    public CardsGridInitialUpdate(int[][] initialGrid, int rows, int columns) {
        this.initialGrid = initialGrid;
        this.rows = rows;
        this.columns = columns;
    }

    void update(View clientView){
        clientView.getModel().getBoard().updateInitialCardsGrid(initialGrid, rows, columns);
    }
}
