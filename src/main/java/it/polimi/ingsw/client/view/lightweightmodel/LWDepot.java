package it.polimi.ingsw.client.view.lightweightmodel;

import com.google.gson.annotations.SerializedName;

public class LWDepot {
    @SerializedName ("storedResource")
    private LWResource storedResource;
    @SerializedName ("capacity")
    private int capacity;
    @SerializedName ("type")
    private LWResourceType type;

    public LWDepot() {
    }

    public LWDepot(LWResource storedResource, int capacity, LWResourceType type) {
        this.storedResource = storedResource;
        this.capacity = capacity;
        this.type = type;
    }

    @Override
    public String toString() {
        String borderChar = "_";
        String border = "";
        // width must be even
        int width = capacity * 4 + 30;
        for (int i = 0; i < width; i++) {
            border = border + borderChar;
        }
        String capacityStr = "capacity: " + capacity + (type != null ? ", type: " + type : "");
        String resourceStr = "content: " + storedResource.toString ();
        int numSpacePadding = Math.round((width - Math.max (capacityStr.length (), resourceStr.length ())) / 2);
        for (int i = 0; i < numSpacePadding; i++) {
            if (capacityStr.length () < width - 2)
                capacityStr = " " + capacityStr + " ";
            if (resourceStr.length () < width - 2)
                resourceStr = " " + resourceStr + " ";
        }
        if (capacityStr.length () % 2 != 0)
            capacityStr = capacityStr + " ";
        if (resourceStr.length () % 2 != 0)
            resourceStr = resourceStr + " ";
        return  border + "\n" +
                "/" + capacityStr + "\\\n" +
                "/" + resourceStr + "\\" ;
    }

    public LWResource getStoredResource() {
        return storedResource;
    }

    public int getCapacity() {
        return capacity;
    }

    public LWResourceType getType() {
        return type;
    }
}
