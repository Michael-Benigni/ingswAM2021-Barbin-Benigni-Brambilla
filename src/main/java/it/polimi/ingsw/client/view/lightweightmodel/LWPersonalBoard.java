package it.polimi.ingsw.client.view.lightweightmodel;


import java.util.ArrayList;

public class LWPersonalBoard {

    private ArrayList<LWDepot> warehouse;
    private ArrayList<LWResource> strongbox;
    private ArrayList<ArrayList<LWDevCard>> slots;
    private ArrayList<LWLeaderCard> leaderCardsPlayed;
    private ArrayList<LWLeaderCard> leaderCardsNotPlayed;
    private final LWTemporaryContainer temporaryContainer;
    private final ArrayList <LWWMPower> whiteMarblePowers;
    private final ArrayList <LWXProductionPower> extraProductionPowers;


    public LWPersonalBoard() {
        this.warehouse = new ArrayList<> ();
        this.strongbox = new ArrayList<> ();
        this.temporaryContainer = new LWTemporaryContainer();
        this.leaderCardsPlayed = new ArrayList<> ();
        this.leaderCardsNotPlayed = new ArrayList<> ();
        this.whiteMarblePowers = new ArrayList<> ();
        this.extraProductionPowers = new ArrayList<> ();
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

    public LWTemporaryContainer getTemporaryContainer() {
        return temporaryContainer;
    }

    public ArrayList<LWResource> getStrongbox() {
        return strongbox;
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

    public void updateTemporaryContainer(ArrayList<LWResource> storableResources, int emptyResources) {
        this.temporaryContainer.storableResources = storableResources;
        this.temporaryContainer.emptyResources = emptyResources;
    }

    public void updateSlots(LWDevCard addedDevCard, int numberOfSlot, int positionInSlot) {
        slots.get(numberOfSlot).add(positionInSlot, addedDevCard);
    }

    public void updateLeaderCards(ArrayList<LWLeaderCard> leaderCardsNotPlayed,
                                  ArrayList<LWLeaderCard> leaderCardsPlayed) {
        this.leaderCardsNotPlayed = leaderCardsNotPlayed;
        this.leaderCardsPlayed = leaderCardsPlayed;
    }

    public void addWMPowers(LWWMPower lwwmPower){
        this.whiteMarblePowers.add(lwwmPower);
    }

    public void addXProductionPowers(LWXProductionPower lwxProductionPower){
        this.extraProductionPowers.add(lwxProductionPower);
    }

    public ArrayList<LWWMPower> getWhiteMarblePowers() {
        return whiteMarblePowers;
    }

    public ArrayList<LWXProductionPower> getExtraProductionPowers() {
        return extraProductionPowers;
    }
}
