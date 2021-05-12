package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.exception.EmptySlotException;
import it.polimi.ingsw.server.exception.NotContainedResourceException;
import it.polimi.ingsw.server.exception.NotExistingExtraProductionPower;
import it.polimi.ingsw.server.exception.WrongSlotDevelopmentIndexException;
import it.polimi.ingsw.server.model.cards.developmentcards.DevelopmentCard;
import it.polimi.ingsw.server.model.cards.developmentcards.SlotDevelopmentCards;
import it.polimi.ingsw.server.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.server.model.cards.leadercards.SlotLeaderCards;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gameresources.Producible;
import it.polimi.ingsw.server.model.gameresources.faithtrack.FaithPoint;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.server.model.gameresources.stores.Strongbox;
import it.polimi.ingsw.server.model.gameresources.stores.TemporaryContainer;
import it.polimi.ingsw.server.model.gameresources.stores.WarehouseDepots;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Class that represents the board that belongs to a player.
 * It's a collection of game elements, like Strongbox, Depots...
 */
public class PersonalBoard extends Producer {

    /**
     * this class models the extra
     * production power provided
     * by a leader card
     */
    class ExtraProductionPower extends Producer{

        private final StorableResource consumedResource;

        /**
         * constructor method of this class
         * @param consumedResource is the resource consumed to start the production
         */
        private ExtraProductionPower(StorableResource consumedResource) {
            this.consumedResource = consumedResource;
        }

        /**
         * It's the getter method for the consumed resource.
         * @return
         */
        public StorableResource getConsumedResource() {
            return (StorableResource) consumedResource.clone();
        }

        /**
         * this method activates the extra production power
         * @param producedResource is the resource that the player wants to receive
         * @return a list of resource, one of this resource is a faith point and the other one is the resource choose by the player
         */
        ArrayList <Producible> produce(Player player, StorableResource producedResource) throws NotExistingExtraProductionPower, NotContainedResourceException {
            if (checkExtraPower(player)) {
                FaithPoint faithPoint = new FaithPoint(1);
                ArrayList<Producible> listOfResource = new ArrayList<>(0);
                listOfResource.add(producedResource);
                listOfResource.add(faithPoint);
                return listOfResource;
            }
            else
                throw new NotContainedResourceException();
        }

        /**
         * this method invokes the method "checkActivation" of the class
         * ExtraProductionPower to checks if the player has the resources
         * that the power uses. After the check it calls the method
         * "activateExtraProductionPower" to start the production
         * @param player is the refer to the player
         * @return a boolean value, true if the production can be activated, if this method returns true
         * the action has to remove the consumed resource from the personal board
         */
        private boolean checkExtraPower(Player player) {
            return this.consumedResource.containedIn(player);
        }

    }
    private Strongbox strongbox;

    private WarehouseDepots warehouseDepots;
    private ArrayList<SlotDevelopmentCards> listOfSlotDevelopmentCards;
    private TemporaryContainer tempContainer;
    private SlotLeaderCards slotLeaderCards;
    private ArrayList <ExtraProductionPower> extraProductionPowers;

    /**
     * Constructor method of this class. It creates an empty personal board.
     * The method is public because called by Action/Game/Controller outside this package.
     */
    public PersonalBoard(WarehouseDepots warehouseDepots, int numberOfSlotDevCards, int maxDevCardsInSlot, int maxLeaderCardsInSlot, int maxNumOfCardsDuringGame) {
        this.strongbox = new Strongbox();
        this.warehouseDepots = warehouseDepots;
        this.listOfSlotDevelopmentCards = initSlotsDevCards(numberOfSlotDevCards, maxDevCardsInSlot);
        this.slotLeaderCards = new SlotLeaderCards(maxLeaderCardsInSlot, maxNumOfCardsDuringGame);
        this.tempContainer = new TemporaryContainer();
        this.extraProductionPowers = new ArrayList<>(0);
    }



    /**
     * this method is called by the leader card
     * that adds an extra production power to
     * the personal board of a player.
     * @param consumedResource -> the resource that the extra production power uses to activate the production
     */
    public void addExtraProductionPower(StorableResource consumedResource) {
        ExtraProductionPower extraProductionPower = new ExtraProductionPower(consumedResource);
        this.extraProductionPowers.add(extraProductionPower);
    }

    public ExtraProductionPower getExtraPower (int index) throws NotExistingExtraProductionPower {
        if(this.extraProductionPowers.size() == 0 || index > this.extraProductionPowers.size() - 1)
            throw new NotExistingExtraProductionPower();
        return this.extraProductionPowers.get(index);
    }


    /**
     * this method invokes the method "containedIn"
     * on the "consumedResources"
     * to check if the player has all the resources
     * he wants to spend to generate another resource
     * @param consumedResources -> resources that the player wants to spend
     * @param producedResource -> resource that the player wants to gain
     * @param player -> the player that wants to start the basic power production
     * @return -> the "producedResource" if the player has the consumed resources in his personal board
     */
    //TODO: forse va direttamente nella action
    StorableResource basicProductionPower(ArrayList <StorableResource> consumedResources, StorableResource producedResource, Player player) throws NotContainedResourceException {
        for(int i = 0; i < consumedResources.size(); i++) {
            if(!consumedResources.get(i).containedIn(player)) {
                throw new NotContainedResourceException();
            }
        }
        return producedResource;
    }


