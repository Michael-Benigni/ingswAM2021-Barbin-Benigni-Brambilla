package it.polimi.ingsw.model.gameresources.stores;

import it.polimi.ingsw.exception.*;

import java.util.Objects;

/**
 * Class which represents a single depot of the warehouse depots.
 * of storable Resources.
 */
class Depot {

    private StorableResource storedResource;
    private final int capacity;

    private int getCapacity() {
        return capacity;
    }

    /**
     * Constructor method of "Depot" class. This method create an empty Arraylist of storable resources and reads
     * the capacity from a json file.
     */
    Depot(int capacity) {
        storedResource = null;
        this.capacity = capacity;
    }


    boolean alreadyContained (StorableResource resource) {
        return getStoredResource().ifSameResourceType(resource);
    }


    /**
     * Method that puts a resource in this depot. This action is performed if and only if this depot is empty or
     * if it contains a resource of the same type as the one provided in input and, in both cases, the amount of the
     * contained resource must not exceed "capacity".
     * @param resourceToStore -> resource to be added to this depot
     * @throws Exception thrown if: amount that exceeds the capacity or different resource type of the provided resource
     * and the contained one.
     */
    void storeResourceInDepot(StorableResource resourceToStore) throws NotEqualResourceTypeException, ResourceOverflowInDepotException {
        StorableResource newResource;
        try {
            newResource = this.getStoredResource().increaseAmount(resourceToStore);
        } catch (NullPointerException e) {
            newResource = resourceToStore;
        }
        if(newResource.amountLessEqualThan(capacity))
            storedResource = newResource;
        else {
            throw new ResourceOverflowInDepotException();
        }
    }


    void removeResourceFromDepot(StorableResource resourceToStore) throws NotEqualResourceTypeException, NegativeResourceAmountException {
        StorableResource newResource = null;
        try {
            newResource = this.getStoredResource().decreaseAmount(resourceToStore);
        } catch (NullResourceAmountException e) {
            storedResource = null;
        }
        storedResource = newResource;
    }


    StorableResource getStoredResource() {
        return storedResource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Depot depot = (Depot) o;
        return capacity == depot.capacity && Objects.equals(storedResource, depot.storedResource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storedResource, capacity);
    }
}