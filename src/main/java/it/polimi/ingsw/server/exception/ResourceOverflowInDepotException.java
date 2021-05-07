package it.polimi.ingsw.server.exception;


import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;

/**
 * Exception thrown if you try to insert a resource into a depot, but the amount exceeds the capacity.
 */
public class ResourceOverflowInDepotException extends Exception{
    private StorableResource resource;

    public ResourceOverflowInDepotException(StorableResource resource) {
        this.resource = resource;
    }

    public StorableResource getResource() {
        return this.resource;
    }
}
