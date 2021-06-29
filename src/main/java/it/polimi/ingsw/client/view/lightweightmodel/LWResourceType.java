package it.polimi.ingsw.client.view.lightweightmodel;

/**
 * Enumeration of the possible types of resource.
 */
public enum LWResourceType {
    SERVANT ("SERVANT"), STONE ("STONE"), SHIELD ("SHIELD"), COIN ("COIN");

    private final String type;

    /**
     * Constructor method of this enumeration.
     */
    LWResourceType(String type) {
        this.type = type;
    }
}
