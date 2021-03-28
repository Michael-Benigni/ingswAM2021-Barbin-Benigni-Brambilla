package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.NegativeResourceAmountException;
import it.polimi.ingsw.exception.NotEqualResourceTypeException;


/**
 * Class that represents 1 between 4 different type of resources, and also the quantity of that resource
 */
public class StorableResource implements Resource{


    private int amount;
    private final ResourceType resourceType;


    /**
     * Constructor method of the StorableResource class
     * @param resourceType -> What kind of resource
     * @param amount -> How many copies of that resource: need to be greater or equal than "0"
     */
    StorableResource(ResourceType resourceType, int amount) throws NegativeResourceAmountException {
        if(amount < 0)
            throw new NegativeResourceAmountException();
        else
            this.amount = amount;
        this.resourceType = resourceType;
    }


    /**
     * Method that sum two resources of the same type: need to be the same type
     * @param resource -> resource to add
     */
    void increaseAmount(StorableResource resource) throws NegativeResourceAmountException, NotEqualResourceTypeException {

        if(this.getResourceType() != resource.getResourceType())
            throw new NotEqualResourceTypeException();
        else
            this.amount = this.amount + resource.amount;

    }


    /**
     * Method that subtract two resources of the same type: need to be the same type and this.amount need to be greater
     * than resource.amount
     * @param resource -> resource to subtract
     */
    void decreaseAmount(StorableResource resource) throws NotEqualResourceTypeException, NegativeResourceAmountException {

        if(this.getResourceType() != resource.getResourceType())
            throw new NotEqualResourceTypeException();
        else
            if (this.getAmount() < resource.getAmount())
                throw new NegativeResourceAmountException();
            else
                this.amount = this.getAmount() - resource.getAmount();

    }


    /**
     * Getter method for amount
     * @return -> return int
     */
    int getAmount() {

        return amount;

    }


    /**
     * Getter method for ResourceType
     * @return -> return ResourceType of StorableResource
     */
    ResourceType getResourceType() {

        return resourceType;

    }


    /**
     * Method inherited by the interface "Resource"
     */
    @Override
    public void activate() {
    //TO DO: what kind of action need to be executed by the activation of stockResource ??
    }

    /**
     * this method creates a copy of the object StorableResource
     * @return the created copy
     * @throws NegativeResourceAmountException
     */
    @Override
    public Resource copyResource() throws NegativeResourceAmountException {
        StorableResource copy = new StorableResource(this.resourceType, this.amount);
        return copy;
    }
}
