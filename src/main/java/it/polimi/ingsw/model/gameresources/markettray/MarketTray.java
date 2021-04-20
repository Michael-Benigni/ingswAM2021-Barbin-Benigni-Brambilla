package it.polimi.ingsw.model.gameresources.markettray;

import it.polimi.ingsw.exception.InvalidMarketColumnException;
import it.polimi.ingsw.exception.InvalidMarketRowException;
import java.util.*;

/**
 * It's the class that represents the market present in the game board
 */
public class MarketTray {

    /**
     * it's the matricial representation of the Market: the marbles are placed in a matrix
     */
    private ArrayList<ArrayList<MarketMarble>> marblesMatrix;

    /**
     * it's the marble outside the matrix, used to change the matrix disposition
     */
    private MarketMarble marbleOnSlide;

    /**
     * number of rows of the matrix
     */
    private Integer rows;

    /**
     * number of columns of the matrix
     */
    private Integer columns;

    /**
     * HashMap that relates a marble to a number: the number indicates the how many marbles of the associated type
     * will be in the market
     */
    private HashMap<MarketMarble, Integer> howManyMarbles;


    /**
     * Constructor. Pre-condition: columns * rows + 1 == {sum of all the integers in the values of the HashMap}
     * @param columns number of columns of the matrix
     * @param rows number of rows of the matrix
     * @param howManyMarbles HashMap that indicates how many marbles of a certain colour there will be in the Market
     */
    public MarketTray (int columns, int rows, HashMap<MarketMarble, Integer> howManyMarbles) {
        this.columns = columns;
        this.rows = rows;
        this.howManyMarbles = howManyMarbles;
        setInitialShuffleDisposition();
    }


    /**
     * This method duplicates all the marbles in the needed number, according to the attribute howManyMarbles
     * @return -> all the marbles
     */
    private ArrayList<MarketMarble> allMarbles () {
        ArrayList<MarketMarble> allMarbles = new ArrayList<>();
        for (MarketMarble marble : howManyMarbles.keySet()) {
            Integer numberOfMarblesOfThisType = howManyMarbles.get(marble);
            while (numberOfMarblesOfThisType > 0) {
                allMarbles.add(marble);
                numberOfMarblesOfThisType--;
            }
        }
        return allMarbles;
    }


    /**
     * This method initializes the matrix in a random way
     * @return this whole MarketTray object
     */
    private MarketTray setInitialShuffleDisposition() {
        ArrayList<MarketMarble> marbles = allMarbles();
        Collections.shuffle(marbles);
        marblesMatrix = new ArrayList<>(0);
        ArrayList<MarketMarble> column = null;
        int i, j;
        for (i = 0; i < columns; i++) {
            column = new ArrayList<>();
            for (j = 0; j < rows; j++) {
                column.add(j, marbles.get(j));
            }
            j = 0;
            marblesMatrix.add(column);
            int alreadyTakenMarbles = 0;
            while (alreadyTakenMarbles < rows) {
                marbles.remove(0);
                alreadyTakenMarbles++;
            }
        }
        setNewMarbleOnSlide(marbles.get(0));
        return this;
    }


    /**
     * set the attribute marbleOnSlide with the input parameter
     * @param marbleOnSlide the new marbleOnSlide
     */
    private void setNewMarbleOnSlide(MarketMarble marbleOnSlide) {
        this.marbleOnSlide = marbleOnSlide;
    }


    /**
     * Invoked when a player wants to pick resources from a row of the market
     * @param selectedRow row from which will be taken the resources objects
     * @return The List of all the Resources associated to the marbles of the row number "selectedRow"
     * @throws InvalidMarketRowException if selectedRow >= rows (look at the attributes)
     */
    public List<Resource> pickResourcesOnRow (int selectedRow) throws InvalidMarketRowException {
        //TODO: check how is decoded the row when this method is called
        ArrayList<MarketMarble> rowToSwap = getRow(selectedRow);
        swap(rowToSwap);
        // it has been swapped the row in marblesGrid or a copy of the row?
        return getResources(rowToSwap);
    }
    //TODO: check how is decoded the row when this method is called


