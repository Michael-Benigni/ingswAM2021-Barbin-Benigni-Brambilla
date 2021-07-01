package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.client.view.lightweightmodel.LWDevCard;
import it.polimi.ingsw.client.view.ui.cli.Colour;

import java.util.ArrayList;

public class CardsGridInitialUpdate implements ViewUpdate {

    private final ArrayList<ArrayList<Integer>> initialGrid;
    private final ArrayList<ArrayList<String>> descriptions;
    private final ArrayList<ArrayList<Colour>> colours;
    private final ArrayList<ArrayList<Integer>> levels;
    private final int rows;
    private final int columns;

    public CardsGridInitialUpdate(ArrayList<ArrayList<Integer>> initialGrid, ArrayList<ArrayList<String>> descriptions,
                                  ArrayList<ArrayList<Colour>> colours, ArrayList<ArrayList<Integer>> levels,
                                  int rows, int columns) {
        this.initialGrid = initialGrid;
        this.descriptions = descriptions;
        this.colours = colours;
        this.levels = levels;
        this.rows = rows;
        this.columns = columns;
    }

    @Override
    public void update(Controller clientController){
        clientController.getModel().getBoard().buildDevCardsGrid (convertFromIDsToLWDevCards(), rows, columns);
    }

    /**
     *
     * @return
     */
    private ArrayList<ArrayList<LWDevCard>> convertFromIDsToLWDevCards(){
        ArrayList<ArrayList<LWDevCard>> lwGrid = new ArrayList<> ();
        for(int i = 0; i < rows; i++){
            ArrayList<LWDevCard> row = new ArrayList<> ();
            lwGrid.add (row);
            for(int j = 0; j < columns; j++){
                LWDevCard newLWDevCard = new LWDevCard(initialGrid.get (i).get (j),
                        descriptions.get (i).get (j), colours.get (i).get (j), levels.get (i).get (j), -1);
                lwGrid.get (i).add (j, newLWDevCard);
            }
        }
        return lwGrid;
    }
}
