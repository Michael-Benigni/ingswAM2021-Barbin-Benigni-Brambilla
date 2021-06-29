package it.polimi.ingsw.client.view.lightweightmodel;

import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.client.view.ui.cli.Colour;
import it.polimi.ingsw.utils.Attachable;

import java.util.ArrayList;

/**
 * Class that represents the view of the market in the game board.
 */
public class LWMarket implements Attachable<UI> {

    /**
     * List of marbles that is represented by an array of colours.
     */
    private ArrayList<ArrayList<Colour>> marbles;

    /**
     * Marble out of the grid of the market, represents by a colour.
     */
    private Colour marbleOnSlide;

    /**
     * User interface.
     */
    private UI ui;

    /**
     * Number of rows.
     */
    private int rows;

    /**
     * Number of columns.
     */
    private int columns;

    /**
     * Constructor method of this class. It initializes the array of marbles and the marble on slide.
     */
    public LWMarket(ArrayList<ArrayList<Colour>> marbles, Colour marbleOnSlide) {
        this.marbles = marbles;
        this.marbleOnSlide = marbleOnSlide;
    }

    /**
     * Method that update this market with another market provided in input.
     */
    public void update(LWMarket market) {
        this.marbleOnSlide = market.marbleOnSlide;
        this.marbles = market.marbles;
        this.rows = marbles.size ();
        this.columns = marbles.get (0).size ();
        ui.onMarketChanged();
    }

    /**
     * Getter method for the array of marbles of this market.
     */
    public ArrayList<ArrayList<Colour>> getMarbles() {
        return marbles;
    }

    /**
     * Getter method for the marble on slide of this market.
     */
    public Colour getMarbleOnSlide() {
        return marbleOnSlide;
    }

    /**
     * This method is used to attach the attached to the object that implements this interface.
     */
    @Override
    public void attach(UI attached) {
        this.ui = attached;
    }

    /**
     * Getter method for the number of rows of this market.
     */
    public int getRows() {
        return rows;
    }

    /**
     * Getter method for the number of columns of this market.
     */
    public int getColumns() {
        return columns;
    }
}
