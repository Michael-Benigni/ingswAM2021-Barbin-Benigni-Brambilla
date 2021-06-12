package it.polimi.ingsw.client.view.lightweightmodel;

/**
 *
 */
public enum LWResourceType {
    SERVANT ("SERVANT"), STONE ("STONE"), SHIELD ("SHIELD"), COIN ("COIN");

    private final String type;

    LWResourceType(String type) {
        this.type = type;
    }
}
