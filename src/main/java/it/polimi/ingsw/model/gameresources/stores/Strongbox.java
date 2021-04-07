package it.polimi.ingsw.model.gameresources.stores;


import it.polimi.ingsw.exception.NegativeResourceAmountException;
import it.polimi.ingsw.exception.NotContainedResourceException;
import it.polimi.ingsw.exception.NotEqualResourceTypeException;

import java.util.ArrayList;

/**
 * Class that represents the player's strongbox on the personal board. It's a collection of resources
 */
public class Strongbox {

    private ArrayList<StorableResource> resourceContained;


    /**
     * Constructor method of Strongbox class
     * This method use the constructor method of ArrayList with a parameter "initialCapacity" = 0; so it will create an
     * empty array which first element is "EMPTY_ELEMENTDATA".
     */
    public Strongbox() {
        this.resourceContained = new ArrayList<>(0);
    }


    /**
     * Method that checks if the array "ResourceContained" is empty.
     * @return -> boolean: true if the array is null, false otherwise.
     */
    private boolean ifEmptyStrongbox()
    {
        return resourceContained.size() == 0;
    }
    //This method is only used in test class till now 3/4/21.


    /**
     * Method that checks if a resource of the given type already exists in the array
     * @param storableResource -> resource that i want to compare
     * @return -> return a boolean that represents if the resource is already contained
     */
    private boolean ifAlreadyContained(StorableResource storableResource) {

        for(StorableResource s : this.resourceContained)
            if(s.ifSameResourceType(storableResource))
                return true;
        return false;
    }


    /**
     * Method that looks at the array and returns the element which have the same Type of the provided resource
     * @param storableResource -> resource that contained the type of resource i'm looking for
     * @return -> element of the array which has same resourceType of the provided resource, otherwise returns null
     * @throws NotContainedResourceException -> thrown if doesn't exist a resource contained in this strongbox with the
     * same type of the one provided.
     */
    private StorableResource searchResourceInStrongbox(StorableResource storableResource) throws NotContainedResourceException {

        int pos;
        for(StorableResource s : this.resourceContained)
            if(s.ifSameResourceType(storableResource))
            {
                pos = resourceContained.indexOf(s);
                return resourceContained.get(pos);
            }
        throw new NotContainedResourceException();
    }


    /**
     * Method that store one resource inside the array of Strongbox. First it checks if a resource with the same type
     * already exists. If it exists, just increase its amount. Otherwise create a new element in the array.
     * @param storableResource -> resource to store in the strongbox
     * @throws NotEqualResourceTypeException -> can be throwed by "increaseAmount" method
     */
    void storeResourceInStrongbox(StorableResource storableResource) throws Exception {

        if(ifAlreadyContained(storableResource)) {
            searchResourceInStrongbox(storableResource).increaseAmount(storableResource);
        }
        else {
            resourceContained.add(storableResource);
        }

    }


    /**
     * Method that return a copy of the entire array of resources that are contained into the strongbox
     * @return -> the array of resources.
     * @throws NegativeResourceAmountException -> can be thrown by "copyResource" method of "StorableResource" class.
     */
    ArrayList<StorableResource> getAllStoredResources() throws NegativeResourceAmountException {

        ArrayList<StorableResource> copyList = new ArrayList<>(0);
        for(StorableResource r : resourceContained) {
            StorableResource temporaryStorableResource = (StorableResource) r.copyResource();
            copyList.add(temporaryStorableResource);
        }
        return copyList;
    }


    /**
     * Method that decrease the amount of the stored resource by the amount provided. If the amount reaches "0", the
     * element is removed from the array.
     * @param storableResource -> contains the type of the resource to be decremented and by how much to decrement it
     * @throws Exception -> exception thrown if the provided resource is not contained in this strongbox. Can also be
     * thrown by "decreaseAmount" method
     */
    void removeResourceFromStrongbox(StorableResource storableResource)
            throws Exception {

        if(ifAlreadyContained(storableResource))
        {
            searchResourceInStrongbox(storableResource).decreaseAmount(storableResource);
            if(searchResourceInStrongbox(storableResource).amountEqualTo(0))
                resourceContained.remove(searchResourceInStrongbox(storableResource));
        }
        else{ throw new NotContainedResourceException();}

    }
}
