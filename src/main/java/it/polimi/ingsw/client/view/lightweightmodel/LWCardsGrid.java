package it.polimi.ingsw.client.view.lightweightmodel;

import it.polimi.ingsw.client.view.exceptions.IllegalInputException;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.utils.Attachable;

import java.util.ArrayList;

/**
 * Class that represents the view of the development cards grid.
 */
public class LWCardsGrid implements Attachable<UI> {
    private ArrayList<ArrayList<LWDevCard>> cardsGrid;
    private int rows;
    private int columns;
    private UI ui;

    /**
     * Constructor method for this class.
     */
    public LWCardsGrid(ArrayList<ArrayList<LWDevCard>> cardsGrid, int rows, int columns) {
        this.cardsGrid = cardsGrid;
        this.rows = rows;
        this.columns = columns;
    }

    /**
     * Method that update this grid.
     * @param cardToRemove card to be removed from the grid.
     * @param cardToShow card it replaces "cardToRemove".
     */
    public void update(LWDevCard cardToRemove, LWDevCard cardToShow) {
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                if(cardToRemove.equals(cardsGrid.get (i).get (j)))
                    cardsGrid.get (i).set (j, cardToShow);
            }
        }
        this.ui.onCardBoughtFromGrid();
    }

    /**
     * Getter method for the list of cards in this grid.
     */
    public ArrayList<ArrayList<LWDevCard>> getCardsGrid() {
        return cardsGrid;
    }

    /**
     * Getter method for the number of rows of this grid.
     */
    public int getRows() {
        return rows;
    }

    /**
     * Getter method for the number of columns of this grid.
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Getter method for a specific card of the grid.
     * @param row indicates the row number of the card.
     * @param column indicates the column number of the card.
     * @throws IllegalInputException thrown if the provided index are wrong, for example if one of them is out of bounds.
     */
    public LWDevCard getCard(int row, int column) throws IllegalInputException {
        try {
            return cardsGrid.get (row).get (column);
        } catch (IndexOutOfBoundsException | NullPointerException exception) {
            throw new IllegalInputException();
        }
    }

    /**
     * This method is used to attach the attached to the object that implements this interface.
     */
    @Override
    public void attach(UI attached) {
        this.ui = attached;
    }

    /**
     * Method used to set the initial configuration of this grid.
     */
    public void setGrid(ArrayList<ArrayList<LWDevCard>> initialCardsGrid, int rows, int columns) {
        this.columns = columns;
        this.rows = rows;
        this.cardsGrid = initialCardsGrid;
        this.ui.onCardsGridBuilt();
    }
}
