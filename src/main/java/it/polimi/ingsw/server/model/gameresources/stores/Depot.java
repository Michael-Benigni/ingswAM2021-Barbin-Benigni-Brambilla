package it.polimi.ingsw.server.model.gameresources.stores;


import it.polimi.ingsw.server.model.exception.*;

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
     * @param resource resource to be compared to the one stored in this depot.
     * @return boolean: true if the two resource have the same type, false otherwise.
     */
    boolean alreadyContained (StorableResource resource) {
        try {
            return getStoredResource().ifSameResourceType(resource);
        } catch (EmptyDepotException e) {
            return false;
        }
    }


    /**
     * Method that puts a resource in this depot. This action is performed if and only if this depot is empty or
     * if it contains a resource of the same type as the one provided in input and, in both cases, the amount of the
     * contained resource must not exceed "capacity". If it happens, this method fills this depot, calculates the
     * difference and throws an exception.
     * @param resourceToStore resource to be added to this depot.
     */
    void storeResourceInDepot(StorableResource resourceToStore) throws NotEqualResourceTypeException, ResourceOverflowInDepotException {
        StorableResource resourceTot;
        if (resourceToStore != null) {
            try {
                resourceTot = this.getStoredResource ().increaseAmount (resourceToStore);
            } catch (EmptyDepotException e) {
                resourceTot = resourceToStore.clone ();
            } if (resourceTot.amountLessEqualThan (capacity)) {
                storedResource = resourceTot;
            } else {
                storedResource = resourceToStore;
                storedResource.setAmount (capacity);
                try {
                    throw new ResourceOverflowInDepotException (resourceTot.decreaseAmount (storedResource));
                } catch (NullResourceAmountException | NegativeResourceAmountException | NotEqualResourceTypeException e) {
                    e.printStackTrace ();
                }
            }
        }
    }


    /**
     * Method that decrease the amount of the contained resource.
     * @param resourceToRemove Storable resource whose amount must be subtracted from the already contained resource.
     * @throws NotEqualResourceTypeException can be thrown by "decreaseAmount" method of "StorableResource" class.
     * @throws NegativeResourceAmountException can be thrown by "decreaseAmount" method of "StorableResource" class.
     */
    void removeResourceFromDepot(StorableResource resourceToRemove) throws NotEqualResourceTypeException, NegativeResourceAmountException, EmptyDepotException {
        try {
            storedResource = this.getStoredResource().decreaseAmount(resourceToRemove);
        } catch (NullResourceAmountException e) {
            storedResource = null;
        }
    }


    /**
     * Getter method for "storedResource" attribute of this class.
     * @return the storable resource contained in this depot or throws an exception if the depot is empty.
     */
    StorableResource getStoredResource() throws EmptyDepotException {
        if(storedResource != null)
            return storedResource;
        else
            throw new EmptyDepotException();
    }


    /**
     * Method that returns if the two object are equals.
     * @param o object to be compared to this Depot.
     * @return boolean: true if they are both instances of the same class and if they have the same capacity and if
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