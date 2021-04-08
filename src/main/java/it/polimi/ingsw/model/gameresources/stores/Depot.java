package it.polimi.ingsw.model.gameresources.stores;


import it.polimi.ingsw.exception.DifferentResourceTypeInDepotException;
import it.polimi.ingsw.exception.ResourceOverflowInDepotException;


/**
 * Class which represents a single depot of the warehouse depots.
 * of storable Resources.
 */
public class Depot {

    private StorableResource storedResource;
    private final int capacity;

    int getCapacity(){ return capacity;}

    /**
     * Constructor method of "Depot" class. This method create an empty Arraylist of storable resources and reads
     * the capacity from a json file.
     */
    Depot(int capacity) {
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