package it.polimi.ingsw.client.view.lightweightmodel;

import java.util.ArrayList;

public class LWeightPersonalBoard {

    /**
     *
     */
    private class LWResource {

        private Type type;
        private int amount;
        private LWResource(Type type, int amount) {
            this.type = type;
            this.amount = amount;
        }

    }

    /**
     *
     */
    private enum Type {
        SERVANT ("SERVANT"), STONE ("STONE"), SHIELD ("SHIELD"), COIN ("COIN"), EMPTY ("empty");


        private final String type;
        Type(String type) {
            this.type = type;
        }

    }

    private class LWDepot {
        private LWResource resource;
        private int capacity;
        private Type type;
    }


    private ArrayList<LWDepot> warehouse;
    private ArrayList<LWResource> strongbox;
    private ArrayList<LWResource> temporary;
    private ArrayList<ArrayList<LWDevCard>> devCards; // max 3
    private ArrayList<LWLeaderCard> leaderCardsPlayed; // 4 at the beginning, then 2
    private ArrayList<LWLeaderCard> leaderCardsNotPlayed;

    public LWeightPersonalBoard() {
        this.warehouse = new ArrayList<> ();
        this.strongbox = new ArrayList<> ();
        this.temporary = new ArrayList<> ();
        this.devCards = new ArrayList<> ();
        this.leaderCardsPlayed = new ArrayList<> ();
    }

    void updateWarehouse(ArrayList<LWDepot> warehouse) {
        this.warehouse = warehouse;
    }

    void updateStrongbox(ArrayList<LWResource> strongbox) {
        this.strongbox = strongbox;
    }

    void updateTemporaryContainer(ArrayList<LWResource> temporary) {
        this.temporary = temporary;
    }

    public void updateDevCards(ArrayList<ArrayList<LWDevCard>> devCards) {
        this.devCards = devCards;
    }

    public void updateLeaderCardsPlayed(ArrayList<LWLeaderCard> leaderCardsPlayed) {
        this.leaderCardsPlayed = leaderCardsPlayed;
    }

    public void updateLeaderCardsNotPlayed(ArrayList<LWLeaderCard> leaderCardsNotPlayed) {
        this.leaderCardsNotPlayed = leaderCardsNotPlayed;
    }
}
