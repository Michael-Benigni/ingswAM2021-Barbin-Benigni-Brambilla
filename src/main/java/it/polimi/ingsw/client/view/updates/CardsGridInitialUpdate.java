package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.lightweightmodel.LWDevCard;
import it.polimi.ingsw.client.view.ui.cli.Colour;

public class CardsGridInitialUpdate implements ViewUpdate{

    private final int[][] initialGrid;
    private final String[][] descriptions;
    private final Colour[][] colours;
    private final int[][] levels;
    private final int rows;
    private final int columns;

    public CardsGridInitialUpdate(int[][] initialGrid, String[][] descriptions, Colour[][] colours, int[][] levels, int rows, int columns) {
        this.initialGrid = initialGrid;
        this.descriptions = descriptions;
        this.colours = colours;
        this.levels = levels;
        this.rows = rows;
        this.columns = columns;
    }

    @Override
    public void update(View clientView){
        clientView.getModel().getBoard().updateInitialCardsGrid(convertFromIDsToLWDevCards(), rows, columns);
    }

    /**
     *
     * @return
     */
    private LWDevCard[][] convertFromIDsToLWDevCards(){
        LWDevCard[][] lwGrid = new LWDevCard[rows][columns];
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                LWDevCard newLWDevCard = new LWDevCard(initialGrid[i][j], descriptions[i][j]/*, colours[i][j], levels[i][j]*/);
                lwGrid[i][j] = newLWDevCard;
            }
        }
        return lwGrid;
    }
}
