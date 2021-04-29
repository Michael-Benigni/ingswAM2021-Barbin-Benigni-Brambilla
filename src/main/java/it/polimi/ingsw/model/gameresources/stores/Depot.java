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


    /**
     * Constructor method of "Depot" class. This method create an empty Arraylist of storable resources and reads
     * the capacity from a json file.
     */
    Depot(int capacity) {
        storedResource = null;
        this.capacity = capacity;
    }


    /**
     * Method that returns if the type of the provided resource corresponds to the type of the one contained.
     * @param resource -> resource to be compared to the one stored in this depot.
     * @return -> boolean: true if the two resource have the same type, false otherwise.
     */
    boolean alreadyContained (StorableResource resource) {
        return getStoredResource().ifSameResourceType(resource);
    }


    /**
     * Method that puts a resource in this depot. This action is performed if and only if this depot is empty or
     * if it contains a resource of the same type as the one provided in input and, in both cases, the amount of the
     * contained resource must not exceed "capacity".
     * @param resourceToStore resource to be added to this depot
     * @throws Exception thrown if: amount that exceeds the capacity or different resource type of the provided resource
     * and the contained one.
     */
    void storeResourceInDepot(StorableResource resourceToStore) throws NotEqualResourceTypeException, NegativeResourceAmountException, ResourceOverflowInDepotException {
        StorableResource newResource = this.getStoredResource().increaseAmount(resourceToStore);
        if(newResource.amountLessEqualThan(capacity)) {
            storedResource = newResource;
        }
        else {
            storedResource.setAmount(capacity);
            try {
                throw new ResourceOverflowInDepotException(newResource.decreaseAmount(storedResource));
            } catch (NullResourceAmountException e) {

            }

        }
    }


    /**
     * Method that decrease the amount of the contained resource.
     * @param resourceToRemove -> Storable resource whose amount must be subtracted from the already contained resource.
     * @throws NotEqualResourceTypeException -> can be thrown by "decreaseAmount" method of "StorableResource" class.
     * @throws NegativeResourceAmountException -> can be thrown by "decreaseAmount" method of "StorableResource" class.
     */
    void removeResourceFromDepot(StorableResource resourceToRemove) throws NotEqualResourceTypeException, NegativeResourceAmountException {
        StorableResource newResource = null;
        try {
            newResource = this.getStoredResource().decreaseAmount(resourceToRemove);
        } catch (NullResourceAmountException e) {

        }
        storedResource = newResource;
    }


    /**
     * Getter method for "storedResource" attribute of this class.
     * @return -> the storable resource contained in this depot.
     */
    StorableResource getStoredResource() {
        return storedResource;
    }


    /**
     * Method that returns if the two object are equals.
     * @param o -> object to be compared to this Depot.
     * @return -> boolean: true if they are both instances of the same class and if they have the same capacity and if
     * they have the same resource contained.
     */
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