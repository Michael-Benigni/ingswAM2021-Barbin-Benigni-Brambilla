package it.polimi.ingsw.client.view.lightweightmodel;

import java.util.ArrayList;

public class LWTemporaryContainer {
    ArrayList<LWResource> storableResources;
    int emptyResources;

    public LWTemporaryContainer() {
        this.storableResources = new ArrayList<>();
        this.emptyResources = 0;
    }

    public ArrayList<LWResource> getStorableResources() {
        return storableResources;
    }

    public int getEmptyResources() {
        return emptyResources;
    }
}
