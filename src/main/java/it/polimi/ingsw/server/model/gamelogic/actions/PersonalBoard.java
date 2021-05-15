package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.exception.EmptySlotException;
import it.polimi.ingsw.server.model.exception.NotContainedResourceException;
import it.polimi.ingsw.server.model.exception.NotExistingExtraProductionPower;
import it.polimi.ingsw.server.model.exception.WrongSlotDevelopmentIndexException;
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
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.Publisher;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Class that represents the board that belongs to a player.
 * It's a collection of game elements, like Strongbox, Depots...
 */
public class PersonalBoard extends Producer implements Publisher {

    /**
     * this class models the extra
     * production power provided
     * by a leader card
     */
    class ExtraProductionPower extends Producer {

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


    private ArrayList<Observer> observers;
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
        this.observers = new ArrayList<> ();
    }



    /**
     * this method is called by the leader card
     * that adds an extra production power to
     * the personal board of a player.
     * @param consumedResource the resource that the extra production power uses to activate the production
     */
    public void addExtraProductionPower(StorableResource consumedResource) {
        ExtraProductionPower extraProductionPower = new ExtraProductionPower(consumedResource);
        this.extraProductionPowers.add(extraProductionPower);
    }


    /**
     * @param index index of the power
     * @return the ExtraProductionPower in this PersonalBoard at the specified index
     * @throws NotExistingExtraProductionPower
     */
    public ExtraProductionPower getExtraPower (int index) throws NotExistingExtraProductionPower {
        if(this.extraProductionPowers.size() == 0 || index > this.extraProductionPowers.size() - 1)
            throw new NotExistingExtraProductionPower();
        return this.extraProductionPowers.get(index);
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
    public SlotLeaderCards getSlotLeaderCards() {
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


    /**
     * @return the number of all the resources stored in this PersonalBoard
     */
    private int getAllResourcesAmount() {
        ArrayList<StorableResource> resources = getAllResource();
        int totalAmount = 0;
        for (StorableResource resource : resources)
            totalAmount += resource.getAmount();
        return totalAmount;
    }


    /**
     * @return the total VictoryPoint that the PersonalBoard has in the current instant in which this method is called
     */
    public VictoryPoint computeTotalVP() {
        VictoryPoint points = new VictoryPoint(0);
        ArrayList<DevelopmentCard> cards = getAllDevelopmentCards();
        for (DevelopmentCard card : cards)
            points.increaseVictoryPoints(card.getVictoryPoints());
        ArrayList<LeaderCard> leaderCards = this.slotLeaderCards.getAllActiveCards();
        for (LeaderCard card : leaderCards)
            points.increaseVictoryPoints(card.getVictoryPoints());
        points.increaseVictoryPoints(new VictoryPoint((int) Math.ceil(getAllResourcesAmount() / 5)));
        return points;
    }


    /**
     * @param initialResources It's the total amount of all the resources that a PersonalBoard has to contain at the end
     *                        of the first turn
     * @return true of the conditions of the first Turn are satisfied
     */
    public boolean checkFirstTurnConditions(int initialResources) {
        return getAllResourcesAmount() == initialResources
                && this.getSlotLeaderCards().isReadyToStart();
    }


    /**
     * @return all the Producers belonging to the PersonalBoard
     */
    ArrayList<Producer> getAllProducers() {
        ArrayList<Producer> producers = new ArrayList<>();
        producers.add(this);
        producers.addAll(this.listOfSlotDevelopmentCards);
        producers.addAll(this.extraProductionPowers);
        return producers;
    }


    /**
     * This method notifies a change in the status of the publisher to the Observers registered, usually
     */
    @Override
    public void publish() {

    }


    /**
     * This method is used to attach the observer to the object that implements this interface
     *
     * @param observer
     */
    @Override
    public void attach(Observer observer) {
        this.observers.add (observer);
        if (!observers.contains (observer)) {
            if (observers.size () == 1) {
                //TODO: attach only to the things that only the player will know. es: tempContainer?
            }
        }
    }
}
