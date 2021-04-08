package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.WrongSlotDevelopmentIndexException;
import it.polimi.ingsw.exception.WrongSlotIndexException;
import it.polimi.ingsw.model.cards.developmentcards.SlotDevelopmentCards;
import it.polimi.ingsw.model.config.ConfigLoaderWriter;
import it.polimi.ingsw.model.personalboard.Strongbox;
import it.polimi.ingsw.model.personalboard.WarehouseDepots;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Class that represents the board that belongs to a player. It's a collection of game elements,
 * like Strongbox, Depots...
 */
public class PersonalBoard {

    private Strongbox strongbox;
    private WarehouseDepots warehouseDepots;
    private ArrayList<SlotDevelopmentCards> listOfSlotDevelopmentCards;
    //private Arraylist<SlotLeaderCard> listOfSlotLC;


    /**
     * Constructor method of this class. It creates an empty personal board.
     * The method is public because called by Action/Game/Controller outside this package.
     */
    PersonalBoard() {
    }


    /**
     * Method that builds every element of the personal board.
     */
    void prepareGameBoard() throws FileNotFoundException {
        strongbox = new Strongbox();
        warehouseDepots = new WarehouseDepots();
        Integer numberOfSlotDevelopmentCards = 0;
        ConfigLoaderWriter.getAttribute(numberOfSlotDevelopmentCards, "numberOfSlotDevelopmentCards", PersonalBoard.class);
        listOfSlotDevelopmentCards = new ArrayList<SlotDevelopmentCards>(0);
        for(int i = 0; i < numberOfSlotDevelopmentCards; i++) {
            SlotDevelopmentCards temporarySlot = new SlotDevelopmentCards();
            listOfSlotDevelopmentCards.add(temporarySlot);
        }
        //create an Arraylist of SlotLeaderCard.
    }


    /**
     * Method that return the real strongbox (not a copy) to the caller.
     * @return -> the real strongbox, that is an attribute of this personal board.
     */
    Strongbox getStrongbox(){
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

    //public Arraylist<SlotDevelopmentCard> getAllSlotDevelopmentCard(){ return listOfSlotDC;}
    //public SlotLeaderCard getSlotLeaderCard(int slotIndex){ return listOfSlotLC.get(slotIndex);}
    //public Arraylist<SlotLeaderCard> getAllSlotLeaderCard(){ return listOfSlotLC;}
    //may be useful a private method that checks if the provided index has sense or not.
}
