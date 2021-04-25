package it.polimi.ingsw.model.gamelogic.actions;

import it.polimi.ingsw.exception.NoValidActionException;
import it.polimi.ingsw.model.gamelogic.Action;
import it.polimi.ingsw.model.gamelogic.Game;
import it.polimi.ingsw.model.gameresources.markettray.MarketTray;
import it.polimi.ingsw.model.gameresources.Resource;
import java.util.List;

class MarketAction extends Action {
    private final int numRowOrColumn;
    private final String rowOrColumn;

    protected MarketAction(int numRowOrColumn, String rowOrColumn) {
        super();
        this.numRowOrColumn = numRowOrColumn;
        this.rowOrColumn = rowOrColumn;
    }

    @Override
    protected ActionType getType() {
        return ActionType.MUTUAL_EX;
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
