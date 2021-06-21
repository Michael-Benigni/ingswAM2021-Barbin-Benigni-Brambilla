package it.polimi.ingsw.client.view.lightweightmodel;

import com.google.gson.annotations.SerializedName;

public class LWResource implements LWProducible {
    @SerializedName ("resourceType")
    private LWResourceType resourceType;
    @SerializedName ("amount")
    private int amount;

    public LWResource(LWResourceType resourceType, int amount) {
        this.resourceType = resourceType;
        this.amount = amount;
    }


    public LWResourceType getResourceType() {
        return resourceType;
    }

    public int getAmount() {
        return amount;
    }
}
