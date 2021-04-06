package it.polimi.ingsw.model.personalboard;

import it.polimi.ingsw.exception.DevelopmentCardNotAddableException;
import it.polimi.ingsw.exception.EmptySlotException;
import it.polimi.ingsw.model.cards.developmentcards.DevelopmentCard;

import java.util.ArrayList;

/**
 * this class models a single slot where the player can place his development cards
 */
public class SlotDevelopmentCards {
    private ArrayList <DevelopmentCard> listOfDevelopmentCards;

    /**
     * constructor of the class SlotDevelopmentCards
     */
    SlotDevelopmentCards() {
        this.listOfDevelopmentCards = new ArrayList <DevelopmentCard> (0);
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
        else {
            throw new EmptySlotException();
        }
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
    void placeOnTop (DevelopmentCard cardToAdd) throws Exception {
        try {
            DevelopmentCard card = getTopCard();
            if(cardToAdd.isTheLevelRight(card)) {
                this.listOfDevelopmentCards.add(cardToAdd);
            }
            else {
                throw new DevelopmentCardNotAddableException();
            }
        }
        catch (EmptySlotException exc) {
            if(cardToAdd.isTheLevelRight()) {
                this.listOfDevelopmentCards.add(cardToAdd);
            }
            else {
                throw new DevelopmentCardNotAddableException();
            }
        }
    }
}