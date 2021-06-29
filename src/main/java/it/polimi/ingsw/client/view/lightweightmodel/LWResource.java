package it.polimi.ingsw.client.view.lightweightmodel;

import com.google.gson.annotations.SerializedName;

/**
 * Class that represents the view of a resource.
 */
public class LWResource implements LWProducible {
    @SerializedName ("resourceType")
    private LWResourceType resourceType;
    @SerializedName ("amount")
    private int amount;

    /**
     * Constructor method of this class.
     */
    public LWResource(LWResourceType resourceType, int amount) {
        this.resourceType = resourceType;
        this.amount = amount;
    }

    /**
     * Getter method for the type of this resource.
     */
    public LWResourceType getResourceType() {
        return resourceType;
    }

    /**
     * Getter method for the amount of this resource.
     */
    public int getAmount() {
        return amount;
    }
}