    /**
     * Method that initializes the slots of development cards.
     * @param numberOfSlotDevCards integer that represents how many slots are contained in this personal board.
     * @param maxNumberOfDevCards integer that represents how many development cards are contained in each individual slot.
     * @return the arraylist of slot of development cards.
     */
    private ArrayList<SlotDevelopmentCards> initSlotsDevCards(int numberOfSlotDevCards, int maxNumberOfDevCards) {
        ArrayList<SlotDevelopmentCards> slotsOfDevCardsArray = new ArrayList<>(numberOfSlotDevCards);
        for (int i = 0; i < numberOfSlotDevCards; i++) {
            slotsOfDevCardsArray.add(new SlotDevelopmentCards(maxNumberOfDevCards));
        }
        return slotsOfDevCardsArray;
    }


    /**
     * Getter method for "strongbox" attribute of this class.
     * @return the real (not a copy) strongbox.
     */
    public Strongbox getStrongbox() {
        return strongbox;
    }


    /**
     * Getter method for "slotLeaderCards" attribute of this class.
     * @return the real (not a copy) slot of leader cards.
     */
    SlotLeaderCards getSlotLeaderCards() {
        return slotLeaderCards;
    }


    /**
     * Getter method for "warehouseDepots" attribute of this class.
     * @return the real (not a copy) warehouse depots.
     */
    public WarehouseDepots getWarehouseDepots(){
        return warehouseDepots;
    }


    /**
     * Getter method for "tempContainer" attribute of this class.
     * @return the real (not a copy) temporary container.
     */
    public TemporaryContainer getTempContainer() {
        return tempContainer;
    }


    /**
     * Method that return one of the real slot of development cards (not a copy) to the caller.
     * @param slotIndex int that indicates which slot need to be returned.
     * @return the real slot, that corresponds to an element of the array "listOfSlotDevelopmentCards".
     * @throws WrongSlotDevelopmentIndexException exception thrown if the provided index is negative or
     * exceeds the number of slots.
     */
    SlotDevelopmentCards getSlotDevelopmentCards(int slotIndex) throws WrongSlotDevelopmentIndexException {
        if(slotIndex >= 0 && slotIndex < listOfSlotDevelopmentCards.size())
            return listOfSlotDevelopmentCards.get(slotIndex);
        else { throw new WrongSlotDevelopmentIndexException();}
    }


    /**
     * this method invokes the method "getAllCards" from all
     * the slots that contain the development cards owned by
     * the player to build a list of all the development cards of the player
     * @return the list of all the development cards of the player
     * @throws WrongSlotDevelopmentIndexException
     * @throws EmptySlotException
     */
    public ArrayList <DevelopmentCard> getAllDevelopmentCards() {
        ArrayList <DevelopmentCard> listOfAllCards = new ArrayList<>(0);
        for(int i = 0; i < listOfSlotDevelopmentCards.size(); i++) {
            try {
                listOfAllCards.addAll(getSlotDevelopmentCards(i).getAllCards());
            } catch(EmptySlotException | WrongSlotDevelopmentIndexException e) {
                //do nothing, because the i-th slot is empty, so go to the next slot.
            }
        }
        return listOfAllCards;
    }


    /**
     * this method provides an
     * ArrayList of the player's requirements
     * represented by storable resources
     * @return
     */
    public ArrayList <StorableResource> getAllResource() {
        ArrayList <StorableResource> requirements = new ArrayList<>(0);
        requirements.addAll(this.getStrongbox().getAllResources());
        requirements.addAll(this.getWarehouseDepots().getAllResources());
        return requirements;
    }


    /**
     * equals method.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonalBoard)) return false;
        PersonalBoard that = (PersonalBoard) o;
        return getStrongbox().equals(that.getStrongbox()) && getWarehouseDepots().equals(that.getWarehouseDepots()) && listOfSlotDevelopmentCards.equals(that.listOfSlotDevelopmentCards) && getTempContainer().equals(that.getTempContainer()) && getSlotLeaderCards().equals(that.getSlotLeaderCards()) && extraProductionPowers.equals(that.extraProductionPowers);
    }


    /**
     * hashcode method.
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(getStrongbox(), getWarehouseDepots(), listOfSlotDevelopmentCards, getTempContainer(), getSlotLeaderCards(), extraProductionPowers);
    }

    private int getAllResourcesAmount() {
        ArrayList<StorableResource> resources = getAllResource();
        int totalAmount = 0;
        for (StorableResource resource : resources)
            totalAmount += resource.getAmount();
        return totalAmount;
    }

    public VictoryPoint computeTotalVP() {
        VictoryPoint points = new VictoryPoint(0);
        ArrayList<DevelopmentCard> cards = getAllDevelopmentCards();
        for (DevelopmentCard card : cards) {
            points.increaseVictoryPoints(card.getVictoryPoints());
        }
        ArrayList<LeaderCard> leaderCards = this.slotLeaderCards.getAllActiveCards();
        for (LeaderCard card : leaderCards)
            points.increaseVictoryPoints(card.getVictoryPoints());
        points.increaseVictoryPoints(new VictoryPoint((int) Math.ceil(getAllResourcesAmount() / 5)));
        return points;
    }

    public boolean checkFirstTurnConditions(int initialResources) {
        return getAllResourcesAmount() == initialResources
                && this.getSlotLeaderCards().isReadyToStart();
    }

    ArrayList<Producer> getAllProducers() {
        ArrayList<Producer> producers = new ArrayList<>();
        producers.add(this);
        producers.addAll(this.listOfSlotDevelopmentCards);
        producers.addAll(this.extraProductionPowers);
        return producers;
    }
}