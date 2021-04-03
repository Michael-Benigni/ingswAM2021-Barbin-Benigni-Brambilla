package it.polimi.ingsw.model.personalboard;

import it.polimi.ingsw.exception.NegativeResourceAmountException;
import it.polimi.ingsw.exception.NotEqualResourceTypeException;
import it.polimi.ingsw.model.gameresources.Resource;

/**
 * Class that represents 1 between 4 different type of resources, and also the quantity of that resource
 */
public class StorableResource implements Resource {


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
    void increaseAmount(StorableResource resource) throws NotEqualResourceTypeException {

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
    void decreaseAmount(StorableResource resource) throws Exception {

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
    private int getAmount() {

        return amount;

    }


    /**
     * Getter method for ResourceType
     * @return -> return ResourceType of StorableResource
     */
    private ResourceType getResourceType() {

        return resourceType;

    }


    /**
     * Method inherited by the interface "Resource"
     */
    @Override
    public void activate() {
    //TODO: what kind of action need to be executed by the activation of stockResource ??
    }


    /**
     * this method creates a copy of the object StorableResource
     * @return the created copy
     * @throws NegativeResourceAmountException -> can be throwed by the constructor method if StorableResource class
     */
    @Override
    public Resource copyResource() throws NegativeResourceAmountException {
        StorableResource copy = new StorableResource(this.resourceType, this.amount);
        return copy;
    }


    /**
     * Method that verify if the two resources have the same resourceType
     * This method is called on the first resource to compare
     * @param resourceToCompare -> second resource to compare
     * @return -> boolean that represents if the two objects share the resourceType
     */
    boolean ifSameResourceType(StorableResource resourceToCompare)
    {
        return this.resourceType.equals(resourceToCompare.resourceType);
    }


    /**
     * Method that return if two resources have same type and same amount
     * @param resourceToCompare -> resource to be compared to this
     * @return -> boolean: true if equal
     */
    boolean equals(StorableResource resourceToCompare)
    {
        return this.getAmount() == resourceToCompare.getAmount() && this.ifSameResourceType(resourceToCompare);
    }


    /**
     * Method that return if the resource has the provided amount.
     * @param amountToCompare -> integer to be compare to the resource's amount.
     * @return -> boolean: true if the two amounts are equal.
     */
    boolean amountEqualTo(int amountToCompare)
    {
        return this.getAmount() == amountToCompare;
    }


    /**
     * Method that returns if this resource has amount less tha the one provided.
     * @param amountToCompare -> integer to be compared to the amount of this resource.
     * @return -> boolean: true if this resource has amount less than the one provided.
     */
    boolean amountLessThan(int amountToCompare){
        return this.getAmount() < amountToCompare;
    }


    /**
     * Method that return if the resource has the provided resource type.
     * @param resourceType -> resource type to be compared to the resource's type.
     * @return -> boolean: true if the two types are equal
     */
    boolean resourceTypeEqualTo(ResourceType resourceType){
        return this.getResourceType() == resourceType;
    }

}
