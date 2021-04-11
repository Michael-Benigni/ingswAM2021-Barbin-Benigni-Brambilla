package it.polimi.ingsw.model.gameresources.markettray;

import it.polimi.ingsw.exception.InvalidMarketColumnException;
import it.polimi.ingsw.exception.InvalidMarketRowException;
import java.util.*;

public class MarketTray {
    private ArrayList<ArrayList<MarketMarble>> marblesMatrix;
    private MarketMarble marbleOnSlide;
    private Integer rows;
    private Integer columns;
    private HashMap<MarketMarble, Integer> howManyMarbles;

    public MarketTray (int columns, int rows, HashMap<MarketMarble, Integer> howManyMarbles) {
        this.columns = columns;
        this.rows = rows;
        this.howManyMarbles = howManyMarbles;
        setInitialMarblesConfiguration(allMarbles());
    }

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

    private void setInitialMarblesConfiguration(ArrayList<MarketMarble> marbles) {
        Collections.shuffle(marbles);
        marblesMatrix = new ArrayList<>();
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
    }

    private void setNewMarbleOnSlide(MarketMarble marbleOnSlide) {
        this.marbleOnSlide = marbleOnSlide;
    }

    List<Resource> pickResourcesOnRow (int selectedRow) throws InvalidMarketRowException, CloneNotSupportedException {
        //TODO: check how is decoded the row when this method is called
        ArrayList<MarketMarble> rowToSwap = getRow(selectedRow);
        swap(rowToSwap);
        // it has been swapped the row in marblesGrid or a copy of the row?
        return getResources(rowToSwap);
    }
    //TODO: check how is decoded the row when this method is called

    List<Resource> pickResourcesOnColumn (int selectedColumn) throws InvalidMarketColumnException, CloneNotSupportedException {
        ArrayList<MarketMarble> columnToSwap = getColumn(selectedColumn);
        swap(columnToSwap);
        return getResources(columnToSwap);
    }

    private ArrayList<MarketMarble> getRow(int selectedRowIndex) throws InvalidMarketRowException {
        try {
            return marblesMatrix.get(selectedRowIndex);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidMarketRowException();
        }
    }

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

    private MarketMarble getMarbleAt(int row, int column) {
        return marblesMatrix.get(row).get(column);
    }

    private List<Resource> getResources(ArrayList<MarketMarble> vectorOfMarbles) throws CloneNotSupportedException {
        ArrayList<Resource> resources = new ArrayList<>(0);
        for (int i = 0; i < vectorOfMarbles.size(); i++) {
            resources.add(vectorOfMarbles.get(i).getCorrespondentResource());
        }
        return resources;
    }

    private ArrayList<MarketMarble> swap(ArrayList<MarketMarble> vectorToSwap) {
        MarketMarble oldMarbleOnSlide = marbleOnSlide;
        setNewMarbleOnSlide(vectorToSwap.get(0));
        for (int i = 0; i < vectorToSwap.size() - 1; i++) {
            vectorToSwap.set(i, vectorToSwap.get(i + 1));
        }
        vectorToSwap.set(vectorToSwap.size(), oldMarbleOnSlide);
        return vectorToSwap;
    }
}
