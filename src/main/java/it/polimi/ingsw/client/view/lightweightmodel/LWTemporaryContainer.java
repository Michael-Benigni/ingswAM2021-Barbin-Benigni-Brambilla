package it.polimi.ingsw.client.view.lightweightmodel;

import java.util.ArrayList;

/**
 * Class that represents the view of the temporary container.
 */
public class LWTemporaryContainer {

    /**
     * Resource stored in this temporary container
     */
    ArrayList<LWResource> storableResources;

    /**
     * Number of the empty resources in this temporary container.
     */
    int emptyResources;

    /**
     * Constructor method of this class.
     */
    public LWTemporaryContainer() {
        this.storableResources = new ArrayList<>();
        this.emptyResources = 0;
    }

    /**
     * Getter method for the list of resources stored in this temporary container.
     */
    public ArrayList<LWResource> getStorableResources() {
        return storableResources;
    }

    /**
     * Getter method for the number of empty resources stored in this temporary container.
     */
    public int getEmptyResources() {
        return emptyResources;
    }
}
