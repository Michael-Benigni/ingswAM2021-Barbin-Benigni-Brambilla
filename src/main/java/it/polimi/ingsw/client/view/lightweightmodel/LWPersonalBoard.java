package it.polimi.ingsw.client.view.lightweightmodel;


import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.utils.Attachable;
import java.util.ArrayList;

/**
 * Class that represents the view of the personal board owned by a player.
 */
public class LWPersonalBoard implements Attachable<UI> {

    /**
     * Warehouse depot of this personal board, represented by an  array of depots.
     */
    private ArrayList<LWDepot> warehouse;

    /**
     * Strongbox of this personal board, represented by an array of resources.
     */
    private ArrayList<LWResource> strongbox;

    /**
     * Slots for development cards, represented by an array of array of cards.
     */
    private ArrayList<ArrayList<LWDevCard>> slots;

    /**
     * List of leader cards already played by this player, represented by an array of leader cards.
     */
    private ArrayList<LWLeaderCard> leaderCardsPlayed;

    /**
     * List of leader cards still not played by this player, represented by an array of leader cards.
     */
    private ArrayList<LWLeaderCard> leaderCardsNotPlayed;

    /**
     * Temporary container of this personal board, used to store resource between the actions, ready to be placed in
     * the strongbox or in the warehouse depots.
     */
    private final LWTemporaryContainer temporaryContainer;

    /**
     * List of leader card power of the type "transform white marble" that are activated.
     */
    private final ArrayList <LWWMPower> whiteMarblePowers;

    /**
     * List of leader card power of the type "extra production power" that are activated.
     */
    private final ArrayList <LWXProductionPower> extraProductionPowers;

    /**
     * User interface
     */
    private UI ui;

    /**
     * Constructor method of this class.
     */
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

    /**
     * Method that initializes the slots of development cards as an array of empty arrays.
     */
    private void initSlots(){
        this.slots = new ArrayList<> ();
        //TODO: 3 in file o sent in update
        for(int i = 0; i < 3; i++){
            slots.add(new ArrayList<>());
        }
    }

    /**
     * Getter method for the warehouse depots of this personal board.
     */
    public ArrayList<LWDepot> getWarehouse() {
        return warehouse;
    }

    /**
     * Getter method for the temporary container of this personal board.
     */
    public LWTemporaryContainer getTemporaryContainer() {
        return temporaryContainer;
    }

    /**
     * Getter method for the strongbox of this personal board.
     */
    public ArrayList<LWResource> getStrongbox() {
        return strongbox;
    }

    /**
     * Getter method for the slots of development cards of this personal board.
     */
    public ArrayList<ArrayList<LWDevCard>> getSlots() {
        return slots;
    }

    /**
     * Getter method for the array of leader cards already played.
     */
    public ArrayList<LWLeaderCard> getLeaderCardsPlayed() {
        return leaderCardsPlayed;
    }

    /**
     * Getter method for the array of leader cards still not played.
     */
    public ArrayList<LWLeaderCard> getLeaderCardsNotPlayed() {
        return leaderCardsNotPlayed;
    }

    /**
     * Method that updates the warehouse depots of this personal board with the one provided.
     */
    public void updateWarehouse(ArrayList<LWDepot> warehouse) {
        this.warehouse = warehouse;
        this.ui.onWarehouseChanged();
    }

    /**
     * Method that updates the strongbox of this personal board with the one provided.
     */
    public void updateStrongbox(ArrayList<LWResource> strongbox) {
        this.strongbox = strongbox;
        this.ui.onStrongboxChanged();
    }

    /**
     * Method that updates the temporary container of this personal board with the one provided.
     */
    public void updateTemporaryContainer(ArrayList<LWResource> storableResources, int emptyResources) {
        this.temporaryContainer.storableResources = storableResources;
        this.temporaryContainer.emptyResources = emptyResources;
        this.ui.onTempContainerChanged();
    }

    /**
     * Method that updates the slots of development cards of this personal board with the ones provided.
     */
    public void updateSlots(LWDevCard addedDevCard, Integer numberOfSlot, Integer positionInSlot) {
        if (addedDevCard.getId () != null)
            slots.get(numberOfSlot).add(positionInSlot, addedDevCard);
        this.ui.onSlotDevCardsChanged ();
    }

    /**
     * Method that updates the leader cards in this personal board (played and not played).
     */
    public void updateLeaderCards(ArrayList<LWLeaderCard> leaderCardsNotPlayed,
                                  ArrayList<LWLeaderCard> leaderCardsPlayed) {
        this.leaderCardsNotPlayed = leaderCardsNotPlayed;
        this.leaderCardsPlayed = leaderCardsPlayed;
        this.ui.onSlotLeaderCardsChanged ();
    }

    /**
     * Method that adds a "Transform White Marble" power to the list of activated power.
     */
    public void addWMPowers(LWWMPower lwwmPower){
        this.whiteMarblePowers.add(lwwmPower);
        this.ui.onWMPowerChanged ();
    }

    /**
     * Method that adds a "Extra Production" power to the list of activated power.
     */
    public void addXProductionPowers(LWXProductionPower lwxProductionPower){
        this.extraProductionPowers.add(lwxProductionPower);
        this.ui.onXPowersChanged();
    }

    /**
     * Getter method for the list of the activated "Transform White Marble" powers.
     */
    public ArrayList<LWWMPower> getWhiteMarblePowers() {
        return whiteMarblePowers;
    }

    /**
     * Getter method for the list of the activated "Extra Production" powers.
     */
    public ArrayList<LWXProductionPower> getExtraProductionPowers() {
        return extraProductionPowers;
    }

    /**
     * This method is used to attach the observer to the object that implements this interface.
     */
    @Override
    public void attach(UI attached) {
        this.ui = attached;
    }
}
