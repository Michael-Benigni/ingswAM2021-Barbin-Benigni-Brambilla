package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.EmptySlotException;
import it.polimi.ingsw.exception.NegativeResourceAmountException;
import it.polimi.ingsw.exception.WrongSlotDevelopmentIndexException;
import it.polimi.ingsw.model.cards.developmentcards.DevelopmentCard;
import it.polimi.ingsw.model.cards.developmentcards.SlotDevelopmentCards;
import it.polimi.ingsw.model.cards.leadercards.Requirement;
import it.polimi.ingsw.model.cards.leadercards.SlotLeaderCards;
import it.polimi.ingsw.model.config.ConfigLoaderWriter;
import it.polimi.ingsw.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.model.gameresources.stores.Strongbox;
import it.polimi.ingsw.model.gameresources.stores.WarehouseDepots;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Class that represents the board that belongs to a player. It's a collection of game elements,
 * like Strongbox, Depots...
 */
public class PersonalBoard {
    private Integer numberOfSlotDevCards;
    private Integer numberOfSlotLeaderCards;
    private Strongbox strongbox;
    private WarehouseDepots warehouseDepots;
    private ArrayList<SlotDevelopmentCards> listOfSlotDevelopmentCards;
    private ArrayList<SlotLeaderCards> listOfSlotLeaderCards;

    /**
     * Constructor method of this class. It creates an empty personal board.
     * The method is public because called by Action/Game/Controller outside this package.
     */
    PersonalBoard(int numberOfSlotDevCards, int numberOfSlotLeaderCards) {
        this.numberOfSlotDevCards = numberOfSlotDevCards;
        this.numberOfSlotLeaderCards = numberOfSlotLeaderCards;
        strongbox = new Strongbox();
        warehouseDepots = new WarehouseDepots(0, null);
        listOfSlotDevelopmentCards = initSlotsDevCards(numberOfSlotDevCards, 0);
        //TODO: createListOfLeaderCards(numberOfSlotLeaderCards);
    }

    //TODO: documentation
    PersonalBoard initFromJSON() throws FileNotFoundException {
        initWarehouseFromJSON("personalBoard/");
        initSlotsDevCardsFromJSON("personalBoard/");
        //TODO : LEADER CARDS SLOT
        return this;
    }

    //TODO: documentation and is this method useful? the method initSlotsDevCardsFromJSON already exists
    private ArrayList<SlotDevelopmentCards> initSlotsDevCards(int numberOfSlotDevCards, int maxNumberOfDevCards) {
        ArrayList<SlotDevelopmentCards> slotsOfDevCardsArray = new ArrayList<>(numberOfSlotDevCards);
        for (int i = 0; i < numberOfSlotDevCards; i++) {
            slotsOfDevCardsArray.add(new SlotDevelopmentCards(maxNumberOfDevCards));
        }
        return slotsOfDevCardsArray;
    }

    /**
     * this method initializes the warehouse
     * of the player from the database
     * @param jsonPath -> the path of the file that represents the database
     * @throws FileNotFoundException
     */
    private void initWarehouseFromJSON(String jsonPath) throws FileNotFoundException {
        final String keyInJSON = jsonPath + "warehouseDepots/";
        int numberOfDepots = (int) ConfigLoaderWriter.getAsJavaObjectFromJSON(int.class, keyInJSON + "numberOfDepots/");
        ArrayList<Integer> capacities = new ArrayList<>(numberOfDepots);
        for (int i = 0; i < numberOfDepots; i++) {
            int capacity = (int) ConfigLoaderWriter.getAsJavaObjectFromJSONArray(int.class, keyInJSON + "capacities/", new int[] {i});
            capacities.add(capacity);
        }
        warehouseDepots = new WarehouseDepots(numberOfDepots, capacities);
    }

    /**
     * this method initializes the slots
     * of development cards from the database
     * @param jsonPath -> the path of the file that represents the database
     * @throws FileNotFoundException
     */
    private void initSlotsDevCardsFromJSON(String jsonPath) throws FileNotFoundException {
        final String keyInJSON = jsonPath + "slotDevelopmentCards/";
        int maxNumberOfDevCardsInSlot = (int) ConfigLoaderWriter.getAsJavaObjectFromJSON(int.class, keyInJSON + "maxNumberOfCardsInSlot/");
        listOfSlotDevelopmentCards = initSlotsDevCards(numberOfSlotDevCards, maxNumberOfDevCardsInSlot);
    }

    /**
     * Method that return the real strongbox (not a copy) to the caller.
     * @return -> the real strongbox, that is an attribute of this personal board.
     */
    Strongbox getStrongbox () {
        return strongbox;
    }


    /**
     * Method that return the real warehouse (not a copy) to the caller.
     * @return -> the real warehouse, that is an attribute of this personal board.
     */
    WarehouseDepots getWarehouseDepots(){
        return warehouseDepots;
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
    private ArrayList <DevelopmentCard> getAllDevelopmentCards() throws WrongSlotDevelopmentIndexException, EmptySlotException {
        ArrayList <DevelopmentCard> listOfAllCards = new ArrayList<>(0);
        for(int i = 0; i < numberOfSlotDevCards; i++) {
            listOfAllCards.addAll(getSlotDevelopmentCards(i).getAllCards());
        }
        return listOfAllCards;
    }

    /**
     * this method provides an
     * ArrayList of the player's requirements
     * represented by storable resources
     * @return
     * @throws CloneNotSupportedException
     */
    ArrayList <StorableResource> getResourceRequirements() throws CloneNotSupportedException {
        ArrayList <StorableResource> requirements = new ArrayList<>(0);
        requirements.addAll(this.getStrongbox().getAllResources());
        requirements.addAll(this.getWarehouseDepots().getAllResources());
        return requirements;
    }

    /**
     * this method provides an
     * ArrayList of the player's requirements
     * represented by development cards
     * @return
     * @throws EmptySlotException
     * @throws WrongSlotDevelopmentIndexException
     */
    ArrayList <DevelopmentCard> getDevCardRequirements() throws EmptySlotException, WrongSlotDevelopmentIndexException {
        ArrayList <DevelopmentCard> requirements = new ArrayList<>(0);
        requirements.addAll(this.getAllDevelopmentCards());
        return requirements;
    }

    /**
     * this method overrides the Object method "equals"
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalBoard that = (PersonalBoard) o;
        return numberOfSlotDevCards.equals(that.numberOfSlotDevCards)
                && Objects.equals(numberOfSlotLeaderCards, that.numberOfSlotLeaderCards)
                && Objects.equals(strongbox, that.strongbox)
                && Objects.equals(warehouseDepots, that.warehouseDepots)
                && Objects.equals(listOfSlotDevelopmentCards, that.listOfSlotDevelopmentCards);
    }

    /**
     * this method overrides the Object method "hashCode"
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(numberOfSlotDevCards, numberOfSlotLeaderCards, strongbox, warehouseDepots, listOfSlotDevelopmentCards);
    }

    //public Arraylist<SlotDevelopmentCard> getAllSlotDevelopmentCard(){ return listOfSlotDC;}
    //public SlotLeaderCard getSlotLeaderCard(int slotIndex){ return listOfSlotLC.get(slotIndex);}
    //public Arraylist<SlotLeaderCard> getAllSlotLeaderCard(){ return listOfSlotLC;}
    //may be useful a private method that checks if the provided index has sense or not.
}
