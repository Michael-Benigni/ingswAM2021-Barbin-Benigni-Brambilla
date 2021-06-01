package it.polimi.ingsw.client.view.lightweightmodel;

public class LWLeaderCard {
    private int id;
    private String description;

    public LWLeaderCard(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
