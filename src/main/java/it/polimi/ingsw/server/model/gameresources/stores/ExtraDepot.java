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
    private ResourceType type;

    /**
     * this is the constructor method of this class
     * @param capacity -> the maximum number of resources it can contain
     * @param resourceType -> indicates the type of resourceType that the extra depot can contain
     */
    public ExtraDepot(int capacity, ResourceType resourceType) throws NegativeResourceAmountException, NotEqualResourceTypeException, ResourceOverflowInDepotException {
        super(capacity);
        this.type = resourceType;
        super.storeResourceInDepot(new StorableResource(resourceType, 0));
    }

    /**
     * Method that puts a resource in this depot. This action is performed if and only if this depot is empty or
     * if it contains a resource of the same type as the one provided in input and, in both cases, the amount of the
     * contained resource must not exceed "capacity". If it happens, this method fills this depot, calculates the
     * difference and throws an exception.
     *
     * @param resourceToStore resource to be added to this depot.
     */
    @Override
    void storeResourceInDepot(StorableResource resourceToStore) throws NotEqualResourceTypeException, ResourceOverflowInDepotException {
        StorableResource resource = null;
        try {
            resource = new StorableResource (type, 0);
        } catch (NegativeResourceAmountException e) {
            e.printStackTrace ();
        }
        try {
            if (! getStoredResource ().ifSameResourceType (resource))
                throw new NotEqualResourceTypeException (resource, resourceToStore);
            else
                super.storeResourceInDepot (resourceToStore);
        } catch (EmptyDepotException e) {
            e.printStackTrace ();
        }
    }

    @Override
    void removeResourceFromDepot(StorableResource resourceToRemove) throws NegativeResourceAmountException, NotEqualResourceTypeException, EmptyDepotException {
        super.removeResourceFromDepot(resourceToRemove);
        try {
            super.getStoredResource();
        } catch(EmptyDepotException exception) {
            try {
                super.storeResourceInDepot(new StorableResource(this.type, 0));
            } catch (ResourceOverflowInDepotException ignored) {

            }
        }
    }

    @Override
    StorableResource getStoredResource() throws EmptyDepotException {
        StorableResource resource = super.getStoredResource();
        if(resource.getAmount() == 0)
            throw new EmptyDepotException ();
        return resource;
    }
}
