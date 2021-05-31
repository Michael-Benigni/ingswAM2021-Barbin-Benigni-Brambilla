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

    public class LWDepot {
        private LWResource resource;
        private int capacity;
        private Type type;
    }


    private ArrayList<LWDepot> warehouse;
    private ArrayList<LWResource> strongbox;
    private ArrayList<LWResource> temporary;
    private ArrayList<ArrayList<LWDevCard>> slots; // max 3
    private ArrayList<LWLeaderCard> leaderCardsPlayed; // 4 at the beginning, then 2
    private ArrayList<LWLeaderCard> leaderCardsNotPlayed;

    public LWeightPersonalBoard() {
        this.warehouse = new ArrayList<> ();
        this.strongbox = new ArrayList<> ();
        this.temporary = new ArrayList<> ();
        initSlots();
        this.leaderCardsPlayed = new ArrayList<> ();
    }

    private void initSlots(){
        this.slots = new ArrayList<> ();
        for(int i = 0; i < 3; i++){
            slots.add(new ArrayList<>());
        }
    }

    public void updateWarehouse(ArrayList<LWDepot> warehouse) {
        this.warehouse = warehouse;
    }

    void updateStrongbox(ArrayList<LWResource> strongbox) {
        this.strongbox = strongbox;
    }

    void updateTemporaryContainer(ArrayList<LWResource> temporary) {
        this.temporary = temporary;
    }

    public void updateSlots(LWDevCard addedDevCard, int numberOfSlot) {
        slots.get(numberOfSlot).add(addedDevCard);
    }

    public void updateLeaderCardsPlayed(ArrayList<LWLeaderCard> leaderCardsPlayed) {
        this.leaderCardsPlayed = leaderCardsPlayed;
    }

    public void updateLeaderCardsNotPlayed(ArrayList<LWLeaderCard> leaderCardsNotPlayed) {
        this.leaderCardsNotPlayed = leaderCardsNotPlayed;
    }
}
