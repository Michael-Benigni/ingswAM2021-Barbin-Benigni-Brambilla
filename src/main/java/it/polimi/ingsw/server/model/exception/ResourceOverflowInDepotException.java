package it.polimi.ingsw.server.model.exception;


import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;

/**
 * Exception thrown if you try to insert a resource into a depot, but the amount exceeds the capacity.
 */
public class ResourceOverflowInDepotException extends Exception{
    private StorableResource resource;

    public ResourceOverflowInDepotException(StorableResource resource) {
        super("Amount of the resource exceeds the capacity of the depot.");
        this.resource = resource;
    }

    public StorableResource getResource() {
        return this.resource;
    }
}
