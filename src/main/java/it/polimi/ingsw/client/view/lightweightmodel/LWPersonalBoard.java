package it.polimi.ingsw.client.view.lightweightmodel;

import java.util.ArrayList;

public class LWPersonalBoard {

    private ArrayList<LWDepot> warehouse;
    private ArrayList<LWResource> strongbox;
    private ArrayList<LWResource> temporary;
    private ArrayList<ArrayList<LWDevCard>> slots;
    private ArrayList<LWLeaderCard> leaderCardsPlayed;
    private ArrayList<LWLeaderCard> leaderCardsNotPlayed;


    public LWPersonalBoard() {
        this.warehouse = new ArrayList<> ();
        this.strongbox = new ArrayList<> ();
        this.temporary = new ArrayList<> ();
        this.leaderCardsPlayed = new ArrayList<> ();
        initSlots();
    }

    private void initSlots(){
        this.slots = new ArrayList<> ();
        for(int i = 0; i < 3; i++){
            slots.add(new ArrayList<>());
        }
    }

    public ArrayList<LWDepot> getWarehouse() {
        return warehouse;
    }

    public ArrayList<LWResource> getStrongbox() {
        return strongbox;
    }

    public ArrayList<LWResource> getTemporary() {
        return temporary;
    }

    public ArrayList<ArrayList<LWDevCard>> getSlots() {
        return slots;
    }

    public ArrayList<LWLeaderCard> getLeaderCardsPlayed() {
        return leaderCardsPlayed;
    }

    public ArrayList<LWLeaderCard> getLeaderCardsNotPlayed() {
        return leaderCardsNotPlayed;
    }

    public void updateWarehouse(ArrayList<LWDepot> warehouse) {
        this.warehouse = warehouse;
    }

    public void updateStrongbox(ArrayList<LWResource> strongbox) {
        this.strongbox = strongbox;
    }

    void updateTemporaryContainer(ArrayList<LWResource> temporary) {
        this.temporary = temporary;
    }

    public void updateSlots(LWDevCard addedDevCard, int numberOfSlot) {
        slots.get(numberOfSlot).add(addedDevCard);
    }

    public void updateLeaderCards(ArrayList<LWLeaderCard> leaderCardsNotPlayed,
                                  ArrayList<LWLeaderCard> leaderCardsPlayed) {
        this.leaderCardsNotPlayed = leaderCardsNotPlayed;
        this.leaderCardsPlayed = leaderCardsPlayed;
    }
}
