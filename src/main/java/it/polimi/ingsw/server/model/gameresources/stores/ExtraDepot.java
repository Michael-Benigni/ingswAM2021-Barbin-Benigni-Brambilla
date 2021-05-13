package it.polimi.ingsw.server.model.gameresources.stores;

import it.polimi.ingsw.server.model.exception.EmptyDepotException;
import it.polimi.ingsw.server.model.exception.NegativeResourceAmountException;
import it.polimi.ingsw.server.model.exception.NotEqualResourceTypeException;
import it.polimi.ingsw.server.model.exception.ResourceOverflowInDepotException;

/**
 * this class represents the
 * extra depot provided by
 * the power of the leader card
 */
public class ExtraDepot extends Depot{
    private ResourceType resource;

    /**
     * this is the constructor method of this class
     * @param capacity -> the maximum number of resources it can contain
     * @param resourceType -> indicates the type of resourceType that the extra depot can contain
     */
    public ExtraDepot(int capacity, ResourceType resourceType) throws NegativeResourceAmountException, NotEqualResourceTypeException, ResourceOverflowInDepotException {
        super(capacity);
        this.resource = resourceType;
        super.storeResourceInDepot(new StorableResource(resourceType, 0));
    }

    @Override
    void removeResourceFromDepot(StorableResource resourceToRemove) throws NegativeResourceAmountException, NotEqualResourceTypeException, EmptyDepotException {
        super.removeResourceFromDepot(resourceToRemove);
        try {
            super.getStoredResource();
        } catch(EmptyDepotException exception) {
            try {
                super.storeResourceInDepot(new StorableResource(this.resource, 0));
            } catch (ResourceOverflowInDepotException e) {

            }
        }
    }
}
