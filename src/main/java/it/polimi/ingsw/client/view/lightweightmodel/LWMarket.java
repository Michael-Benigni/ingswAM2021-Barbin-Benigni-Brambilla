package it.polimi.ingsw.client.view.lightweightmodel;

import it.polimi.ingsw.client.view.ui.cli.Colour;

import java.util.ArrayList;

public class LWMarket {
    private ArrayList<ArrayList<Colour>> marbles;
    private Colour marbleOnSlide;

    public LWMarket(ArrayList<ArrayList<Colour>> marbles, Colour marbleOnSlide) {
        this.marbles = marbles;
        this.marbleOnSlide = marbleOnSlide;
    }

    public void update(LWMarket market) {
        this.marbleOnSlide = market.marbleOnSlide;
        this.marbles = market.marbles;
    }

    public ArrayList<ArrayList<Colour>> getMarbles() {
        return marbles;
    }

    public Colour getMarbleOnSlide() {
        return marbleOnSlide;
    }
}
