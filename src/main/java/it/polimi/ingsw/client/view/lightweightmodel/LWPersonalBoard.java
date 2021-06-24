package it.polimi.ingsw.client.view.lightweightmodel;


import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.utils.Attachable;
import java.util.ArrayList;

public class LWPersonalBoard implements Attachable<UI> {

    private ArrayList<LWDepot> warehouse;
    private ArrayList<LWResource> strongbox;
    private ArrayList<ArrayList<LWDevCard>> slots;
    private ArrayList<LWLeaderCard> leaderCardsPlayed;
    private ArrayList<LWLeaderCard> leaderCardsNotPlayed;
    private final LWTemporaryContainer temporaryContainer;
    private final ArrayList <LWWMPower> whiteMarblePowers;
    private final ArrayList <LWXProductionPower> extraProductionPowers;
    private UI ui;


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
        this.ui.onWarehouseChanged();
    }

    public void updateStrongbox(ArrayList<LWResource> strongbox) {
        this.strongbox = strongbox;
        this.ui.onStrongboxChanged();
    }

    public void updateTemporaryContainer(ArrayList<LWResource> storableResources, int emptyResources) {
        this.temporaryContainer.storableResources = storableResources;
        this.temporaryContainer.emptyResources = emptyResources;
        this.ui.onTempContainerChanged();
    }

    public void updateSlots(LWDevCard addedDevCard, int numberOfSlot, int positionInSlot) {
        slots.get(numberOfSlot).add(positionInSlot, addedDevCard);
        this.ui.onSlotDevCardsChanged ();
    }

    public void updateLeaderCards(ArrayList<LWLeaderCard> leaderCardsNotPlayed,
                                  ArrayList<LWLeaderCard> leaderCardsPlayed) {
        this.leaderCardsNotPlayed = leaderCardsNotPlayed;
        this.leaderCardsPlayed = leaderCardsPlayed;
        this.ui.onSlotLeaderCardsChanged ();
    }

    public void addWMPowers(LWWMPower lwwmPower){
        this.whiteMarblePowers.add(lwwmPower);
        this.ui.onWMPowerChanged ();
    }

    public void addXProductionPowers(LWXProductionPower lwxProductionPower){
        this.extraProductionPowers.add(lwxProductionPower);
        this.ui.onXPowersChanged();
    }

    public ArrayList<LWWMPower> getWhiteMarblePowers() {
        return whiteMarblePowers;
    }

    public ArrayList<LWXProductionPower> getExtraProductionPowers() {
        return extraProductionPowers;
    }

    /**
     * This method is used to attach the observer to the object that implements this interface
     *
     * @param attached
     */
    @Override
    public void attach(UI attached) {
        this.ui = attached;
    }
}
