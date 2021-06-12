package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.lightweightmodel.LWMarket;
import it.polimi.ingsw.client.view.ui.cli.Colour;

import java.util.ArrayList;

public class MarketUP implements ViewUpdate {
    private final ArrayList<ArrayList<Colour>> market;
    private final Colour marbleOnSlide;

    public MarketUP(ArrayList<ArrayList<Colour>> market, Colour marbleOnSlide) {
        this.market = market;
        this.marbleOnSlide = marbleOnSlide;
    }

    @Override
    public void update(View view) {
        LWMarket lwMarket = new LWMarket (market, marbleOnSlide);
        view.getModel ().getBoard ().getMarket().update (lwMarket);
    }
}
