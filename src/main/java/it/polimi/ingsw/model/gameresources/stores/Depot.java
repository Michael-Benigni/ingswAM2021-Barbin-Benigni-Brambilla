package it.polimi.ingsw.model.gameresources.stores;


import it.polimi.ingsw.exception.DifferentResourceTypeInDepotException;
import it.polimi.ingsw.exception.NegativeResourceAmountException;
import it.polimi.ingsw.exception.ResourceOverflowInDepotException;


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
    Depot(int capacity) {   //TODO: replace with reading by json file.
        storedResource = null;
        this.capacity = capacity;
    }


    /**
     * Method that return if the depot is empty.
     * @return -> boolean: true if it's empty, false otherwise.
     */
    boolean ifDepotIsEmpty()
    {
        return storedResource == null;
    }


    /**
     *
     * @param resourceToCompare
     * @return
     */
    private boolean ifSameResourceTypeInDepot(StorableResource resourceToCompare)
    {
        if(storedResource != null) {
            return resourceToCompare.ifSameResourceType(storedResource);
        }
        else{
            return false;
        }
    }


    /**
     * Method that puts a resource in this depot. This action is performed if and only if this depot is empty or
     * if it contains a resource of the same type as the one provided in input and, in both cases, the amount of the
     * contained resource must not exceed "capacity".
     * @param resourceToStore -> resource to be added to this depot
     * @throws Exception thrown if: amount that exceeds the capacity or different resource type of the provided resource
     * and the contained one.
     */
    void storeResourceInDepot(StorableResource resourceToStore)
            throws Exception{
        if(ifDepotIsEmpty())
        {
            if(resourceToStore.amountEqualTo(capacity) || resourceToStore.amountLessThan(capacity))
                storedResource = resourceToStore;
            else{ throw new ResourceOverflowInDepotException();}
        }
        else{
            if(ifSameResourceTypeInDepot(resourceToStore)){
                storedResource = increaseAmountInDepot(resourceToStore);
            }
            else{ throw new DifferentResourceTypeInDepotException();}
        }
    }


    /**
     * Method that method that creates a copy of the resource contained in this depot. Then it adds the amount to the
     * amount of the resource provided. If the total amount does not exceed the capacity, then returns the copy.
     * @param resourceToAdd -> resource to be added to the stored resource.
     * @return -> a "StorableResource" object, which represents the total resource.
     * @throws Exception -> thrown if: the sum of the two amounts is greater than the capacity of ths depot.
     */
    private StorableResource increaseAmountInDepot(StorableResource resourceToAdd)
            throws Exception{

        StorableResource temporaryStorableResource;
        temporaryStorableResource = (StorableResource) storedResource.copyResource();
        temporaryStorableResource.increaseAmount(resourceToAdd);
        if(temporaryStorableResource.amountLessThan(capacity) || temporaryStorableResource.amountEqualTo(capacity))
            return temporaryStorableResource;
        else{ throw new ResourceOverflowInDepotException(); }
    }


    /**
     * Method that returns the resource contained in this depot, casting it to a "StorableResource" object.
     * @return -> the stored resource casted to a "StorableResource" object.
     * @throws NegativeResourceAmountException -> can be thrown by "copyResource" method of "StorableResource" class.
     */
    StorableResource getStoredResource() throws NegativeResourceAmountException, CloneNotSupportedException {
        if(this.ifDepotIsEmpty()) {
            return null;
        } else{
            return (StorableResource) storedResource.copyResource();
        }
    }


    /**
     * Method that removes resources from this depot if and only if it is not empty and the contained resource has the
     * same type as the one provided in input.
     * @param resourceToRemove -> resource to be removed from this depot.
     * @throws Exception -> thrown if trying to remove a resource of a type other
     * than the one contained. Can be also thrown by "decreaseAmountInDepot" method.
     */
    void removeResourceFromDepot(StorableResource resourceToRemove)
            throws Exception {

        if(!ifDepotIsEmpty() && ifSameResourceTypeInDepot(resourceToRemove)){
                storedResource = decreaseAmountInDepot(resourceToRemove);
            }
        else{ throw new DifferentResourceTypeInDepotException();}
    }


    /**
     * Method that creates a copy of the resource contained in this depot. Then it decreases the amount by a value
     * equals to the amount of the resource provided in input. If the obtained integer is greater than zero, the updated
     * copy is returned. Otherwise, an object of class "EmptyResource" is created and returned.
     * @param resourceToSubtract -> resource to be subtracted to the stored resource.
     * @return -> the updated resource if the amount is greater than zero. Otherwise, an "EmptyResource" is returned.
     * @throws Exception -> can be thrown by "decreaseAmount" method of "StorableResource" class.
     */
    private StorableResource decreaseAmountInDepot(StorableResource resourceToSubtract)
            throws Exception {

        StorableResource temporaryStorableResource;
        temporaryStorableResource = storedResource;
        temporaryStorableResource.decreaseAmount(resourceToSubtract);
        if(temporaryStorableResource.amountEqualTo(0)){
            return null;
        }
        else{ return temporaryStorableResource;}
    }
}
