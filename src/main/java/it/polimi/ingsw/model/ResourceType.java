package it.polimi.ingsw.model;


/**
 * Class that represents all resources which can be stocked inside Depots and Strongbox
 */
public enum ResourceType {

    COIN("coin"), SERVANT("servant"), SHIELD("shield"), STONE("stone");

    private final String resourceTypeName;

    /**
     * Constructor method of ResourceType class
     * @param resourceTypeName -> String that can be:COIN, SERVANT, SHIELD, STONE
     */
    ResourceType(String resourceTypeName) {
        this.resourceTypeName = resourceTypeName;
    }

}
