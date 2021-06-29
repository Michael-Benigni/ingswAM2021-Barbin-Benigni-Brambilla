package it.polimi.ingsw.client.view.lightweightmodel;

/**
 * Class that represents the view of a leader card.
 */
public class LWLeaderCard {
    /**
     * Identification number of this card.
     */
    private int id;

    private String description;

    /**
     * Integer that represents the index of the slot of this card.
     */
    private int slotIndex;

    /**
     * Constructor method of this class.
     */
    public LWLeaderCard(int id, String description, int slotIndex) {
        this.id = id;
        this.description = description;
        this.slotIndex = slotIndex;
    }

    /**
     * Getter method for the index of the slot.
     */
    public int getSlotIndex() {
        return slotIndex;
    }

    /**
     * Getter method for the description of this card.
     */
    public String getDescription() {
        return description;
    }
}
