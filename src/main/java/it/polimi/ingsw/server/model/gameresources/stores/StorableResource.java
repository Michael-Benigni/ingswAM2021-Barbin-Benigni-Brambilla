package it.polimi.ingsw.server.model.gameresources.stores;

import it.polimi.ingsw.server.model.exception.NegativeResourceAmountException;
import it.polimi.ingsw.server.model.exception.NotEqualResourceTypeException;
import it.polimi.ingsw.server.model.exception.NullResourceAmountException;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.cards.leadercards.Requirement;
import it.polimi.ingsw.server.model.gameresources.Producible;
import it.polimi.ingsw.server.model.gameresources.Storable;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Class that represents 1 between 4 different type of resources, and also the quantity of that resource
 */
public class StorableResource implements Storable, Requirement, Producible {
    private int amount;
    private ResourceType resourceType;

    /**
     * Constructor method of the StorableResource class
     *
     * @param resourceType -> What kind of resource
     * @param amount       -> How many copies of that resource: need to be greater or equal than "0"
     * */
    public StorableResource(ResourceType resourceType, int amount) throws NegativeResourceAmountException {
        if (amount < 0)
            throw new NegativeResourceAmountException(null);
        else
            this.amount = amount;
        this.resourceType = resourceType;
    }

    /**
     * Method that sum two resources of the same type: need to be the same type
     * @param resource resource to add
     * @return
     */
    StorableResource increaseAmount(StorableResource resource) throws NotEqualResourceTypeException {
        if (!this.ifSameResourceType(resource))
            throw new NotEqualResourceTypeException();
        else {
            try {
                return new StorableResource(this.getResourceType(), this.getAmount() + resource.getAmount());
            } catch (NegativeResourceAmountException e) {
                return resource;
            }
        }
    }

    /**
     * Method that subtract two resources of the same type: need to be the same type and this.amount need to be greater
     * than resource.amount
     *
     * @param resource resource to subtract
     * @return
     */
    public StorableResource decreaseAmount(StorableResource resource) throws NegativeResourceAmountException, NotEqualResourceTypeException, NullResourceAmountException {
        if (!this.ifSameResourceType(resource))
            throw new NotEqualResourceTypeException();
        else if (this.getAmount() < resource.getAmount())
            throw new NegativeResourceAmountException(new StorableResource (this.getResourceType (), resource.getAmount () - this.getAmount ()));
        else if (this.getAmount() == resource.getAmount())
            throw new NullResourceAmountException();
        return new StorableResource(this.getResourceType(), this.getAmount() - resource.getAmount());
    }

    /**
     * Getter method for amount
     *
     * @return -> return int
     */
    public int getAmount() {
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
    public void activate(Player player, Game game) {
        this.store(player);
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

    /**
     * this method overrides the
     * "Object" method "equals"
     * @param o -> object we want to compare
     * @return -> boolean value: true if the caller and the specified object are equals
     *                           false if the caller and the specified object aren't equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StorableResource that = (StorableResource) o;
        return amount == that.amount && resourceType == that.resourceType;
    }


    /**
     * this method overrides the
     * "Object" method "clone"
     * @return -> the copy of the caller
     */
    @Override
    public StorableResource clone() {
        try {
            return (StorableResource) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }


    /**
     * this method overrides the
     * "Object" method "hashCode"
     * @return -> int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), amount, resourceType);
    }


    /**
     * this method overrides the
     * "Requirement" method "containedIn".
     * it checks if the player
     * satisfies this requirements
     * to play a leader card
     * or to pay a development card
     * or to start a production
     * @param player player we want to examine
     * @return boolean value: true if this resource is contained in the list of the player requirements
     *                        false if the player doesn't satisfy the previous condition.
     */
    @Override
    public boolean containedIn(Player player) {
        StorableResource thisCopy = this.clone();
        ArrayList <StorableResource> requirements = player.getResourceRequirements();
        for (StorableResource requirement : requirements) {
            try {
                StorableResource resourceDecreased = thisCopy.decreaseAmount (requirement);
                thisCopy.setAmount (resourceDecreased.getAmount ());
            } catch (NegativeResourceAmountException | NullResourceAmountException e) {
                return true;
            } catch (NotEqualResourceTypeException ignored) {

            }
        }
        return false;
    }

    /**
     * Method that set the amount of this storable resource to the one provided if and only if the provided amount
     * isn't negative.
     * @param amount integer to which to set the amount of this resource.
     */
    void setAmount(int amount) {
        if(amount >= 0)
            this.amount = amount;
    }

    @Override
    public void onProduced(Player player, Game game) {
        store(player);
    }


    /**
     * This method is invoked when a StorableResource has to be stored automatically, usually after a production. It is
     * stored in the TemporaryContainer because the player could perform different productions in one turn, but the
     * resources produced in the same turn can not be used for other productions of the same turn.
     * @param player
     */
    @Override
    public void store(Player player) {
        player.getPersonalBoard().getTempContainer().store(this);
    }

    @Override
    public String toString() {
        return amount + " " + resourceType.toString ();
    }
}