    /**
     * Invoked when a player wants to pick resources from a column of the market
     * @param selectedColumn -> column from which the player takes the resources
     * @return The List of all the Resources associated to the marbles of the column number "selectedColumn"
     * @throws InvalidMarketColumnException if selectedColumn >= columns (look at the attributes)
     */
    public ArrayList<Resource> pickResourcesOnColumn (int selectedColumn) throws InvalidMarketColumnException {
        ArrayList<MarketMarble> columnToSwap = getColumn(selectedColumn);
        swap(columnToSwap);
        return getResources(columnToSwap);
    }


    /**
     * @param selectedRowIndex row from which will be taken the resources objects
     * @return the selected row as ArrayList of MarketMarbles
     * @throws InvalidMarketRowException if selectedRow >= rows (look at the attributes)
     */
    private ArrayList<MarketMarble> getRow(int selectedRowIndex) throws InvalidMarketRowException {
        try {
            return marblesMatrix.get(selectedRowIndex);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidMarketRowException();
        }
    }


    /**
     *
     * @param selectedColumnIndex column from which the player takes the resources
     * @return the selected column as ArrayList of MarketMarbles
     * @throws InvalidMarketColumnException if selectedColumn >= columns (look at the attributes)
     */
    private ArrayList<MarketMarble> getColumn(int selectedColumnIndex) throws InvalidMarketColumnException {
        ArrayList<MarketMarble> column = new ArrayList<>();
        for(int i = 0; i < rows; i++) {
            try {
                column.add(getMarbleAt(i, selectedColumnIndex));
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new InvalidMarketColumnException();
            }
        }
        return column;
    }


    /**
     *
     * @param row
     * @param column
     * @return
     */
    private MarketMarble getMarbleAt(int row, int column) {
        return marblesMatrix.get(row).get(column);
    }


    /**
     * Invoked to obtain Resources of a correspondent vector of marbles
     * @param vectorOfMarbles vector from which are taken the Resources
     * @return the correspondent Resources of the vectoOfMarbles
     */
    private ArrayList<Resource> getResources(ArrayList<MarketMarble> vectorOfMarbles) {
        return vectorOfMarbles
                .stream()
                .parallel()
                .map((marble)-> marble.getCorrespondentResource())
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }


    /**
     * Updates the matrix of marbles according to the rules of the game: the marble on slide is pushed into a row or a
     * column and all the marbles are swapped. The last marble of the target marbles' vector will become the new marble
     * on slide
     * @param vectorToSwap the vector of marbles that will be updated
     * @return the new updated vector of marbles as ArrayList of MarketMarbles
     */
    private ArrayList<MarketMarble> swap(ArrayList<MarketMarble> vectorToSwap) {
        MarketMarble oldMarbleOnSlide = marbleOnSlide;
        setNewMarbleOnSlide(vectorToSwap.get(0));
        for (int i = 0; i < vectorToSwap.size() - 1; i++) {
            vectorToSwap.set(i, vectorToSwap.get(i + 1));
        }
        vectorToSwap.set(vectorToSwap.size() - 1, oldMarbleOnSlide);
        return vectorToSwap;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarketTray that = (MarketTray) o;
        return Objects.equals(marblesMatrix, that.marblesMatrix)
                && Objects.equals(marbleOnSlide, that.marbleOnSlide)
                && Objects.equals(rows, that.rows)
                && Objects.equals(columns, that.columns)
                && Objects.equals(howManyMarbles, that.howManyMarbles);
    }


    @Override
    public int hashCode() {
        return Objects.hash(marblesMatrix, marbleOnSlide, rows, columns, howManyMarbles);
    }
}
