package it.polimi.ingsw.client.view.lightweightmodel;

import it.polimi.ingsw.server.model.gameresources.markettray.MarbleColour;
import java.util.ArrayList;

public class LWeightPersonalBoard {

    /**
     *
     */
    private class Resource {

        private Type type;
        private int amount;
        private Resource(Type type, int amount) {
            this.type = type;
            this.amount = amount;
        }

    }

    /**
     *
     */
    private enum Type {
        SERVANT ("SERVANT"), STONE ("STONE"), SHIELD ("SHIELD"), COIN ("COIN");


        private final String type;
        Type(String type) {
            this.type = type;
        }

    }


    private ArrayList<Resource> warehouse;
    private ArrayList<Resource> strongbox;
    private ArrayList<MarbleColour> temporary;
    private ArrayList<LWDevCard> devCards; // max 3
    private ArrayList<LWLeaderCard> leaderCardsPlayed; // 4 at the beginning, then 2
    private ArrayList<LWLeaderCard> leaderCardsNotPlayed;

    public LWeightPersonalBoard() {
        this.warehouse = new ArrayList<> ();
        this.strongbox = new ArrayList<> ();
        this.temporary = new ArrayList<> ();
        this.devCards = new ArrayList<> ();
        this.leaderCardsPlayed = new ArrayList<> ();
    }

    void updateWarehouse(ArrayList<Resource> warehouse) {
        this.warehouse = warehouse;
    }

    void updateStrongbox(ArrayList<Resource> strongbox) {
        this.strongbox = strongbox;
    }

    void updateTemporaryContainer(ArrayList<MarbleColour> temporary) {
        this.temporary = temporary;
    }

    public void updateDevCards(ArrayList<LWDevCard> devCards) {
        this.devCards = devCards;
    }

    public void updateLeaderCardsPlayed(ArrayList<LWLeaderCard> leaderCardsPlayed) {
        this.leaderCardsPlayed = leaderCardsPlayed;
    }

    public void updateLeaderCardsNotPlayed(ArrayList<LWLeaderCard> leaderCardsNotPlayed) {
        this.leaderCardsNotPlayed = leaderCardsNotPlayed;
    }
}
