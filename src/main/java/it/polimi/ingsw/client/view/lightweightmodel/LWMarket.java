package it.polimi.ingsw.client.view.lightweightmodel;

import it.polimi.ingsw.client.view.ui.cli.Colour;

public class LWMarket {
    private Colour[][] marbles;
    private Colour marbleOnSlide;

    public LWMarket(Colour[][] marbles, Colour marbleOnSlide) {
        this.marbles = marbles;
        this.marbleOnSlide = marbleOnSlide;
    }
}
