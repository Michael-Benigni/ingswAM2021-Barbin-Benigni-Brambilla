package it.polimi.ingsw.model.gamelogic.actions;

import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.cards.developmentcards.DevelopmentCard;
import it.polimi.ingsw.model.cards.developmentcards.SlotDevelopmentCards;
import it.polimi.ingsw.model.cards.leadercards.SlotLeaderCards;
import it.polimi.ingsw.model.gameresources.faithtrack.FaithPoint;
import it.polimi.ingsw.model.gameresources.Resource;
import it.polimi.ingsw.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.model.gameresources.stores.Strongbox;
import it.polimi.ingsw.model.gameresources.stores.TemporaryContainer;
import it.polimi.ingsw.model.gameresources.stores.WarehouseDepots;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Class that represents the board that belongs to a player.
 * It's a collection of game elements, like Strongbox, Depots...
 */
public class PersonalBoard {

    /**
     * this class models the extra
     * production power provided
     * by a leader card
     */
    private class ExtraProductionPower {

        private final StorableResource consumedResource;
        /**
         * constructor method of this class
         * @param consumedResource is the resource consumed to start the production
         */
        private ExtraProductionPower(StorableResource consumedResource) {
            this.consumedResource = consumedResource;
        }

        /**
         * this invokes the method "containedIn"
         * to check if the player has all the
         * resource required to start the production
         * @param player is the refer to the player
         * @return boolean value, true if the player can start the production
         * @throws NullResourceAmountException
         */
        boolean checkActivation(Player player) {
            if(this.consumedResource.containedIn(player)) {
                return true;
            }
            return false;
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
    public PersonalBoard(WarehouseDepots warehouseDepots, int numberOfSlotDevCards, int maxDevCardsInSlot, int maxLeaderCardsInSlot) {
        strongbox = new Strongbox();
        this.warehouseDepots = warehouseDepots;
        this.listOfSlotDevelopmentCards = initSlotsDevCards(numberOfSlotDevCards, maxDevCardsInSlot);
        this.slotLeaderCards = new SlotLeaderCards(maxLeaderCardsInSlot);
        this.tempContainer = new TemporaryContainer();
        this.extraProductionPowers = new ArrayList<>();
    }

    /**
     * this method invokes the method "checkActivation" of the class
     * ExtraProductionPower to checks if the player has the resources
     * that the power uses. After the check it calls the method
     * "activateExtraProductionPower" to start the production
     * @param powerIndex is the index that the player provides to communicate which production power he wants to start
     * @param player is the refer to the player
     * @param producedResource is the resource that the player wants to gain after the production
     * @return a boolean value, true if the production can be activated, if this method returns true
     *                        the action has to remove the consumed resource from the personal board
     * @throws NullResourceAmountException
     * @throws NegativeResourceAmountException
     */
    public boolean checkExtraPower(int powerIndex, Player player, StorableResource producedResource) throws NotExistingExtraProductionPower {
        if(this.extraProductionPowers.size() == 0)
            throw new NotExistingExtraProductionPower();
        if(this.extraProductionPowers.get(powerIndex).checkActivation(player)) {
            activateExtraProductionPower(producedResource);
            return true;
        }
        return false;
    }


    /**
     * this method activates the extra production power
     * @param producedResource is the resource that the player wants to receive
     * @return a list of resource, one of this resource is a faith point and the other one is the resource choosen by the player
     * @throws NegativeResourceAmountException
     */
    ArrayList <Resource> activateExtraProductionPower(StorableResource producedResource) {
        FaithPoint faithPoint = new FaithPoint(1);
        ArrayList <Resource> listOfResource = new ArrayList<>(0);
        listOfResource.add(producedResource);
        listOfResource.add(faithPoint);
        return listOfResource;
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


    /**
     * this method invokes the method "containedIn"
     * on the "consumedResources"
     * to check if the player has all the resources
     * he wants to spend to generate another resource
     * @param consumedResources -> resources that the player wants to spend
     * @param producedResource -> resource that the player wants to gain
     * @param player -> the player that wants to start the basic power production
     * @return -> the "producedResource" if the player has the consumed resources in his personal board
     * @throws NullResourceAmountException
     */
    //TODO: forse va direttamente nella action
    StorableResource basicProductionPower(ArrayList <StorableResource> consumedResources, StorableResource producedResource, Player player) {
        for(int i = 0; i < consumedResources.size(); i++) {
            if(!consumedResources.get(i).containedIn(player)) {
                return null;
            }
        }
        return producedResource;
    }


    /**
     * //TODO:
     * @param numberOfSlotDevCards
     * @param maxNumberOfDevCards
     * @return
     */
    private ArrayList<SlotDevelopmentCards> initSlotsDevCards(int numberOfSlotDevCards, int maxNumberOfDevCards) {
        ArrayList<SlotDevelopmentCards> slotsOfDevCardsArray = new ArrayList<>(numberOfSlotDevCards);
        for (int i = 0; i < numberOfSlotDevCards; i++) {
            slotsOfDevCardsArray.add(new SlotDevelopmentCards(maxNumberOfDevCards));
        }
        return slotsOfDevCardsArray;
    }


    /**
     * Method that return the real strongbox (not a copy) to the caller.
     * @return -> the real strongbox, that is an attribute of this personal board.
     */
    public Strongbox getStrongbox() {
        return strongbox;
    }


    /**
     * //TODO:
     * @return
     */
    SlotLeaderCards getSlotLeaderCards() {
        return slotLeaderCards;
    }


    /**
     * Method that return the real warehouse (not a copy) to the caller.
     * @return -> the real warehouse, that is an attribute of this personal board.
     */
    public WarehouseDepots getWarehouseDepots(){
        return warehouseDepots;
    }


    /**
     * TODO:
     * @return
     */
    public TemporaryContainer getTempContainer() {
        return tempContainer;
    }


    /**
     * Method that return one of the real slot of development cards (not a copy) to the caller.
     * @param slotIndex -> int that indicates which slot need to be returned.
     * @return -> the real slot, that corresponds to an element of the array "listOfSlotDevelopmentCards".
     * @throws WrongSlotDevelopmentIndexException -> exception thrown if the provided index is negative or
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
    public ArrayList <DevelopmentCard> getAllDevelopmentCards() throws WrongSlotDevelopmentIndexException, EmptySlotException {
        ArrayList <DevelopmentCard> listOfAllCards = new ArrayList<>(0);
        for(int i = 0; i < listOfSlotDevelopmentCards.size(); i++) {
            listOfAllCards.addAll(getSlotDevelopmentCards(i).getAllCards());
        }
        return listOfAllCards;
    }


    /**
     * this method provides an
     * ArrayList of the player's requirements
     * represented by storable resources
     * @return
     */
    ArrayList <StorableResource> getAllResource() {
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
}
