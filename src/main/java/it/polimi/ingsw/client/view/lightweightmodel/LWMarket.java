package it.polimi.ingsw.client.view.lightweightmodel;

import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.client.view.ui.cli.Colour;
import it.polimi.ingsw.utils.Attachable;

import java.util.ArrayList;

public class LWMarket implements Attachable<UI> {
    private ArrayList<ArrayList<Colour>> marbles;
    private Colour marbleOnSlide;
    private UI ui;
    private int rows;
    private int columns;

    public LWMarket(ArrayList<ArrayList<Colour>> marbles, Colour marbleOnSlide) {
        this.marbles = marbles;
        this.marbleOnSlide = marbleOnSlide;
    }

    public void update(LWMarket market) {
        this.marbleOnSlide = market.marbleOnSlide;
        this.marbles = market.marbles;
        this.rows = marbles.size ();
        this.columns = marbles.get (0).size ();
        ui.onMarketChanged();
    }

    public ArrayList<ArrayList<Colour>> getMarbles() {
        return marbles;
    }

    public Colour getMarbleOnSlide() {
        return marbleOnSlide;
    }

    /**
     * This method is used to attach the attached to the object that implements this interface
     *
     * @param attached
     */
    @Override
    public void attach(UI attached) {
        this.ui = attached;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}
