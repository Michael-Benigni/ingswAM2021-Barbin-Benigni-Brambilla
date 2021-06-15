package it.polimi.ingsw.client.view.lightweightmodel;

public class LWLeaderCard {
    private int id;
    private String description;
    private int slotIndex;

    public LWLeaderCard(int id, String description, int slotIndex) {
        this.id = id;
        this.description = description;
        this.slotIndex = slotIndex;
    }

    public int getSlotIndex() {
        return slotIndex;
    }

    public String getDescription() {
        return description;
    }
}
