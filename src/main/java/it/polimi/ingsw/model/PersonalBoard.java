package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.WrongSlotDevelopmentIndexException;
import it.polimi.ingsw.model.cards.developmentcards.SlotDevelopmentCards;
import it.polimi.ingsw.model.config.ConfigLoaderWriter;
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
    //private Arraylist<SlotLeaderCard> listOfSlotLC;

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

    PersonalBoard initFromJSON() throws FileNotFoundException {
        initWarehouseFromJSON("personalBoard/");
        initSlotsDevCardsFromJSON("personalBoard/");
        //TODO : LEADER CARDS SLOT
        return this;
    }

    private ArrayList<SlotDevelopmentCards> initSlotsDevCards(int numberOfSlotDevCards, int maxNumberOfDevCards) {
        ArrayList<SlotDevelopmentCards> slotsOfDevCardsArray = new ArrayList<>(numberOfSlotDevCards);
        for (int i = 0; i < numberOfSlotDevCards; i++) {
            slotsOfDevCardsArray.add(new SlotDevelopmentCards(maxNumberOfDevCards));
        }
        return slotsOfDevCardsArray;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(numberOfSlotDevCards, numberOfSlotLeaderCards, strongbox, warehouseDepots, listOfSlotDevelopmentCards);
    }

    //public Arraylist<SlotDevelopmentCard> getAllSlotDevelopmentCard(){ return listOfSlotDC;}
    //public SlotLeaderCard getSlotLeaderCard(int slotIndex){ return listOfSlotLC.get(slotIndex);}
    //public Arraylist<SlotLeaderCard> getAllSlotLeaderCard(){ return listOfSlotLC;}
    //may be useful a private method that checks if the provided index has sense or not.
}
