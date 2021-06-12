package it.polimi.ingsw.client.view.lightweightmodel;

import it.polimi.ingsw.client.view.exceptions.IllegalInputException;
import java.util.ArrayList;

public class LWCardsGrid {
    private ArrayList<ArrayList<LWDevCard>> cardsGrid;
    private int rows;
    private int columns;

    public LWCardsGrid(ArrayList<ArrayList<LWDevCard>> cardsGrid, int rows, int columns) {
        this.cardsGrid = cardsGrid;
        this.rows = rows;
        this.columns = columns;
    }

    public void update(LWDevCard cardToRemove, LWDevCard cardToShow) {
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                if(cardToRemove.equals(cardsGrid.get (i).get (j)))
                    cardsGrid.get (i).set (j, cardToShow);
            }
        }
    }

    public ArrayList<ArrayList<LWDevCard>> getCardsGrid() {
        return cardsGrid;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public LWDevCard getCard(int row, int column) throws IllegalInputException {
        try {
            return cardsGrid.get (row).get (column);
        } catch (IndexOutOfBoundsException | NullPointerException exception) {
            throw new IllegalInputException();
        }
    }
}
