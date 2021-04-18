package it.polimi.ingsw.model.gameresources.stores;

import it.polimi.ingsw.exception.NegativeResourceAmountException;
import it.polimi.ingsw.exception.NotContainedResourceException;
import it.polimi.ingsw.exception.NotEqualResourceTypeException;
import it.polimi.ingsw.exception.NullResourceAmountException;

import java.util.ArrayList;
import java.util.Objects;

abstract class ResourcesContainer {

    private ArrayList<StorableResource> containedResources;


    /**
     * Constructor method of Strongbox class
     * This method use the constructor method of ArrayList with a parameter "initialCapacity" = 0; so it will create an
     * empty array which first element is "EMPTY_ELEMENTDATA".
     */
    public ResourcesContainer() {
        this.containedResources = new ArrayList<>(0);
    }


    /**
     * Method that store one resource inside the array of Strongbox. First it checks if a resource with the same type
     * already exists. If it exists, just increase its amount. Otherwise create a new element in the array.
     * @param storableResource -> resource to store in the strongbox
     * @throws NotEqualResourceTypeException -> can be throwed by "increaseAmount" method
     */
    void store(StorableResource storableResource) {
        for (int i = 0; i < containedResources.size(); ) {
            try {
                StorableResource increasedResource = this.containedResources.get(i).increaseAmount(storableResource);
                this.containedResources.add(i, increasedResource);
                return;
            } catch (NotEqualResourceTypeException e) {
                i++;
            }
        }
        containedResources.add(storableResource);
    }


    /**
     * Method that return a copy of the entire array of resources that are contained into the strongbox
     * @return -> the array of resources.
     * @throws NegativeResourceAmountException -> can be thrown by "copyResource" method of "StorableResource" class.
     */
    public ArrayList<StorableResource> getAllResources() {
        ArrayList<StorableResource> copyList = new ArrayList<>(0);
        for(StorableResource r : containedResources) {
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
    void removeResource(StorableResource storableResource) throws NegativeResourceAmountException, NotContainedResourceException {
        for (int i = 0; i < containedResources.size(); ) {
            try {
                StorableResource decreasedResource = this.containedResources.get(i).decreaseAmount(storableResource);
                this.containedResources.add(i, decreasedResource);
                return;
            } catch (NotEqualResourceTypeException e) {
                i++;
            } catch (NullResourceAmountException e) {
                this.containedResources.remove(i);
                return;
            }
        }
        throw new NotContainedResourceException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourcesContainer container = (ResourcesContainer) o;
        return Objects.equals(containedResources, container.containedResources);
    }

    @Override
    public int hashCode() {
        return Objects.hash(containedResources);
    }
}
