package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gamelogic.Turn;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.server.model.gameresources.stores.TemporaryContainer;
import it.polimi.ingsw.server.model.gameresources.stores.WarehouseDepots;

class TempContainerAction implements FirstTurnAction {
    private final PayAction.StoreOrRemove storeOrRemove;
    private final StorableResource resource;
    private final int depotIdx;

    TempContainerAction(PayAction.StoreOrRemove storeOrRemove, StorableResource resource, int depotIdx) {
        super();
        this.storeOrRemove = storeOrRemove;
        this.resource = resource;
        this.depotIdx = depotIdx;
    }


    @Override
    public void perform(Game game, Player player) throws WrongDepotIndexException, NegativeResourceAmountException,
            EmptyDepotException, SameResourceTypeInDifferentDepotsException, NotEqualResourceTypeException,
            ResourceOverflowInDepotException, NotContainedResourceException, TempContainerForProductionException {
        TemporaryContainer tempCont = player.getPersonalBoard().getTempContainer();
        if (!tempCont.isContainerForProduction()) {
            switch (storeOrRemove) {
                case REMOVE: {
                    tempCont.remove (this.resource);
                    try{
                        new WarehouseAction (PayAction.StoreOrRemove.STORE, resource, depotIdx).perform (game, player);
                    } catch (WrongDepotIndexException | NotEqualResourceTypeException | SameResourceTypeInDifferentDepotsException e){
                        player.getPersonalBoard().getTempContainer().store(this.resource);
                        throw e;
                    } catch (ResourceOverflowInDepotException e) {
                        player.getPersonalBoard().getTempContainer().store(e.getResource ());
                        throw new ResourceOverflowInDepotException (e.getResource ());
                    }
                    break;
                }
                case STORE: {
                    new WarehouseAction (PayAction.StoreOrRemove.REMOVE, resource, depotIdx).perform (game, player);
                    tempCont.store (this.resource);
                    break;
                }
                default:
            }
        } else
            throw new TempContainerForProductionException();
    }

    @Override
    public boolean isValid(Turn turn) {
        return true;
    }
}
