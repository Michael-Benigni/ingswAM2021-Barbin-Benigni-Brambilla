package it.polimi.ingsw.model.gameresources.stores;

import it.polimi.ingsw.exception.NegativeResourceAmountException;
import it.polimi.ingsw.exception.NotEqualResourceTypeException;
import it.polimi.ingsw.exception.NullResourceAmountException;
import it.polimi.ingsw.model.gameresources.markettray.Resource;

/**
 * Class that represents 1 between 4 different type of resources, and also the quantity of that resource
 */
public class StorableResource extends Resource {


    private int amount;
    private ResourceType resourceType;

    /**
     * Constructor method of the StorableResource class
     *
     * @param resourceType -> What kind of resource
     * @param amount       -> How many copies of that resource: need to be greater or equal than "0"
     */

    public StorableResource(ResourceType resourceType, int amount) throws NegativeResourceAmountException {
        if (amount < 0)
            throw new NegativeResourceAmountException();
        else
            this.amount = amount;
        this.resourceType = resourceType;
    }


    /**
     * Method that sum two resources of the same type: need to be the same type
     *
     * @param resource -> resource to add
     * @return
     */
    StorableResource increaseAmount(StorableResource resource) throws NotEqualResourceTypeException {
        if (!this.getResourceType().equals(resource.getResourceType()))
            throw new NotEqualResourceTypeException();
        else {
            try {
                return new StorableResource(this.getResourceType(), this.getAmount() + resource.getAmount());
            } catch (NegativeResourceAmountException e) {
                e.printStackTrace();
                return resource;
            }
        }
    }


    /**
     * Method that subtract two resources of the same type: need to be the same type and this.amount need to be greater
     * than resource.amount
     *
     * @param resource -> resource to subtract
     * @return
     */
    StorableResource decreaseAmount(StorableResource resource) throws NegativeResourceAmountException, NotEqualResourceTypeException, NullResourceAmountException {
        if (!this.getResourceType().equals(resource.getResourceType()))
            throw new NotEqualResourceTypeException();
        else if (this.getAmount() < resource.getAmount())
            throw new NegativeResourceAmountException();
        else if (this.getAmount() == resource.getAmount())
            throw new NullResourceAmountException();
        return new StorableResource(this.getResourceType(), this.getAmount() - resource.getAmount());

    }


    /**
     * Getter method for amount
     *
     * @return -> return int
     */
    private int getAmount() {
        return amount;
    }


    /**
     * Getter method for ResourceType
     *
     * @return -> return ResourceType of StorableResource
     */
    private ResourceType getResourceType() {
        return resourceType;
    }


    /**
     * Method inherited by the interface "Resource"
     */
    @Override
    protected void activate() {
        //TODO: what kind of action need to be executed by the activation of stockResource ??
    }


    /**
     * this method creates a copy of the object StorableResource
     *
     * @return the created copy
     * @throws NegativeResourceAmountException -> can be throwed by the constructor method if StorableResource class
     */
    @Override
    protected Resource clone() throws CloneNotSupportedException {
        return (StorableResource) super.clone();
    }


    /**
     * Method that verify if the two resources have the same resourceType
     * This method is called on the first resource to compare
     *
     * @param resourceToCompare -> second resource to compare
     * @return -> boolean that represents if the two objects share the resourceType
     */
    boolean ifSameResourceType(StorableResource resourceToCompare) {
        return this.resourceType.equals(resourceToCompare.resourceType);
    }

    /**
     * Method that returns if this resource has amount less tha the one provided.
     *
     * @param amountToCompare -> integer to be compared to the amount of this resource.
     * @return -> boolean: true if this resource has amount less than the one provided.
     */
    boolean amountLessEqualThan(int amountToCompare) {
        return this.getAmount() <= amountToCompare;
    }
}

