package it.polimi.ingsw.model.gameresources.stores;

import it.polimi.ingsw.exception.NegativeResourceAmountException;
import it.polimi.ingsw.exception.NotContainedResourceException;
import it.polimi.ingsw.exception.NotEqualResourceTypeException;
import it.polimi.ingsw.exception.NullResourceAmountException;

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
    //TODO: This method is only used in test class till now 3/4/21. Detachable?


    /**
     * Method that store one resource inside the array of Strongbox. First it checks if a resource with the same type
     * already exists. If it exists, just increase its amount. Otherwise create a new element in the array.
     * @param storableResource -> resource to store in the strongbox
     * @throws NotEqualResourceTypeException -> can be throwed by "increaseAmount" method
     */
    void storeResourceInStrongbox(StorableResource storableResource) throws Exception {
        for (int i = 0; i < resourceContained.size(); ) {
            try {
                StorableResource increasedResource = this.resourceContained.get(i).increaseAmount(storableResource);
                this.resourceContained.add(i, increasedResource);
                return;
            } catch (NotEqualResourceTypeException e) {
                i++;
            }
        }
        resourceContained.add(storableResource);
    }


    /**
     * Method that return a copy of the entire array of resources that are contained into the strongbox
     * @return -> the array of resources.
     * @throws NegativeResourceAmountException -> can be thrown by "copyResource" method of "StorableResource" class.
     */
    ArrayList<StorableResource> getAllStoredResources() throws NegativeResourceAmountException, CloneNotSupportedException {
        ArrayList<StorableResource> copyList = new ArrayList<>(0);
        for(StorableResource r : resourceContained) {
            StorableResource temporaryStorableResource = (StorableResource) r.clone();
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
    void removeResourceFromStrongbox(StorableResource storableResource) throws NegativeResourceAmountException, NotContainedResourceException {
        for (int i = 0; i < resourceContained.size(); ) {
            try {
                StorableResource decreasedResource = this.resourceContained.get(i).decreaseAmount(storableResource);
                this.resourceContained.add(i, decreasedResource);
                return;
            } catch (NotEqualResourceTypeException e) {
                i++;
            } catch (NullResourceAmountException e) {
                this.resourceContained.remove(i);
                return;
            }
        }
        throw new NotContainedResourceException();
    }
}
