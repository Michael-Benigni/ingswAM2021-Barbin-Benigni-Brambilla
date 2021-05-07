package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.exception.NoValidActionException;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gameresources.markettray.MarketTray;
import it.polimi.ingsw.server.model.gameresources.Resource;

import java.util.List;

class MarketAction implements MutualExclusiveAction {
    private final int numRowOrColumn;
    private final String rowOrColumn;

    protected MarketAction(int numRowOrColumn, String rowOrColumn) {
        super();
        this.numRowOrColumn = numRowOrColumn;
        this.rowOrColumn = rowOrColumn;
    }


    @Override
    public void perform(Game game, Player player) throws Exception {
        List<Resource> resources;
        MarketTray market = game.getGameBoard().getMarketTray();
        switch (rowOrColumn) {
            case "row": {
                resources = market.pickResourcesOnRow(numRowOrColumn);
                break;
            }
            case "column": {
                resources = market.pickResourcesOnColumn(numRowOrColumn);
                break;
            }
            default:
                throw new NoValidActionException();
        }
        for (Resource r : resources) {
            r.activate(player, game);
        }
    }
}
