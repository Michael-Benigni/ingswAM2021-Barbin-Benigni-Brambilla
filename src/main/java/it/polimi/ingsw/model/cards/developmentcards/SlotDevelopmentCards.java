package it.polimi.ingsw.model.cards.developmentcards;

import it.polimi.ingsw.exception.DevelopmentCardNotAddableException;
import it.polimi.ingsw.exception.EmptySlotException;
import it.polimi.ingsw.exception.SlotDevelopmentCardsIsFullException;
import it.polimi.ingsw.model.config.ConfigLoaderWriter;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * this class models a single slot where the player can place his development cards
 */
public class SlotDevelopmentCards {
    private Integer maxNumberOfCardsInSlot;
    private ArrayList <DevelopmentCard> listOfDevelopmentCards;


    //TODO: missing parameter maxNumberOfCardsInSlot in constructor method
    /**
     * constructor of the class SlotDevelopmentCards
     */
    public SlotDevelopmentCards() throws FileNotFoundException {
        setMaxNumberOfCardsInSlot();
        this.listOfDevelopmentCards = new ArrayList <DevelopmentCard> (0);
    }

    public void setMaxNumberOfCardsInSlot() throws FileNotFoundException {
        this.maxNumberOfCardsInSlot = 0;
        ConfigLoaderWriter.getAttribute(maxNumberOfCardsInSlot, "maxNumberOfCardsInSlot", SlotDevelopmentCards.class);
    }

    /**
     * this method provides the development card that is on top of the slot to the caller
     * @return the created copy of the top development card
     */
    DevelopmentCard getTopCard() throws EmptySlotException {
        if(!listOfDevelopmentCards.isEmpty()) {
            DevelopmentCard topCard = (DevelopmentCard) listOfDevelopmentCards.get((listOfDevelopmentCards.size() - 1)).clone();
            return topCard; //the first position in the list is occupied by the last added development card
        }
        throw new EmptySlotException();
    }

    /**
     * this method provides all the cards that are placed in the slot
     * the array is filled starting from the card on top of the slot
     * @return an array list of DevelopmentCard
     */
    ArrayList <DevelopmentCard> getAllCards() throws EmptySlotException {
        ArrayList <DevelopmentCard> listOfAllCards = new ArrayList<>(0);
        listOfAllCards.add(this.getTopCard());
        for (int i = listOfDevelopmentCards.size() - 2; i > 0; i--) {
            listOfAllCards.add((DevelopmentCard) listOfDevelopmentCards.get(i).clone());
        }
        return listOfAllCards;
    }

    /**
     * this method adds a new development card on top of the slot
     * @param cardToAdd
     * @throws Exception ->  EmptySlotException, DevelopmentCardNotAddableException
     */
    void placeOnTop (DevelopmentCard cardToAdd) throws DevelopmentCardNotAddableException, SlotDevelopmentCardsIsFullException {
        if (listOfDevelopmentCards.size() < maxNumberOfCardsInSlot) {
            DevelopmentCard card;
            try {
                card = getTopCard();
            } catch (EmptySlotException exc) {
                card = null;
            }
            if (cardToAdd.isOfNextLevel(card)) {
                this.listOfDevelopmentCards.add(cardToAdd);
                return;
            }
            throw new DevelopmentCardNotAddableException();
        }
        throw new SlotDevelopmentCardsIsFullException();
    }
}