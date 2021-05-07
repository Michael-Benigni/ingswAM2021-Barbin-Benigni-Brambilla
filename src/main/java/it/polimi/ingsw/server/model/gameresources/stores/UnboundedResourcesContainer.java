package it.polimi.ingsw.server.model.gameresources.stores;

import it.polimi.ingsw.server.exception.NegativeResourceAmountException;
import it.polimi.ingsw.server.exception.NotContainedResourceException;
import it.polimi.ingsw.server.exception.NotEqualResourceTypeException;
import it.polimi.ingsw.server.exception.NullResourceAmountException;
import java.util.ArrayList;
import java.util.Objects;

public class UnboundedResourcesContainer {

    private ArrayList<StorableResource> containedResources;

    /**
     * Method that stores the provided list of resources in this unbounded resource container.
     * @param resources arraylist of storable resource to be stored in this container.
     * @return this updated container.
     */
    public UnboundedResourcesContainer storeAll(ArrayList<StorableResource> resources) {
        for (StorableResource resource : resources)
            this.store(resource);
        return this;
    }

    protected void clear() {
        this.containedResources.clear();
    }

    /**
     * Constructor method of this class.
     * This method use the constructor method of ArrayList with a parameter "initialCapacity" = 0; so it will create an
     * empty array.
     */
    public UnboundedResourcesContainer() {
        this.containedResources = new ArrayList<>(0);
    }


    /**
     * Method that store one resource inside this container. First it checks if a resource with the same type
     * already exists. If it exists, just increase its amount. Otherwise create a new element in the array.
     * @param storableResource resource to be stored in this container.
     */
    public void store(StorableResource storableResource) {
        for (int i = 0; i < containedResources.size(); ) {
            try {
                StorableResource newResource = this.containedResources.get(i).increaseAmount(storableResource);
                this.containedResources.remove(i);
                this.containedResources.add(newResource);
                return;
            } catch (NotEqualResourceTypeException e) {
                i++;
            }
        }
        containedResources.add(storableResource);
    }


    /**
     * Method that return a copy of the entire array of resources that are contained into the strongbox
     * @return a copy of the array of resources.
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
     * @param storableResource contains the type of the resource to be decremented and by how much to decrement it
     * @throws NotContainedResourceException thrown if the provided resource is not contained in this container.
     */
    public void remove(StorableResource storableResource) throws NegativeResourceAmountException, NotContainedResourceException {
        for (int i = 0; i < containedResources.size(); ) {
            try {
                StorableResource decreasedResource = this.containedResources.get(i).decreaseAmount(storableResource);
                this.containedResources.remove(i);
                this.containedResources.add(decreasedResource);
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
        UnboundedResourcesContainer container = (UnboundedResourcesContainer) o;
        return Objects.equals(containedResources, container.containedResources);
    }

    @Override
    public int hashCode() {
        return Objects.hash(containedResources);
    }
}
