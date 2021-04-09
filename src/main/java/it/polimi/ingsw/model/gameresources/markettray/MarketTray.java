package it.polimi.ingsw.model.gameresources.markettray;

import it.polimi.ingsw.exception.InvalidMarketColumnException;
import it.polimi.ingsw.exception.InvalidMarketRowException;
import it.polimi.ingsw.exception.NegativeResourceAmountException;
import it.polimi.ingsw.model.config.ConfigLoaderWriter;

import java.io.FileNotFoundException;
import java.util.*;

public class MarketTray {
    private Vector<Vector<MarketMarble>> marblesMatrix;
    private MarketMarble marbleOnSlide;
    private Integer rows;
    private Integer columns;
    private HashMap<MarketMarble, Integer> howManyMarbles;

    public MarketTray () throws NegativeResourceAmountException, FileNotFoundException, CloneNotSupportedException {
        setColumns();
        setRows();
        setHowManyMarbles();
        setInitialMarblesConfiguration(allMarbles());
    }

    List<Resource> pickResourcesOnRow (int selectedRow) throws InvalidMarketRowException, NegativeResourceAmountException, CloneNotSupportedException {
        //TODO: check how is decoded the row when this method is called
        Vector<MarketMarble> rowToSwap = getRow(selectedRow);
        swap(rowToSwap);
        // it has been swapped the row in marblesGrid or a copy of the row?
        return getResources(rowToSwap);
    }

    //TODO: check how is decoded the row when this method is called
    List<Resource> pickResourcesOnColumn (int selectedColumn) throws InvalidMarketColumnException, NegativeResourceAmountException, CloneNotSupportedException {
        Vector<MarketMarble> columnToSwap = getColumn(selectedColumn);
        swap(columnToSwap);
        return getResources(columnToSwap);
    }

    private Vector<MarketMarble> getRow(int selectedRowIndex) throws InvalidMarketRowException {
        try {
            return marblesMatrix.elementAt(selectedRowIndex);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidMarketRowException();
        }
    }

    private Vector<MarketMarble> getColumn(int selectedColumnIndex) throws InvalidMarketColumnException {
        Vector<MarketMarble> column = new Vector<>();
        for(int i = 0; i < rows; i++) {
            try {
                column.add(getMarbleAt(i, selectedColumnIndex));
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new InvalidMarketColumnException();
            }
        }
        return column;
    }

    private MarketMarble getMarbleAt(int row, int column) {
        return marblesMatrix.get(row).get(column);
    }

    private List<Resource> getResources(Vector<MarketMarble> vectorOfMarbles) throws NegativeResourceAmountException, CloneNotSupportedException {
        ArrayList<Resource> resources = new ArrayList<>(0);
        for (int i = 0; i < vectorOfMarbles.size(); i++) {
            resources.add(vectorOfMarbles.get(i).getCorrespondentResource());
        }
        return resources;
    }

    private void setNewMarbleOnSlide(MarketMarble marbleOnSlide) {
        this.marbleOnSlide = marbleOnSlide;
    }

    private Vector<MarketMarble> swap(Vector<MarketMarble> vectorToSwap) {
        MarketMarble oldMarbleOnSlide = marbleOnSlide;
        setNewMarbleOnSlide(vectorToSwap.get(0));
        for (int i = 0; i < vectorToSwap.size() - 1; i++) {
            vectorToSwap.set(i, vectorToSwap.elementAt(i + 1));
        }
        vectorToSwap.set(vectorToSwap.size(), oldMarbleOnSlide);
        return vectorToSwap;
    }

    private void setRows () throws FileNotFoundException {
        this.rows = 0;
        ConfigLoaderWriter.getAttribute(this.rows, "rows", MarketTray.class) ;
    }

    private void setColumns() throws FileNotFoundException {
        this.columns = 0;
        ConfigLoaderWriter.getAttribute(this.columns, "columns", MarketTray.class);
    }

    private void setHowManyMarbles() throws FileNotFoundException {
        this.howManyMarbles = new HashMap<>();
        ConfigLoaderWriter.getAttribute(this.howManyMarbles, "howManyMarblesOfThisColour", MarketTray.class);
    }

    private Vector<MarketMarble> allMarbles () throws CloneNotSupportedException {
        Vector<MarketMarble> allMarbles = new Vector<>();
        for (MarketMarble marble : howManyMarbles.keySet()) {
            int numberOfMarblesOfThisType = howManyMarbles.get(marble);
            while (numberOfMarblesOfThisType > 0) {
                allMarbles.add(marble);
                numberOfMarblesOfThisType--;
            }
        }
        return allMarbles;
    }

    private void setInitialMarblesConfiguration(Vector<MarketMarble> marbles) {
        Collections.shuffle(marbles);
        marblesMatrix = new Vector<>();
        for (int i = 0; i < columns; i++) {
            Vector<MarketMarble> column = (Vector<MarketMarble>) marbles.subList(0, rows);
            marblesMatrix.add(column);
            int alreadyTakenMarbles = 0;
            while (alreadyTakenMarbles < rows) {
                marbles.remove(alreadyTakenMarbles);
                alreadyTakenMarbles++;
            }
        }
        setNewMarbleOnSlide(marbles.get(0));
    }
}
