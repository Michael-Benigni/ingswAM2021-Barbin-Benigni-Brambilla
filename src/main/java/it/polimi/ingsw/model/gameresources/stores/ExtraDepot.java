package it.polimi.ingsw.model.gameresources.stores;

/**
 * this class represents the
 * extra depot provided by
 * the power of the leader card
 */
public class ExtraDepot extends Depot{
    private StorableResource resource;

    /**
     * this is the constructor method of this class
     * @param capacity -> the maximum number of resources it can contain
     * @param resource -> indicates the type of resource that the extra depot can contain
     */
    public ExtraDepot(int capacity, StorableResource resource) {
        super(capacity);
        this.resource = resource;
    }
}
